package com.son.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.son.myapplication.data.model.Style
import com.son.myapplication.databinding.ItemStyleBinding

class StyleAdapter : ListAdapter<Style, StyleAdapter.StyleViewHolder>(diffCallback) {
    var onItemClickListener: ((Style) -> Unit) = {}

    inner class StyleViewHolder(private val binding: ItemStyleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(style: Style) {
            Glide.with(binding.root.context)
                .load(style.key)
                .into(binding.imgStyle)
            binding.txtStyle.text = style.name

            binding.root.setOnClickListener {
                onItemClickListener(style)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Style>() {
            override fun areItemsTheSame(oldItem: Style, newItem: Style): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Style, newItem: Style): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StyleViewHolder {
        return StyleViewHolder(
            ItemStyleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StyleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}