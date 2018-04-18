package com.caseyjbrooks.scorekeeper.core.db.users

class GameUser(var gameId: String, var userId: String, var gameData: String) {
    constructor() : this("", "", "")

    var id: String = ""
}
