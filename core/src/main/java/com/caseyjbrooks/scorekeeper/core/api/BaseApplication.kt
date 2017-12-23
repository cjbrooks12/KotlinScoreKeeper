package com.caseyjbrooks.scorekeeper.core.api

import android.app.Application

abstract class BaseApplication : Application() {

    abstract val component: BaseComponent

}
