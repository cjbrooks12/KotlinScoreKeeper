package com.caseyjbrooks.scorekeeper.core.db.users

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "game_users")
data class GameUser(
        @ColumnInfo(name = "game_id") var gameId: Long,
        @ColumnInfo(name = "user_id") var userId: Long,
        @ColumnInfo(name = "data") var gameData: String
) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}
