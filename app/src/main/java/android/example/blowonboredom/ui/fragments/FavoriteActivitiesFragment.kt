package android.example.blowonboredom.ui.fragments

import android.example.blowonboredom.R
import android.example.blowonboredom.adapters.FavoriteActivitiesAdapter
import android.example.blowonboredom.data.model.RandomActivity
import android.example.blowonboredom.ui.viewmodel.FavoriteActivitiesVM
import android.example.blowonboredom.ui.viewmodel.states.CompletableStates
import android.example.blowonboredom.ui.viewmodel.states.SuccessStates
import android.example.blowonboredom.utils.showSnackBar
import android.example.blowonboredom.utils.showToast
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivitiesFragment : Fragment(R.layout.fragment_favorite_activities) {

    private lateinit var adapter: FavoriteActivitiesAdapter
    private lateinit var recyclerView: RecyclerView
    private val favoriteActivitiesVM: FavoriteActivitiesVM by viewModels()
    private lateinit var deleteIcon: Drawable
    private var swipeBackground: ColorDrawable = ColorDrawable(Color.parseColor("#B9EA7E76"))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(view)
        initObservers()
        favoriteActivitiesVM.getAllFavoritesActivities()
        deleteIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete_24_grey)!!
        initSwipeToDelete()

    }

    private fun initSwipeToDelete() {
        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                adapter.removeItem(viewHolder.adapterPosition)
                showSnackBar(
                    "Delete activity?",
                    "Undo",
                    { adapter.undoRemoveItem() }, {
                        adapter.getRemovedItem()?.key?.let {
                            favoriteActivitiesVM.deleteFromFavoriteActivities(
                                it
                            )
                        }
                    })

            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val marginIcon = (itemView.height - deleteIcon.intrinsicHeight) / 2
                if (dX > 0) {
                    swipeBackground.setBounds(
                        itemView.left,
                        itemView.top,
                        dX.toInt(),
                        itemView.bottom
                    )
                    deleteIcon.setBounds(
                        itemView.left + marginIcon,
                        itemView.top + marginIcon,
                        itemView.left + marginIcon + deleteIcon.intrinsicWidth,
                        itemView.bottom - marginIcon
                    )
                } else {
                    swipeBackground.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                    deleteIcon.setBounds(
                        itemView.right - marginIcon - deleteIcon.intrinsicWidth,
                        itemView.top + marginIcon,
                        itemView.right - marginIcon,
                        itemView.bottom - marginIcon
                    )
                }
                swipeBackground.draw(c)
                deleteIcon.draw(c)
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun initObservers() {
        favoriteActivitiesVM.favoriteActivitiesState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is SuccessStates.Success -> {
                    adapter.setItems(state.data as MutableList<RandomActivity>)
                }
                is SuccessStates.Error -> {
                    showToast(state.message)
                }
                is SuccessStates.Loading -> {

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

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.fragment_favorite_activities_RV)
        adapter = FavoriteActivitiesAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }


}