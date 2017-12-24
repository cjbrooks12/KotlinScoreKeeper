package com.caseyjbrooks.scorekeeper.core.api

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.caseyjbrooks.scorekeeper.core.R
import com.caseyjbrooks.scorekeeper.core.db.CorePreferences
import com.caseyjbrooks.scorekeeper.core.findInt
import com.caseyjbrooks.scorekeeper.core.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick

abstract class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var component: BaseComponent
    private lateinit var fragment: BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = (application as BaseApplication).component
        component.inject(this)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        fab.onClick { fragment.onFabClicked() }

        setupMenu()

        val menuItemId = CorePreferences(this, "core").get {
            findInt("lastMenuItem", { component.gamesDrawerMenuItems().first().getId() })
        }
        selectMenuItem(menuItemId)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectMenuItem(item.itemId)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun selectMenuItem(id: Int) {
        var matchingItem = component.gamesDrawerMenuItems().find { it.getId() == id }

        if(matchingItem != null) {
            val fragmentClassPair = matchingItem.getFragment()
            fragment = fragmentClassPair.first.newInstance()
            fragment.arguments = fragmentClassPair.second
            this.replaceFragment(fragment, R.id.container)
            fab.hide()

            if(fragment.hasFab()) {
                fab.show()
                fab.setImageDrawable(resources.getDrawable(fragment.getFabIcon()!!, theme))
            }

            CorePreferences(this, "core").set {
                putInt("lastMenuItem", id)
            }
        }
    }

// Setup Menu
//--------------------------------------------------------------------------------------------------

    fun setupMenu() {
        var items = component.gamesDrawerMenuItems()
        var groups: Array<String?> = items.map { it.getGroupName() }.distinct().toTypedArray()
        groups.sort()

        groups.forEach { groupName ->
            val submenu = nav_view.menu.addSubMenu(Menu.NONE, Menu.NONE, Menu.NONE, groupName?:"")
            items.filter { it.getGroupName() == groupName }.forEach {
                var item = submenu.add(it.getGroup(), it.getId(), it.getOrder(), it.getTitle())
                item.setIcon(it.getIcon())
                item.setCheckable(true)
            }
        }
    }

}
