package android.example.blowonboredom.adapters

import android.example.blowonboredom.R
import android.example.blowonboredom.data.model.RandomActivity
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class FavoriteActivitiesAdapter :
    RecyclerView.Adapter<FavoriteActivitiesAdapter.FavoriteHolder>() {

    private lateinit var items : MutableList<RandomActivity>
    private var removedItemPosition : Int = 0
    private var removedItem : RandomActivity? = null


    inner class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val activityText = view.findViewById<TextView>(R.id.favorite_activity_item_activity)
        private val activityType = view.findViewById<TextView>(R.id.favorite_activity_item_type)


        fun bind(item: RandomActivity) {
            activityText.text = item.activity
            activityType.text = item.type
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<RandomActivity>() {
        override fun areItemsTheSame(oldItem: RandomActivity, newItem: RandomActivity): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: RandomActivity, newItem: RandomActivity): Boolean {
            return oldItem == newItem
        }
    }

    private val differ: AsyncListDiffer<RandomActivity> = AsyncListDiffer(this, differCallback)

     fun removeItem( position: Int) {
         removedItemPosition = position
         removedItem = items[removedItemPosition]
         items.removeAt(position)
         differ.submitList(items)
         notifyItemRemoved(position)
         notifyItemRangeChanged(position, items.size)
    }

    fun undoRemoveItem()  {
        removedItem?.let { items.add(removedItemPosition, it) }
        differ.submitList(items)
        notifyItemInserted(removedItemPosition)
    }

    fun getRemovedItem() : RandomActivity? {
        return removedItem
    }

    fun setItems(list: MutableList<RandomActivity>) {
        items = list
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_activity_item, parent, false)
        return FavoriteHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val item = differ.currentList[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int = differ.currentList.size

}