package com.caseyjbrooks.scorekeeper.core.db.firebase

import com.caseyjbrooks.scorekeeper.core.db.users.GameDAO
import com.caseyjbrooks.scorekeeper.core.db.users.GameUserDAO
import com.caseyjbrooks.scorekeeper.core.db.users.UserDAO
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CoreFirebaseDB {

    private var db: FirebaseDatabase? = null
    private lateinit var userRef: DatabaseReference
    private lateinit var gameRef: DatabaseReference

    private var userDao: FBUserDao? = null
    private var gameDao: FBGameDao? = null
    private var gameUserDao: FBGameUserDao? = null

    private fun connect() {
        if(db == null) {
            db = FirebaseDatabase.getInstance()
//            db!!.setPersistenceEnabled(true)

            userRef     = db!!.getReference("users")
            gameRef     = db!!.getReference("games")
        }
    }

    fun userDao(): UserDAO {
        if(userDao == null) {
            connect()
            userDao = FBUserDao(this, userRef)
        }
        return userDao!!
    }

    fun gameDao(): GameDAO {
        if(gameDao == null) {
            connect()
            gameDao = FBGameDao(this, gameRef)
        }
        return gameDao!!
    }

    fun gameUserDao(): GameUserDAO {
        if(gameUserDao == null) {
            connect()
            gameUserDao = FBGameUserDao(this, gameRef, userRef)
        }
        return gameUserDao!!
    }

}
