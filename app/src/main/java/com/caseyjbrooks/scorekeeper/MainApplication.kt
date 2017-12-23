package com.caseyjbrooks.scorekeeper

import com.caseyjbrooks.scorekeeper.core.CoreModule
import com.caseyjbrooks.scorekeeper.core.api.BaseApplication
import com.caseyjbrooks.scorekeeper.core.api.BaseComponent

class MainApplication : BaseApplication() {

    override val component: BaseComponent by lazy {
        DaggerMainComponent
                .builder()
                .coreModule(CoreModule(this))
                .build()
    }

}
