package android.example.blowonboredom.utils

import android.example.blowonboredom.data.model.RandomActivity
import androidx.recyclerview.widget.DiffUtil

class CardStackCallback(
    var old: ArrayList<RandomActivity>,
    var baru: ArrayList<RandomActivity>
) : DiffUtil.Callback(){


    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return baru.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].accessibility === baru.get(newItemPosition).accessibility
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] === baru.get(newItemPosition)
    }
}