package com.caseyjbrooks.scorekeeper.core.db.users

class Game(var gameType: String, var gameData: String) {
    constructor() : this("", "")

    var id: String = ""
}
