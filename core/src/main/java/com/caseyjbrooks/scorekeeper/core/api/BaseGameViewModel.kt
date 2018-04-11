package com.caseyjbrooks.scorekeeper.core.api

import com.caseyjbrooks.scorekeeper.core.db.CorePreferences
import com.caseyjbrooks.scorekeeper.core.db.users.Game
import com.caseyjbrooks.scorekeeper.core.db.users.GameUser
import com.caseyjbrooks.scorekeeper.core.db.users.User
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.customView
import org.jetbrains.anko.editText
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.yesButton
import org.json.JSONObject

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

    protected abstract fun mapper(user: User, gameUser: GameUser): T

    protected open fun reloadData() {
        val game: Game? = component.db().gameDao().findGameById(gameType, gameId)
        if(game != null) {
            this.game = game
        }
        else {
            val newGame = Game(gameType, "{}")
            val newGameId = component.db().gameDao().insertGame(newGame)

            CorePreferences(activity, gameType).set { putLong("lastGame", newGameId) }

            this.gameId = newGameId
            this.game = component.db().gameDao().findGameById(gameType, newGameId)
        }

        val data = JSONObject(this.game.gameData)
        initFromData(data)

        val gameUsers =  component.db().gameUserDao().getAllForGame(this.gameId)
        val gameUserIds = gameUsers.map { it.userId }

        val gameUserMap: MutableMap<Long, GameUser> = mutableMapOf()
        gameUsers.forEach { gameUserMap[it.userId] = it }

        adapter.users = component.db().userDao().getAllIn(gameUserIds).map { mapper(it, gameUserMap[it.id]!!) }
        adapter.notifyDataSetChanged()
    }

// Save Game State
//--------------------------------------------------------------------------------------------------

    abstract fun initFromData(savedData: JSONObject)

    abstract fun getSaveData() : JSONObject

    fun saveGame() {
        val data = getSaveData()
        game.gameData = data.toString()
        component.db().gameDao().updateGame(game)
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

        selector("Add Existing User?", users.map { it.name }, { _, i ->
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

    fun <UI> changeLayout(ui: AnkoContext<UI>) = with(ui) {
        val layouts = listOf("List (Comfy)", "List (Compact)", "Grid")
        selector("Pick Layout", layouts, { dialogInterface, i ->
            CorePreferences(activity, gameType).set { putString("layout", layouts[i]) }
            activity.hardRefresh()
        })
    }

    val layout: String
        get() {
            return CorePreferences(activity, gameType).get { getString("layout", "List (Comfy)") }
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
