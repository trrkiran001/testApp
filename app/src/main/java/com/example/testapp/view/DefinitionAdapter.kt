package com.example.testapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.ItemMeaningBinding

class DefinitionAdapter(): RecyclerView.Adapter<DefinitionAdapter.DefinitionViewHolder>() {
    private var items: List<String> = emptyList()

    inner class DefinitionViewHolder(private val binding: ItemMeaningBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.fullText = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionViewHolder {
        val binding = ItemMeaningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DefinitionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        holder.bind(items[position])
    }

    @BindingAdapter("items")
    fun setItems(newItems: List<String>) {
        items = newItems
        notifyDataSetChanged()
    }
}