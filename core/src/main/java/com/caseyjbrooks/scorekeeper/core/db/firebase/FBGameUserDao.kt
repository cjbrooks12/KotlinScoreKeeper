package com.caseyjbrooks.scorekeeper.core.db.firebase

import com.caseyjbrooks.scorekeeper.core.db.users.GameUser
import com.caseyjbrooks.scorekeeper.core.db.users.GameUserDAO
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.ExecutionException

class FBGameUserDao(val firebase: CoreFirebaseDB, val gameRef: DatabaseReference, val userRef: DatabaseReference) : GameUserDAO {

    override fun getAllForGame(gameId: String): List<GameUser> {
        val tcs = TaskCompletionSource<List<GameUser>>()
        gameRef.child(gameId).child("users")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val users = ArrayList<GameUser>()

                        for (userSnapshot in dataSnapshot.children) {
                            val user = userSnapshot.getValue(GameUser::class.java)!!
                            if (user.gameId == gameId) {
                                users.add(user)
                            }
                        }
                        tcs.setResult(users)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        tcs.setException(databaseError.toException())
                    }
                })

        var t = tcs.task
        try {
            Tasks.await(t)
        } catch (e: ExecutionException) {
            t = Tasks.forException(e)
        } catch (e: InterruptedException) {
            t = Tasks.forException(e)
        }

        if (t.isSuccessful) {
            return t.result
        }

        return emptyList()
    }

    override fun getAllForUser(userId: String): List<GameUser> {
        val tcs = TaskCompletionSource<List<GameUser>>()
        userRef.child(userId).child("games")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val gameUsers = ArrayList<GameUser>()
                        gameRef
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        for (userSnapshot in dataSnapshot.children) {
                                            val gameUser = userSnapshot.getValue(GameUser::class.java)!!
                                            if (dataSnapshot.children.any { it.key == userId }) {
                                                gameUsers.add(gameUser)
                                            }
                                        }
                                        tcs.setResult(gameUsers)
                                    }

                                    override fun onCancelled(databaseError: DatabaseError) {
                                        tcs.setException(databaseError.toException())
                                    }
                                })
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        tcs.setException(databaseError.toException())
                    }
                })

        var t = tcs.task
        try {
            Tasks.await(t)
        } catch (e: ExecutionException) {
            t = Tasks.forException(e)
        } catch (e: InterruptedException) {
            t = Tasks.forException(e)
        }

        if (t.isSuccessful) {
            return t.result
        }

        return emptyList()
    }

    override fun findGameUser(gameId: String, userId: String): GameUser? {
        val tcs = TaskCompletionSource<GameUser>()
        gameRef.child(gameId).child("users").child(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        tcs.setResult(dataSnapshot.getValue(GameUser::class.java))
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        tcs.setException(databaseError.toException())
                    }
                })

        var t = tcs.task
        try {
            Tasks.await(t)
        } catch (e: ExecutionException) {
            t = Tasks.forException(e)
        } catch (e: InterruptedException) {
            t = Tasks.forException(e)
        }

        if (t.isSuccessful) {
            return t.result
        }
//
        return null
    }

    override fun insertGameUser(gameUser: GameUser) {
        gameRef.child(gameUser.gameId).child("users").child(gameUser.userId).setValue(gameUser)
        userRef.child(gameUser.userId).child("games").child(gameUser.gameId).setValue(true)
    }

    override fun updateGameUser(gameUser: GameUser) {
        gameRef.child(gameUser.gameId).child("users").child(gameUser.userId).setValue(gameUser)
    }

    override fun removeUserFromGame(gameId: String, userId: String) {
        gameRef.child(gameId).child("users").child(userId).removeValue()
        userRef.child(userId).child("games").child(gameId).removeValue()
    }

    override fun deleteByUser(userId: String) {
        userRef.child(userId).child("games")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (gameIdSnapshot in dataSnapshot.children) {
                            gameRef.child(gameIdSnapshot.key).child("users").child(userId).removeValue()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                    }
                })
    }

    override fun deleteByGame(gameId: String) {
        getAllForGame(gameId).forEach {
            removeUserFromGame(it.gameId, it.userId)
        }
    }

}
