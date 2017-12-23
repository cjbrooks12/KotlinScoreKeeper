package com.caseyjbrooks.scorekeeper.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.caseyjbrooks.scorekeeper.core.api.BaseFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

class HomescreenFragment : BaseFragment() {

    var text: String? = null
    var tv: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return with(AnkoContext.create(context, this)) {
            verticalLayout {
                tv = textView(text?: "Homescreen fragment")
            }
        }
    }

    fun setText(text: String): HomescreenFragment {
        this.text = text
        tv?.text = text

        return this
    }

}
