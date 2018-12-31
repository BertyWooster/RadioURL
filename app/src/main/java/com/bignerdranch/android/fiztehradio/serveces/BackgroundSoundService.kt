package com.bignerdranch.android.fiztehradio.serveces

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.bignerdranch.android.fiztehradio.MainActivity
import com.bignerdranch.android.fiztehradio.R


class BackgroundSoundService : Service() {
    lateinit var player: MediaPlayer
    var mIntention: String = "toStart" // Начальное намерение для MediaPlayer при запуске Service.
    val mUrl: String = "http://sc2b-sjc.1.fm:8030/"
    var isPlaing: Boolean = false
    var isFirstStarted = true


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val notification = createActionNotification(this)//Чтобы сервис жил в background нам нужна notification
        startForeground(1000, notification) // запускаемся в фоновом режиме.

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if(intent.extras != null){
            mIntention = intent.getStringExtra("intention") // Этот интент приходит из notification при нажатии.
        }


        if(isPlaing){

            if(mIntention == "toStart"){
                // игнорируем
            }
            if(mIntention == "toStop"){
                stop()
            }
            if (mIntention == "toBreak"){
                stopSelf()
            }
        }
        else{
            if(mIntention == "toStart" && isFirstStarted == false){
                restart()
            }

            if(mIntention == "toStart" && isFirstStarted == true){
                start()
                isFirstStarted = false
            }
            if(mIntention == "toStop"){
                //игнорируем
            }
            if (mIntention == "toBreak"){
                stopSelf()
            }
        }


        return Service.START_STICKY
    }

    override fun onDestroy() {
        player.release()
    }



    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    internal fun createActionNotification(context: Context):Notification {


        //Создание интента для запуска плеера!
        val startPlayerIntent = Intent(context, BackgroundSoundService::class.java)
        startPlayerIntent.putExtra("intention", "toStart")
        val piStart = PendingIntent.getService(context, 1, startPlayerIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        //Создание интента для остановки плеера!
        val stopPlayerIntent = Intent(context, BackgroundSoundService::class.java)
        stopPlayerIntent.putExtra("intention", "toStop")
        val piStop = PendingIntent.getService(context, 2, stopPlayerIntent, PendingIntent.FLAG_UPDATE_CURRENT)


        //Создание интента для остановки сервиса!
        val stopServiceIntent = Intent(context, BackgroundSoundService::class.java)
        stopServiceIntent.putExtra("intention", "toBreak")
        val piStopService = PendingIntent.getService(context, 3, stopServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        //Создание интента для перехода в MainActivity по нажатию на notification.
        val notificationIntentMainAct = Intent(context, MainActivity::class.java)
        notificationIntentMainAct.putExtra("notification", true)
        val piMainAct = PendingIntent.getActivity(context, 4, notificationIntentMainAct, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, getChannelId("my_channel_id", "My default Channel", "my_group_id", "My default Group"))
                .setContentIntent(piMainAct)
                .setSmallIcon(R.drawable.player)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.player))
                .addAction(NotificationCompat.Action.Builder(R.drawable.navigation_empty_icon, "play", piStart).build())
                .addAction(NotificationCompat.Action.Builder(R.drawable.navigation_empty_icon, "pause", piStop).build())// #0)
                .addAction(NotificationCompat.Action.Builder(R.drawable.navigation_empty_icon, "stop", piStopService).build())// #0)
                .setAutoCancel(false)
                .setContentTitle("Player")
                .setContentText("chosen url")
                .setColor(245)


        val notification = builder.build()
        return notification
    }


    fun restart(){
        player.start()
        isPlaing = true
    }

    fun start(){
        Toast.makeText(this, " Connecting... ", Toast.LENGTH_LONG).show()
/*     val listener = object : PlayerStateListener {
         override fun onPlayerPrepared() {
             TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
         }
     }*/
     val r = object : Runnable {
         override fun run() {
             preparePlayer()
             player.start()
             isPlaing = true
         }
     }

     val t = Thread(r)
     t.start()
 }

 fun stop(){
     player.pause()
     // player.release()
    // Toast.makeText(this, " Disconnecting... ", Toast.LENGTH_SHORT).show()
     isPlaing = false
 }


 // Подготовка плеера к проигрыванию.
 fun preparePlayer(){
     player = MediaPlayer()
     player.setDataSource(mUrl)
     player.isLooping = true
     player.setVolume(100f, 100f)

     if (Build.VERSION.SDK_INT >= 21) {
         val audioAttributes = AudioAttributes.Builder()
                 .setUsage(AudioAttributes.USAGE_MEDIA).
                         setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
         player.setAudioAttributes(audioAttributes)
         player.prepare();
     }else{
         player.setAudioStreamType(AudioManager.STREAM_MUSIC);
         // This method was deprecated in API level 26, я использую альтернативу (выше), которая доступна с API 21.
         player.prepare();
     }
 }


 private fun getChannelId(channelId: String, name: String, groupId: String, groupName: String) : String {
     return when (Build.VERSION.SDK_INT) {
         Build.VERSION_CODES.O -> getChannelIdInternal(channelId, name, groupId, groupName)
         else ->  ""
     }
 }


 @TargetApi(Build.VERSION_CODES.O)
 private fun getChannelIdInternal(channelId: String, name: String, groupId: String, groupName: String): String {

     val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
     val channels = nm.notificationChannels
     for (channel in channels) {
         if (channel.id == channelId) {
             return channel.id
         }
     }

     val group = getNotificationChannelGroupId(groupId, groupName)
     val importance = NotificationManager.IMPORTANCE_HIGH
     val notificationChannel = NotificationChannel(channelId, name, importance)
     notificationChannel.enableLights(true)
     notificationChannel.lightColor = Color.RED
     notificationChannel.enableVibration(true)
     notificationChannel.group = group // set custom group
     nm.createNotificationChannel(notificationChannel)

     return channelId
 }


 private fun getNotificationChannelGroupId(groupId: String, name: String): String {
     return when (Build.VERSION.SDK_INT) {
         Build.VERSION_CODES.O -> getNotificationChannelGroupIdInternal(groupId, name)
         else ->  ""
     }
 }

 @TargetApi(Build.VERSION_CODES.O)
 private fun getNotificationChannelGroupIdInternal(groupId: String, name: String): String {
     val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
     val groups = nm.notificationChannelGroups
     for (group in groups) {
         if (group.id == groupId) {
             return group.id
         }
     }
     nm.createNotificationChannelGroup(NotificationChannelGroup(groupId, name))
     return groupId
 }

 interface PlayerStateListener {
     fun onPlayerPrepared();
 }

}

