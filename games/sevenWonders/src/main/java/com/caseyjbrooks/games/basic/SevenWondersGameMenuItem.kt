package com.caseyjbrooks.games.basic

import android.os.Bundle
import android.view.Menu
import com.caseyjbrooks.games.sevenWonders.R
import com.caseyjbrooks.scorekeeper.core.DrawerMenuItem
import com.caseyjbrooks.scorekeeper.core.api.BaseFragment

class SevenWondersGameMenuItem : DrawerMenuItem {

    override fun getTitle(): String {
        return "Seven Wonders"
    }

    override fun getGroup(): Int {
        return Menu.NONE
    }

    override fun getId(): Int {
        return 3
    }

    override fun getOrder(): Int {
        return 2
    }

    override fun getIcon(): Int {
        return R.drawable.ic_seven_wonders
    }

    override fun getGroupName(): String? {
        return "Special Games"
    }

    override fun getFragment(): Pair<Class<out BaseFragment>, Bundle?> {
        return SevenWondersGameFragment::class.java to null
    }

}
