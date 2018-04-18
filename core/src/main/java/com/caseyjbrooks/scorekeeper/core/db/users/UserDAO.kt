package com.caseyjbrooks.scorekeeper.core.db.users

interface UserDAO {

    fun getAllUsers(): List<User>

    fun getAllIn(ids: List<String>): List<User>

    fun getAllNotIn(ids: List<String>): List<User>

    fun findUserById(id: String): User?

    fun insertUser(user: User): String

    fun updateUser(user: User)

    fun deleteUser(user: User)

}
