package android.example.blowonboredom.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.blowonboredom.R
import androidx.core.view.ViewCompat

class DetailActivityFragment : Fragment(R.layout.fragment_detail_activity) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTranslationZ(requireView(), 100f)
    }
}