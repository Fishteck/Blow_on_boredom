package android.example.blowonboredom

import android.example.blowonboredom.fragments.RandomActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CardStackAdapter() : RecyclerView.Adapter<CardStackAdapter.CardStackHolder>(){

    private val items = ArrayList<RandomActivity>()

    inner class CardStackHolder(view:View) : RecyclerView.ViewHolder(view) {

        private val text1 : TextView = view.findViewById(R.id.random_activity_item_first)
        private val text2 : TextView = view.findViewById(R.id.random_activity_item_second)
        private val text3 : TextView = view.findViewById(R.id.random_activity_item_third)

        fun bind(item : RandomActivity) {
            text1.text = item.activity
            text2.text = item.key
            text3.text = item.accessibility.toString()
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

    fun setItems(items:  ArrayList<RandomActivity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setItem(items: RandomActivity) {
        this.items.clear()
        this.items.add(items)
        notifyDataSetChanged()
    }

    fun getItems(): ArrayList<RandomActivity> {
        return items
    }
}