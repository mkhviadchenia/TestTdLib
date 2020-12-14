package com.test.testtdlib.presentation.main.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.testtdlib.databinding.ItemTextMessageBinding

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private val items: MutableList<String> = ArrayList()

    init {
        items.add("Test")
        items.add("Test123")
        items.add("Test1234")
    }

    fun swap(items: List<String>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun insertLast(item: String) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTextMessageBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: ItemTextMessageBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.textMessage.text = item
        }
    }
}