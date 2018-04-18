package com.caseyjbrooks.scorekeeper.core

import android.content.Context
import com.caseyjbrooks.scorekeeper.core.api.BaseApplication
import com.caseyjbrooks.scorekeeper.core.db.firebase.CoreFirebaseDB
import dagger.Module
import dagger.Provides

@Module
class CoreModule(val app: BaseApplication) {

    @Provides
    fun provideMainApplication(): BaseApplication = app

    @Provides
    fun provideContext(): Context = app

    @Provides
    fun providesAppFirebase(context: Context): CoreFirebaseDB {
        return CoreFirebaseDB()
    }

}
