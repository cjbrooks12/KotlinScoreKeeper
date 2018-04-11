package com.caseyjbrooks.scorekeeper.core.api

import android.database.DataSetObserver
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

class BaseRecyclerViewGameAdapter<out T: BaseGameAdapter<U>, U>(
        private val list: T
) : RecyclerView.Adapter<BaseRecyclerViewGameAdapter.Holder>() {

    val observer: DataSetObserver

    init {
        observer = object : DataSetObserver() {
            override fun onChanged() {
                notifyDataSetChanged()
            }

            override fun onInvalidated() {
                notifyDataSetChanged()
            }
        }

        list.registerDataSetObserver(observer)
    }

    class Holder(val layout: FrameLayout) : RecyclerView.ViewHolder(layout) {
        fun bind(itemView: View) {
            layout.removeAllViews()
            layout.addView(itemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(FrameLayout(parent.context))
    }

    override fun getItemCount(): Int {
        return list.count
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list.getView(position, holder.layout.context))
    }



}
