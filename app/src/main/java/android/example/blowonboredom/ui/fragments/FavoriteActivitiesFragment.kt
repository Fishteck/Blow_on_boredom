package android.example.blowonboredom.ui.fragments

import android.example.blowonboredom.R
import android.example.blowonboredom.adapters.FavoriteActivitiesAdapter
import android.example.blowonboredom.data.model.RandomActivity
import android.example.blowonboredom.ui.viewmodel.FavoriteActivitiesVM
import android.example.blowonboredom.ui.viewmodel.states.FavoriteActivitiesState
import android.example.blowonboredom.utils.showToast
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivitiesFragment : Fragment(R.layout.fragment_favorite_activities) {

    private lateinit var adapter: FavoriteActivitiesAdapter
    private lateinit var recyclerView: RecyclerView
    private val favoriteActivitiesVM : FavoriteActivitiesVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val btn = view.findViewById<Button>(R.id.btn_to_detail).setOnClickListener {
            it.findNavController().navigate(R.id.action_favoriteActivitiesFragment_to_detailActivityFragment)
        }*/
        initRecyclerView(view)
        initObservers()
        favoriteActivitiesVM.getAllFavoritesActivities()

    }

    private fun initObservers() {
        favoriteActivitiesVM.favoriteActivitiesState.observe(viewLifecycleOwner, { state ->
            when(state) {
                is FavoriteActivitiesState.Success<*> -> {
                    if (state.data is List<*> ) {
                        val list = state.data as ArrayList<RandomActivity>
                        adapter.setItems(list = list)
                    }
                }
                is FavoriteActivitiesState.Error<*> -> {
                   if(state.message is String) {
                       showToast(state.message)
                   }
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