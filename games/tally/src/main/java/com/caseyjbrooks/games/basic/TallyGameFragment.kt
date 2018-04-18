package com.caseyjbrooks.games.basic

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.LinearLayout
import com.caseyjbrooks.scorekeeper.core.api.*
import com.caseyjbrooks.scorekeeper.core.db.CorePreferences
import com.caseyjbrooks.scorekeeper.core.findString
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick



class TallyGameFragment : BaseFragment() {

    private lateinit var component: BaseComponent
    lateinit var gameViewModel: TallyGameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        component = (activity!!.application as BaseApplication).component
        component.inject(this)

        val gameId = CorePreferences(activity!!, "tally").get { findString("lastGame", { "" }) }
        gameViewModel = TallyGameViewModel(activity!! as BaseActivity, component, gameId)
        gameViewModel.setup()

        return setupView()
    }

    private fun setupView(): View {
        val ankoContext = AnkoContext.create(context!!, this)
        return with(ankoContext) {
            relativeLayout {
                val minusBar = linearLayout {
                    id = R.id.minusBar
                    orientation = if (activity!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        LinearLayout.HORIZONTAL
                    }
                    else {
                        LinearLayout.VERTICAL
                    }

                    weightSum = gameViewModel.buttonValues.size.toFloat()
                    for ((index, buttonVal) in gameViewModel.buttonValues.withIndex()) {
                        val btn = button("-$buttonVal") {
                            onClick { gameViewModel.updateVal(index, false) }
                            onLongClick { gameViewModel.changeButtonValue(ankoContext, index, false) }
                        }.lparams { weight = 1.0f }
                        gameViewModel.minusButtons.add(btn)
                    }
                }.lparams {
                    if (activity!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        alignParentLeft()
                        alignParentRight()
                        alignParentBottom()
                    }
                    else {
                        alignParentRight()
                        alignParentBottom()
                        alignParentTop()
                    }
                }

                val plusBar = linearLayout {
                    id = R.id.plusBar
                    orientation = if (activity!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        LinearLayout.HORIZONTAL
                    }
                    else {
                        LinearLayout.VERTICAL
                    }

                    weightSum = gameViewModel.buttonValues.size.toFloat()
                    for ((index, buttonVal) in gameViewModel.buttonValues.withIndex()) {
                        val btn = button("+$buttonVal") {
                            onClick { gameViewModel.updateVal(index, true) }
                            onLongClick { gameViewModel.changeButtonValue(ankoContext, index, true) }
                        }.lparams { weight = 1.0f }
                        gameViewModel.plusButtons.add(btn)
                    }
                }.lparams {
                    if (activity!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        alignParentLeft()
                        alignParentRight()
                        above(minusBar)
                    }
                    else {
                        alignParentBottom()
                        alignParentTop()
                        leftOf(minusBar)
                    }
                }

                val dividerView = view {
                    id = R.id.divider
                    backgroundColor = Color.LTGRAY
                }.lparams {

                    if (activity!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        height = dip(1)
                        width = matchParent
                        alignParentLeft()
                        alignParentRight()
                        above(plusBar)
                    }
                    else {
                        width = dip(1)
                        height = matchParent
                        alignParentBottom()
                        alignParentTop()
                        leftOf(plusBar)
                    }
                }

                recyclerView {
                    layoutManager = when (gameViewModel.layout) {
                        "List (Comfy)"   -> LinearLayoutManager(context)
                        "List (Compact)" -> {addItemDecoration(DividerItemDecoration(activity!!, DividerItemDecoration.VERTICAL));LinearLayoutManager(context)}
                        "Grid"           -> {if (activity!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) { GridLayoutManager(context, 2) } else { GridLayoutManager(context, 3) }}
                        else             -> LinearLayoutManager(context)
                    }
                    adapter = BaseRecyclerViewGameAdapter(gameViewModel.adapter)
                    isNestedScrollingEnabled = true
                }.lparams {
                    width = matchParent
                    if (activity!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        alignParentTop()
                        alignParentLeft()
                        alignParentRight()
                        above(dividerView)
                    }
                    else {
                        alignParentTop()
                        alignParentBottom()
                        alignParentLeft()
//                        alignParentRight()
                        leftOf(dividerView)
//                        sameRight(dividerView)
                    }
                }
            }
        }
    }

// Menu
//----------------------------------------------------------------------------------------------------------------------

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tally_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addNewUser      -> gameViewModel.addNewUser(AnkoContext.create(context!!, this))
            R.id.addExistingUser -> gameViewModel.addExistingUser(AnkoContext.create(context!!, this))
            R.id.resetGame       -> gameViewModel.resetGame(AnkoContext.create(context!!, this))
            R.id.changeLayout    -> gameViewModel.changeLayout(AnkoContext.create(context!!, this))
        }
        return true
    }

}
