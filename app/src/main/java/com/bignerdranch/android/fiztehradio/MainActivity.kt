package com.bignerdranch.android.fiztehradio


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.bignerdranch.android.fiztehradio.fragments.MainFragment
import android.R.attr.fragment
import android.view.View
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem







class MainActivity : AppCompatActivity() {

    lateinit var router  : Router // Вспомогательный класс для переключения между фрагментами.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        router = Router(this, R.id.fragment_container)
        if (savedInstanceState == null) router.navigateTo(false, ::MainFragment)// переключаемся на MainFragment

        val navDrawer = initDrawerItems()
    }

    fun initDrawerItems(): Drawer {
        // Navigation Drawer
        DrawerBuilder().withActivity(this).build()

        val item1 = PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home).withBadge("19")
        val item2 = SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_settings)

        val result = DrawerBuilder()
                .withActivity(this)
                .addDrawerItems(
                        item1,
                        DividerDrawerItem(),
                        item2
                )
                .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                    override fun onItemClick(view: View, position: Int, drawerItem: IDrawerItem<*, *>): Boolean {
                        // do something with the clicked item :D
                        return false
                    }
                })
                .build()
        return result
    }

    override fun onBackPressed() { // Если не можем вернуться к фрагменту, закрываем активити. см класс Router
        if (!router.navigateBack()) {
            super.onBackPressed()
        }
    }
}

