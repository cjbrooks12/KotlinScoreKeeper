package com.caseyjbrooks.scorekeeper.core.db.users

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "games")
data class Game(
        @ColumnInfo(name = "game_type") var gameType: String,
        @ColumnInfo(name = "data") var data: String
) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}
