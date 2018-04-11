package com.caseyjbrooks.games.basic

import com.caseyjbrooks.scorekeeper.core.DrawerMenuItem
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet

@Module
class SevenWondersGameModule {

    private val enabled
        get() = true

    @Provides @ElementsIntoSet
    fun provideSevenWondersGameMenuItem(): Set<DrawerMenuItem> {
        return if(enabled) setOf(SevenWondersGameMenuItem()) else emptySet()
    }

}
