package com.caseyjbrooks.scorekeeper.core

import com.caseyjbrooks.scorekeeper.core.api.BaseFragment

interface DrawerMenuItem {

    fun getTitle(): String

    fun getGroup(): Int

    fun getId(): Int

    fun getOrder(): Int

    fun getIcon(): Int

    fun getGroupName(): String?

    fun getFragment(): BaseFragment

}
