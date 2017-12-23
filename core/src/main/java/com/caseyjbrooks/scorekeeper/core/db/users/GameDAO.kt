package com.caseyjbrooks.scorekeeper.core.db.users

import android.arch.persistence.room.*

@Dao
interface GameDAO {

    @Query("select * from games")
    fun getAllGames(): List<Game>

    @Query("select * from games where game_type = :gameType and id = :id")
    fun findGameById(gameType: String, id: Long): Game

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: Game): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateGame(game: Game)

    @Delete
    fun deleteGame(game: Game)

}
