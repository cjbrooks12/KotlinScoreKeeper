package com.caseyjbrooks.scorekeeper.core.db.users

interface GameUserDAO {

    fun getAllForGame(gameId: String): List<GameUser>

    fun getAllForUser(userId: String): List<GameUser>

    fun findGameUser(gameId: String, userId: String): GameUser?

    fun insertGameUser(gameUser: GameUser)

    fun updateGameUser(gameUser: GameUser)

    fun removeUserFromGame(gameId: String, userId: String)

    fun deleteByUser(userId: String)

    fun deleteByGame(gameId: String)

}
