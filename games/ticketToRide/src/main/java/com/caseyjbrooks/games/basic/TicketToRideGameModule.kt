package com.caseyjbrooks.games.basic

import com.caseyjbrooks.scorekeeper.core.DrawerMenuItem
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class TicketToRideGameModule {

    @Provides @IntoSet
    fun provideBasicGameMenuItem(): DrawerMenuItem {
        return TicketToRideGameMenuItem()
    }

}
