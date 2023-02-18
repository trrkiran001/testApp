package com.example.testapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.ItemMeaningBinding
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class DefinitionAdapter @Inject constructor(): RecyclerView.Adapter<DefinitionAdapter.DefinitionViewHolder>() {
    private val items: MutableList<String> = mutableListOf()

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

    fun setItems(newItems: List<String>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

}