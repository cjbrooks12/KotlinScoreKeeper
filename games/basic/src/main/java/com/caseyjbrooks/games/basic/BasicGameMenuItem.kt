package com.caseyjbrooks.games.basic

import android.view.Menu
import com.caseyjbrooks.scorekeeper.core.DrawerMenuItem
import com.caseyjbrooks.scorekeeper.core.api.BaseFragment

class BasicGameMenuItem : DrawerMenuItem {

    override fun getTitle(): String {
        return "Tally"
    }

    override fun getGroup(): Int {
        return Menu.NONE
    }

    override fun getId(): Int {
        return 1
    }

    override fun getOrder(): Int {
        return 1
    }

    override fun getIcon(): Int {
        return R.drawable.ic_tally
    }

    override fun getGroupName(): String? {
        return "Basic Games"
    }

    override fun getFragment(): BaseFragment {
        return TallyGameFragment()
    }

}
