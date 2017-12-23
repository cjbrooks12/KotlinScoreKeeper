package com.caseyjbrooks.scorekeeper.core.db.users

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE

@Dao
interface UserDAO {

    @Query("select * from users")
    fun getAllUsers(): List<User>

    @Query("select * from users where id in (:ids)")
    fun getAllIn(ids: List<Long>): List<User>

    @Query("select * from users where id not in (:ids)")
    fun getAllNotIn(ids: List<Long>): List<User>

    @Query("select * from users where id = :id")
    fun findUserById(id: Long): User

    @Insert(onConflict = REPLACE)
    fun insertUser(user: User): Long

    @Update(onConflict = REPLACE)
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

}
