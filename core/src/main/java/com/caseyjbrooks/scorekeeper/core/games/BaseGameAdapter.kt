package com.caseyjbrooks.scorekeeper.core.games

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.caseyjbrooks.scorekeeper.core.api.BaseActivity
import com.caseyjbrooks.scorekeeper.core.api.BaseComponent

abstract class BaseGameAdapter<T>(
        val activity: BaseActivity,
        val component: BaseComponent,
        val gameViewModel: BaseGameViewModel<T>
) : BaseAdapter() {

    var users: List<T> = emptyList()

    abstract override fun getView(i : Int, v : View?, parent : ViewGroup?) : View

    abstract override fun getItemId(position : Int) : Long

    override fun getItem(position : Int) : T {
        return users[position]
    }

    override fun getCount() : Int {
        return users.size
    }

}