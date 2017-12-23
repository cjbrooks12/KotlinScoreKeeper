package com.caseyjbrooks.scorekeeper.core.db.users

import android.arch.persistence.room.*

@Dao
interface GameUserDAO {

    @Query("select * from game_users where game_id = :gameId")
    fun getAllForGame(gameId: Long): List<GameUser>

    @Query("select * from game_users where user_id = :userId")
    fun getAllForUser(userId: Long): List<GameUser>

    @Query("select * from game_users where game_id = :gameId and user_id = :userId")
    fun findGameUser(gameId: Long, userId: Long): GameUser

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameUser(gameUser: GameUser)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateGameUser(gameUser: GameUser)

    @Query("delete from game_users where game_id = :gameId and user_id = :userId")
    fun removeUserFromGame(gameId: Long, userId: Long)

    @Query("delete from game_users where user_id = :userId")
    fun deleteByUser(userId: Long)

    @Query("delete from game_users where game_id = :gameId")
    fun deleteByGame(gameId: Long)

}
