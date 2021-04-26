package android.example.blowonboredom.utils.cardstack

import android.example.blowonboredom.R
import android.example.blowonboredom.data.model.RandomActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CardStackAdapter() : RecyclerView.Adapter<CardStackAdapter.CardStackHolder>(){

    private val items = ArrayList<RandomActivity>()

    inner class CardStackHolder(view:View) : RecyclerView.ViewHolder(view) {

        private val textActivity : TextView = view.findViewById(R.id.random_activity_item_activity_text)
        private val textAccessibility : TextView = view.findViewById(R.id.random_activity_item_accessibility_text)
        private val textType : TextView = view.findViewById(R.id.random_activity_item_type_text)
        private val textParticipants : TextView = view.findViewById(R.id.random_activity_item_participants_text)

        fun bind(item : RandomActivity) {
            textActivity.text = item.activity
            textAccessibility.text = item.accessibility.toString()
            textType.text = item.type
            textParticipants.text = item.participants.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.random_activity_item, parent, false)

        return CardStackHolder(view)
    }

    override fun onBindViewHolder(holder: CardStackHolder, position: Int) {
        val randomActivity = items[position]

        holder.bind(randomActivity)
    }

    override fun getItemCount(): Int = items.size


    fun setItem(items: RandomActivity) {
        this.items.clear()
        this.items.add(items)
        notifyDataSetChanged()
    }


}