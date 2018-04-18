package com.caseyjbrooks.scorekeeper.core.api

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.FrameLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

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
            wrapCard { getViewContents(i, context) }
        }
    }

    open fun getCompactListItemView(i: Int, context: Context): View {
        return getViewContents(i, context)
    }

    open fun getGridItemView(i: Int, context: Context): View {
        return with(context) {
            wrapCard { getViewContents(i, context) }
        }
    }

    abstract fun getItemIdString(position: Int): String

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getItem(position: Int): T {
        return users[position]
    }

    override fun getCount(): Int {
        return users.size
    }

    fun Context.wrapCard(init: () -> View): View {
        return with(this) {
            frameLayout {
                cardView {
                    cardElevation = dip(2).toFloat()
                    useCompatPadding = true

                    val v = init()
                    v.layoutParams = android.widget.FrameLayout.LayoutParams(android.widget.FrameLayout.LayoutParams.MATCH_PARENT, android.widget.FrameLayout.LayoutParams.WRAP_CONTENT)
                    addView(v)
                }.lparams {
                    width = org.jetbrains.anko.matchParent
                    horizontalMargin = dip(8)
                    verticalMargin = dip(4)
                    padding = 0
                }
            }
        }
    }



}

