package com.caseyjbrooks.scorekeeper.core.db.users

interface GameDAO {

    fun getAllGames(): List<Game>

    fun findGameById(gameType: String, id: String): Game?

    fun insertGame(game: Game): String

    fun updateGame(game: Game)

    fun deleteGame(game: Game)

}
