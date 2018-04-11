package com.caseyjbrooks.games.basic

import com.caseyjbrooks.scorekeeper.core.DrawerMenuItem
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet

@Module
class TicketToRideGameModule {

    private val enabled
        get() = true

    @Provides @ElementsIntoSet
    fun provideBasicGameMenuItem(): Set<DrawerMenuItem> {
        return if(enabled) setOf(TicketToRideGameMenuItem()) else emptySet()
    }

}
