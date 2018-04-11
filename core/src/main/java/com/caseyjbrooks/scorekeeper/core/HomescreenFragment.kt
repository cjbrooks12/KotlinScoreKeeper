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

    lateinit var text: String
    lateinit var tv: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        text = if(arguments != null && arguments!!.containsKey("text")) { arguments!!.getString("text") } else { "Homescreen fragment" }

        return with(AnkoContext.create(context!!, this)) {
            verticalLayout {
                tv = textView(text)
            }
        }
    }
}
