package com.example.githubreposapp.ui.repoDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubreposapp.databinding.TagItemBinding
import com.example.githubreposapp.ui.TagUI

class TagsListAdapter : ListAdapter<TagUI, TagsListAdapter.TagViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val binding = TagItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagsListAdapter.TagViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TagViewHolder(private val binding: TagItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tagUI: TagUI) = with(binding) {
            tvTagName.text = tagUI.name
            tvCommitSha.text = "SHA: ${tagUI.commitSha}"
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TagUI>() {
        override fun areItemsTheSame(oldItem: TagUI, newItem: TagUI): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: TagUI, newItem: TagUI): Boolean {
            return oldItem == newItem
        }
    }
}