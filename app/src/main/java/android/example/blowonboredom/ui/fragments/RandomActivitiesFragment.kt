package android.example.blowonboredom.ui.fragments

import android.example.blowonboredom.utils.CardStackAdapter
import android.example.blowonboredom.utils.CardStackCallback
import android.example.blowonboredom.R
import android.example.blowonboredom.data.model.RandomActivity
import android.example.blowonboredom.ui.viewmodel.RandomActivityState
import android.example.blowonboredom.ui.viewmodel.RandomActivityVM
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class RandomActivitiesFragment : Fragment(R.layout.fragment_random_activities) {
    private lateinit var manager: CardStackLayoutManager
    private lateinit var adapter: CardStackAdapter
    private val randomActivityVM : RandomActivityVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwipe(view)
        initObserver()
        randomActivityVM.getRandom()
    }

    private fun initObserver() {
        randomActivityVM.state.observe(viewLifecycleOwner, Observer { state ->
            when(state) {
                is RandomActivityState.OnSuccessState<*> -> {
                    if (state.data is RandomActivity) {
                        adapter.setItem(state.data)
                    }
                }
                is RandomActivityState.OnErrorState<*> -> {
                    if (state.messge is String) {
                        Toast.makeText(requireContext(), state.messge, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun initSwipe(view: View) {
        val cardStackView = view.findViewById<CardStackView>(R.id.card_stack_view)
        manager = CardStackLayoutManager(requireContext(), object : CardStackListener {
            override fun onCardDragging(direction: Direction, ratio: Float) {

            }

            override fun onCardSwiped(direction: Direction) {


                randomActivityVM.getRandom()


            }

            override fun onCardRewound() {

            }

            override fun onCardCanceled() {

            }

            override fun onCardAppeared(view: View, position: Int) {

            }

            override fun onCardDisappeared(view: View, position: Int) {

            }
        })
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
        adapter = CardStackAdapter()
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator = DefaultItemAnimator()
    }

    private fun paginate() {

        /*val old: ArrayList<RandomActivity> = adapter.getItems()
        val baru: ArrayList<RandomActivity> = ArrayList(addList())
        val callback = CardStackCallback(old, baru)
        val hasil = DiffUtil.calculateDiff(callback)
        adapter.setItems(baru)
        hasil.dispatchUpdatesTo(adapter)*/
    }
    private fun addList(): ArrayList<RandomActivity> {


        var list = arrayListOf<RandomActivity>()
        Toast.makeText(requireContext(), getRandomNumber().toString(), Toast.LENGTH_SHORT).show()
        list.add(RandomActivity(getRandomNumber(), "relax", "type"))
        return list
    }

    private fun getRandomNumber() : Double{
        val rand = Random(0)
        return rand.nextDouble(0.1, 6.0)
    }
}