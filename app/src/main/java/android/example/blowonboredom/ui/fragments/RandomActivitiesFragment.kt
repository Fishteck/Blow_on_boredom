package android.example.blowonboredom.ui.fragments

import android.app.Dialog
import android.example.blowonboredom.adapters.CardStackAdapter
import android.example.blowonboredom.R
import android.example.blowonboredom.data.model.RandomActivity
import android.example.blowonboredom.ui.viewmodel.FavoriteActivitiesVM
import android.example.blowonboredom.ui.viewmodel.RandomActivityVM
import android.example.blowonboredom.ui.viewmodel.states.CompletableStates
import android.example.blowonboredom.ui.viewmodel.states.SuccessStates
import android.example.blowonboredom.utils.cardstack.CardStackListenerImpl
import android.example.blowonboredom.utils.showSnackBar
import android.example.blowonboredom.utils.showToast
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomActivitiesFragment : Fragment(R.layout.fragment_random_activities), CardStackAdapter.RandomActivitiesListener {

    private lateinit var manager: CardStackLayoutManager
    private lateinit var adapter: CardStackAdapter
    private var progressDialog: Dialog? = null
    private val randomActivityVM : RandomActivityVM by viewModels()
    private val favoriteActivitiesVM : FavoriteActivitiesVM by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwipe(view)
        initObserver()
        randomActivityVM.getRandom()
    }

    private fun initObserver() {
        randomActivityVM.randomActivitiesState.observe(viewLifecycleOwner, { state ->

            when (state) {
                is SuccessStates.Success -> {
                    state.data?.let { adapter.setItem(it) }
                    hideProgress()
                }
                is SuccessStates.Error -> {
                    showToast(state.message)
                }
                is SuccessStates.Loading -> {
                    showProgress()
                }
            }

        })
        favoriteActivitiesVM.favoriteCompletableStates.observe(viewLifecycleOwner, { state ->
            when (state) {
                is CompletableStates.Complete -> {
                    state.message?.let { showSnackBar(it) }
                }
                is CompletableStates.Error -> {
                    showToast(state.message)
                }
            }
        })
    }

    private fun hideProgress() {
        progressDialog?.dismiss()
    }

    private fun showProgress() {
        progressDialog = Dialog(requireContext())
        progressDialog?.let {
            it.setContentView(R.layout.dialog_custom_progress)
            it.show()
        }
    }

    private fun initSwipe(view: View) {

        val cardStackView = view.findViewById<CardStackView>(R.id.card_stack_view)
        manager = CardStackLayoutManager(
            requireContext(),
            CardStackListenerImpl { randomActivityVM.getRandom() }
        )

        setUpLayoutManager()

        adapter = CardStackAdapter(this)
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator = DefaultItemAnimator()
    }

    private fun setUpLayoutManager() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(2)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(false)
        manager.setSwipeableMethod(SwipeableMethod.Manual)
        manager.setOverlayInterpolator(LinearInterpolator())
    }

    override fun onActivityClick(item: RandomActivity, position: Int) {
        if (item.isFavorite == true) {
            item.isFavorite = false
            favoriteActivitiesVM.deleteFromFavoriteActivities(id = item.key)
            adapter.notifyDataSetChanged()
        } else {
            item.isFavorite = true
            favoriteActivitiesVM.addToFavoriteActivities(item)
            adapter.notifyDataSetChanged()
        }


    }


}


