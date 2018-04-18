package com.caseyjbrooks.scorekeeper.core.db.firebase

import com.caseyjbrooks.scorekeeper.core.db.users.Game
import com.caseyjbrooks.scorekeeper.core.db.users.GameDAO
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.ExecutionException

class FBGameDao(val firebase: CoreFirebaseDB, val ref: DatabaseReference) : GameDAO {

    override fun getAllGames(): List<Game> {
        return emptyList()
    }

    override fun findGameById(gameType: String, id: String): Game? {
        val tcs = TaskCompletionSource<Game>()
        ref.child(id)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        tcs.setResult(dataSnapshot.getValue(Game::class.java))
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

    override fun insertGame(game: Game): String {
        val pushed = ref.push()
        pushed.setValue(game)
        game.id = pushed.key
        updateGame(game)
        return game.id
    }

    override fun updateGame(game: Game) {
        ref.child(game.id).setValue(game)
    }

    override fun deleteGame(game: Game) {
        ref.child(game.id).removeValue()
    }

}
