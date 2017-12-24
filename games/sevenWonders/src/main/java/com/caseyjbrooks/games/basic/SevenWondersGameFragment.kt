package com.caseyjbrooks.games.basic

import android.os.Bundle
import android.view.*
import com.caseyjbrooks.games.sevenWonders.R
import com.caseyjbrooks.scorekeeper.core.api.BaseActivity
import com.caseyjbrooks.scorekeeper.core.api.BaseApplication
import com.caseyjbrooks.scorekeeper.core.api.BaseComponent
import com.caseyjbrooks.scorekeeper.core.api.BaseFragment
import com.caseyjbrooks.scorekeeper.core.db.CorePreferences
import com.caseyjbrooks.scorekeeper.core.findLong
import org.jetbrains.anko.*

class SevenWondersGameFragment : BaseFragment() {

    private lateinit var component: BaseComponent
    lateinit var gameViewModel : SevenWondersGameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        component = (activity.application as BaseApplication).component
        component.inject(this)

        val gameId = CorePreferences(activity, "sevenWonders").get { findLong("lastGame", { 0 }) }
        gameViewModel = SevenWondersGameViewModel(activity as BaseActivity, component, gameId)
        gameViewModel.setup()

        return setupView()
    }

    private fun setupView() : View {
        val ankoContext = AnkoContext.create(context, this)
        return with(ankoContext) {
            relativeLayout {
                listView {
                    adapter = gameViewModel.adapter
                    divider = null
                }.lparams {
                    alignParentTop()
                    alignParentBottom()
                    alignParentLeft()
                    alignParentRight()
                }
            }
        }
    }

// Menu
//--------------------------------------------------------------------------------------------------

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.seven_wonders_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addNewUser      -> gameViewModel.addNewUser(AnkoContext.create(context, this))
            R.id.addExistingUser -> gameViewModel.addExistingUser(AnkoContext.create(context, this))
            R.id.resetGame       -> gameViewModel.resetGame(AnkoContext.create(context, this))
        }
        return true
    }

}
