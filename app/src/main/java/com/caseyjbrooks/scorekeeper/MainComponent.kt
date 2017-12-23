package com.caseyjbrooks.scorekeeper

import com.caseyjbrooks.games.basic.BasicGameModule
import com.caseyjbrooks.games.basic.SevenWondersGameModule
import com.caseyjbrooks.games.basic.TicketToRideGameModule
import com.caseyjbrooks.scorekeeper.core.CoreModule
import com.caseyjbrooks.scorekeeper.core.api.BaseComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        CoreModule::class,
        BasicGameModule::class,
        SevenWondersGameModule::class,
        TicketToRideGameModule::class
))
interface MainComponent : BaseComponent
