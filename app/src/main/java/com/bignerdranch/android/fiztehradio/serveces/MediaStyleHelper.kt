package com.bignerdranch.android.fiztehradio.serveces

import android.content.Context
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.media.session.MediaButtonReceiver
import com.bignerdranch.android.fiztehradio.R
import android.graphics.BitmapFactory

internal object MediaStyleHelper {
    /**
     * Build a notification using the information from the given media session. Makes heavy use
     * of [MediaMetadataCompat.getDescription] to extract the appropriate information.
     *
     * @param context      Context used to construct the notification.
     * @param mediaSession Media session to get information.
     * @return A pre-built notification with information from the given media session.
     */
    fun from(
            context: Context, mediaSession: MediaSessionCompat): NotificationCompat.Builder {
        val controller = mediaSession.controller
        //val mediaMetadata = controller.metadata
        //val description = mediaMetadata.description

        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.player)
        val builder = NotificationCompat.Builder(context)
        builder
                .setLargeIcon(largeIcon)
                .setContentIntent(controller.sessionActivity)
                .setDeleteIntent(
                        MediaButtonReceiver.buildMediaButtonPendingIntent(context, PlaybackStateCompat.ACTION_STOP))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//        builder
//                .setContentTitle(description.title)
//                .setContentText(description.subtitle)
//                .setSubText(description.description)
//                .setLargeIcon(description.iconBitmap)
//                .setContentIntent(controller.sessionActivity)
//                .setDeleteIntent(
//                        MediaButtonReceiver.buildMediaButtonPendingIntent(context, PlaybackStateCompat.ACTION_STOP))
//                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        return builder
    }
}