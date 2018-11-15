package com.bignerdranch.android.fiztehradio


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.bignerdranch.android.fiztehradio.fragments.MainFragment
import android.R.attr.fragment
import android.view.View
import com.bignerdranch.android.fiztehradio.fragments.HostListFragment
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.AccountHeaderBuilder


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
        val item2 = SecondaryDrawerItem().withIdentifier(2).withName(R.string.hosts)

        // Account header
        val headerResult = AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(
                        // Отдельный профиль
                        ProfileDrawerItem()
                                .withName("Физтех Радио")
                                .withEmail("phystech-radio@phystech.radio")
                                .withIcon(getResources().getDrawable(R.drawable.background_splash))
                )
                .withOnAccountHeaderListener { view, profile, currentProfile -> false }
                .build()


        val result = DrawerBuilder()
                .withActivity(this)
                .addDrawerItems(
                        item1,
                        DividerDrawerItem(),
                        item2
                )
                .withAccountHeader(headerResult)
                .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                    override fun onItemClick(view: View, position: Int, drawerItem: IDrawerItem<*, *>): Boolean {
                        // do something with the clicked item :D
                        if (position == 1) {
                            // Выбрал пункт "Радио"
                            router.navigateTo(false, ::MainFragment)
                        } else if (position == 3) {
                            // Выбран пункт "Ведущие"
                            // Из-за разделителя для него vposition == 3
                            router.navigateTo(true, ::HostListFragment)
                        }

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

