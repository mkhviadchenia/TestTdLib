package com.test.testtdlib.presentation.main.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.testtdlib.databinding.ItemContactBinding
import org.drinkless.td.libcore.telegram.TdApi

class ContactsAdapter(private val listener: ActionListener) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    interface ActionListener {
        fun onClick(item: TdApi.User)
    }

    private val items: MutableList<TdApi.User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    fun swap(items: List<TdApi.User>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemContactBinding, private  val listener: ActionListener) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

            private lateinit var item: TdApi.User

            fun bind(item: TdApi.User) {
                binding.root.setOnClickListener(this)
                this.item = item
                binding.fullName.text = "${item.firstName} ${item.lastName}"
                binding.phoneNumber.text = item.phoneNumber
            }

        override fun onClick(v: View?) {
            listener.onClick(item)
        }
    }
}