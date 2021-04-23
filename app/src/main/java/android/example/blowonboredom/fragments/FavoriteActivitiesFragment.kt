package android.example.blowonboredom.fragments

import android.example.blowonboredom.R
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class FavoriteActivitiesFragment : Fragment(R.layout.fragment_favorite_activities) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn = view.findViewById<Button>(R.id.btn_to_detail).setOnClickListener {
            it.findNavController().navigate(R.id.action_favoriteActivitiesFragment_to_detailActivityFragment)
        }
    }
}