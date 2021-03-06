package com.caseyjbrooks.games.basic

import android.os.Bundle
import android.view.Menu
import com.caseyjbrooks.games.ticketToRide.R
import com.caseyjbrooks.scorekeeper.core.DrawerMenuItem
import com.caseyjbrooks.scorekeeper.core.HomescreenFragment
import com.caseyjbrooks.scorekeeper.core.api.BaseFragment

class TicketToRideGameMenuItem : DrawerMenuItem {

    override fun getTitle(): String {
        return "Ticket To Ride"
    }

    override fun getGroup(): Int {
        return Menu.NONE
    }

    override fun getId(): Int {
        return 2
    }

    override fun getOrder(): Int {
        return 3
    }

    override fun getIcon(): Int {
        return R.drawable.ic_ticket_to_ride
    }

    override fun getGroupName(): String? {
        return "Special Games"
    }

    override fun getFragment(): Pair<Class<out BaseFragment>, Bundle?> {
        var args = Bundle()
        args.putString("text", "Ticket To Ride fragment")
        return HomescreenFragment::class.java to args
    }

}
