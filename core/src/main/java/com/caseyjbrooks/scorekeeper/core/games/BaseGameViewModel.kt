package com.caseyjbrooks.scorekeeper.core.games

import com.caseyjbrooks.scorekeeper.core.api.BaseActivity
import com.caseyjbrooks.scorekeeper.core.api.BaseComponent
import com.caseyjbrooks.scorekeeper.core.db.users.Game
import com.caseyjbrooks.scorekeeper.core.db.users.GameUser
import com.caseyjbrooks.scorekeeper.core.db.users.User
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat

abstract class BaseGameViewModel<T>(
        val activity: BaseActivity,
        val component: BaseComponent,
        val gameType: String,
        var gameId: Long
) {

    lateinit var game: Game
    lateinit var adapter: BaseGameAdapter<T>

    open fun setup() {
        reloadData()
    }

    protected abstract fun mapper(user: User): T

    protected open fun reloadData() {
        val game: Game? = component.db().gameDao().findGameById(gameType, gameId)
        if(game != null) {
            this.game = game
        }
        else {
            val newGame = Game(gameType, "{}")
            val newGameId = component.db().gameDao().insertGame(newGame)

            this.gameId = newGameId
            this.game = component.db().gameDao().findGameById(gameType, newGameId)
        }

        val gameUserIds = component.db().gameUserDao().getAllForGame(this.gameId).map { it.userId }
        adapter.users = component.db().userDao().getAllIn(gameUserIds).map { mapper(it) }
        adapter.notifyDataSetChanged()
    }

// Game Dialogs
//--------------------------------------------------------------------------------------------------

    fun <UI> addNewUser(ui: AnkoContext<UI>) = with(ui) {
        alert(Appcompat, title = "Add New User", message = "") {
            customView {
                verticalLayout {
                    val name = editText { hint = "Name" }
                    positiveButton("Save") {
                        if(name.text.isNotEmpty()) {
                            addNewUser(name.text.toString())
                            toast("Created " + name.text.toString())
                        }
                        else {
                            toast("Name cannot be empty")
                        }
                    }
                    negativeButton("Cancel") { }
                }
            }
        }.show()
    }

    fun <UI> addExistingUser(ui: AnkoContext<UI>) = with(ui) {
        val gameUsers = component.db().gameUserDao().getAllForGame(gameId)
        val gameUserIds = gameUsers.map { it.userId }
        val users = component.db().userDao().getAllNotIn(gameUserIds)

        selector("Add Existing User?", users.map { it.name }, { dialogInterface, i ->
            toast("adding ${users[i].name}")
            addExistingUser(users[i])
        })
    }

    fun <UI> renameUser(ui: AnkoContext<UI>, user: User) = with(ui) {
        alert(Appcompat, title = "Rename ${user.name}", message = "") {
            customView {
                verticalLayout {
                    val name = editText { hint = "Name" }
                    positiveButton("Save") {
                        if(name.text.isNotEmpty()) {
                            user.name = name.text.toString()
                            updateUser(user)
                            toast("Name changed to " + user.name)
                        }
                        else {
                            toast("Name cannot be empty")
                        }
                    }
                    negativeButton("Cancel") { }
                }
            }
        }.show()
    }

    fun <UI> removeUser(ui: AnkoContext<UI>, user: User) = with(ui) {
        alert(Appcompat, title = "Remove ${user.name}", message = "You may either remove ${user.name} from this game, or permanently delete ${user.name} and all their data") {
            positiveButton("Remove") {
                removeUserFromGame(user)
            }
            negativeButton("Cancel") {

            }
            neutralPressed("Delete") {
                deleteUser(user)
            }
        }.show()
    }

    fun <UI> resetGame(ui: AnkoContext<UI>) = with(ui) {
        alert(Appcompat, title = "Reset game?", message = "This will reset the game to its initial state. Continue?") {
            yesButton {
                resetGame()
                toast("Game Reset")
            }
            cancelButton { }
        }.show()
    }

// Handle Dialog Actions
//--------------------------------------------------------------------------------------------------

    protected open fun addNewUser(name: String) {
        val newUser = User(name)
        val newUserId = component.db().userDao().insertUser(newUser)

        val gameUser = GameUser(this.gameId, newUserId, "{}")
        component.db().gameUserDao().insertGameUser(gameUser)

        reloadData()
    }

    protected open fun addExistingUser(user: User) {
        val gameUser = GameUser(this.gameId, user.id, "{}")
        component.db().gameUserDao().insertGameUser(gameUser)
        reloadData()
    }

    protected open fun updateUser(user: User) {
        component.db().userDao().updateUser(user)
        reloadData()
    }

    protected open fun removeUserFromGame(user: User) {
        component.db().gameUserDao().removeUserFromGame(gameId, user.id)
        reloadData()
    }

    protected open fun deleteUser(user: User) {
        component.db().gameUserDao().deleteByUser(user.id)
        component.db().userDao().deleteUser(user)
        reloadData()
    }

    protected open fun resetGame() {
        reloadData()
    }

}
