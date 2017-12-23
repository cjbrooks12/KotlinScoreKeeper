package com.caseyjbrooks.scorekeeper.core.api

import com.caseyjbrooks.scorekeeper.core.DrawerMenuItem
import com.caseyjbrooks.scorekeeper.core.db.CoreRoomDB

interface BaseComponent {

    fun inject(obj: Any)

    fun gamesDrawerMenuItems(): Set<DrawerMenuItem>

    fun db(): CoreRoomDB

}
