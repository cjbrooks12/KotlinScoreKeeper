package com.caseyjbrooks.scorekeeper.core.db.firebase

import com.caseyjbrooks.scorekeeper.core.db.users.User
import com.caseyjbrooks.scorekeeper.core.db.users.UserDAO
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.ExecutionException


class FBUserDao(val firebase: CoreFirebaseDB, val ref: DatabaseReference) : UserDAO {

    override fun getAllUsers(): List<User> {
        val tcs = TaskCompletionSource<List<User>>()
        ref
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val users = ArrayList<User>()

                        for (userSnapshot in dataSnapshot.children) {
                            val user = userSnapshot.getValue(User::class.java)!!
                            users.add(user)
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

    override fun getAllIn(ids: List<String>): List<User> {
        val tcs = TaskCompletionSource<List<User>>()
        ref
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val users = ArrayList<User>()

                        for (userSnapshot in dataSnapshot.children) {
                            val user = userSnapshot.getValue(User::class.java)!!
                            if(ids.any { it == user.id }) { users.add(user) }
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

    override fun getAllNotIn(ids: List<String>): List<User> {
        val tcs = TaskCompletionSource<List<User>>()
        ref
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val users = ArrayList<User>()
                        for (userSnapshot in dataSnapshot.children) {
                            val user = userSnapshot.getValue(User::class.java)!!
                            if(!ids.any { it == user.id }) { users.add(user) }
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

    override fun findUserById(id: String): User? {
        val tcs = TaskCompletionSource<User>()
        ref.child(id)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        tcs.setResult(dataSnapshot.getValue(User::class.java))
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

        return null
    }

    override fun insertUser(user: User): String {
        val pushed = ref.push()
        pushed.setValue(user)
        user.id = pushed.key
        updateUser(user)
        return user.id
    }

    override fun updateUser(user: User) {
        ref.child(user.id).setValue(user)
    }

    override fun deleteUser(user: User) {
        ref.child(user.id).removeValue()
    }

}
