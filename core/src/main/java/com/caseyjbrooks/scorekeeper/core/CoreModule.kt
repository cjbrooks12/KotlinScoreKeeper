package com.caseyjbrooks.scorekeeper.core

import android.arch.persistence.room.Room
import android.content.Context
import com.caseyjbrooks.scorekeeper.core.api.BaseApplication
import com.caseyjbrooks.scorekeeper.core.db.CoreRoomDB
import dagger.Module
import dagger.Provides

@Module
class CoreModule(val app: BaseApplication) {

    @Provides
    fun provideMainApplication(): BaseApplication = app

    @Provides
    fun provideContext(): Context = app

    @Provides
    fun providesAppDatabase(context: Context): CoreRoomDB =
            Room.databaseBuilder(context, CoreRoomDB::class.java, "scorekeeper-db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

}
