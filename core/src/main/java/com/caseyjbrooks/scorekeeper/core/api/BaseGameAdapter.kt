package com.caseyjbrooks.scorekeeper.core.api

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.FrameLayout
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.dip
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.horizontalMargin
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.padding
import org.jetbrains.anko.verticalMargin

abstract class BaseGameAdapter<T>(
        val activity: BaseActivity,
        val component: BaseComponent,
        val gameViewModel: BaseGameViewModel<T>
) : BaseAdapter() {

    var users: List<T> = emptyList()

    final override fun getView(i: Int, v: View?, parent: ViewGroup?): View {
        return getView(i, parent!!.context)
    }

    fun getView(i: Int, context: Context): View {
        return when (gameViewModel.layout) {
            "List (Comfy)"   -> getComfyListItemView(i, context)
            "List (Compact)" -> getCompactListItemView(i, context)
            "Grid"           -> getGridItemView(i, context)
            else             -> getViewContents(i, context)
        }
    }

    abstract fun getViewContents(i: Int, context: Context): View

    open fun getComfyListItemView(i: Int, context: Context): View {
        return with(context) {
            frameLayout {
                cardView {
                    cardElevation = dip(2).toFloat()
                    useCompatPadding = true

                    val v = getViewContents(i, context)
                    v.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
                    addView(v)
                }.lparams {
                    width = matchParent
                    horizontalMargin = dip(8)
                    verticalMargin = dip(4)
                    padding = 0
                }
            }
        }
    }

    open fun getCompactListItemView(i: Int, context: Context): View {
        return getViewContents(i, context)
    }

    open fun getGridItemView(i: Int, context: Context): View {
        return getComfyListItemView(i, context)
    }

    abstract override fun getItemId(position: Int): Long

    override fun getItem(position: Int): T {
        return users[position]
    }

    override fun getCount(): Int {
        return users.size
    }

}