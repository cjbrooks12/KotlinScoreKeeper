package com.caseyjbrooks.scorekeeper.core.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.caseyjbrooks.scorekeeper.core.db.users.*


@Database(
        entities = [
            User::class,
            Game::class,
            GameUser::class
        ],
        version = 2,
        exportSchema = false
)
abstract class CoreRoomDB : RoomDatabase() {

    abstract fun userDao(): UserDAO

    abstract fun gameDao(): GameDAO

    abstract fun gameUserDao(): GameUserDAO

}
