package com.caseyjbrooks.games.basic

import com.caseyjbrooks.scorekeeper.core.DrawerMenuItem
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class TallyGameModule {

    @Provides @IntoSet
    fun provideBasicGameMenuItem(): DrawerMenuItem {
        return TallyGameMenuItem()
    }

}
