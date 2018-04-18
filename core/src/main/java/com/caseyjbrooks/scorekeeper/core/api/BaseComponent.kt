package com.caseyjbrooks.scorekeeper.core.api

import com.caseyjbrooks.scorekeeper.core.DrawerMenuItem
import com.caseyjbrooks.scorekeeper.core.db.firebase.CoreFirebaseDB

interface BaseComponent {

    fun inject(obj: Any)

    fun gamesDrawerMenuItems(): Set<DrawerMenuItem>

    fun db(): CoreFirebaseDB

}
