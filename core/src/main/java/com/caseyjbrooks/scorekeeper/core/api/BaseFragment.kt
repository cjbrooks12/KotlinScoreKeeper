package com.caseyjbrooks.scorekeeper.core.api

import android.support.v4.app.Fragment

abstract class BaseFragment : Fragment() {

    open fun hasFab(): Boolean {
        return false
    }

    open fun getFabIcon(): Int? {
        return null
    }

    open fun onFabClicked() {

    }

}
