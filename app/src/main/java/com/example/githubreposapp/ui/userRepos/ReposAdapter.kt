package com.example.githubreposapp.ui.userRepos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubreposapp.R
import com.example.githubreposapp.databinding.RepoItemBinding
import com.example.githubreposapp.ui.common.model.RepoUI

class ReposAdapter(private val onRepoClick: (String) -> Unit) :
    ListAdapter<RepoUI, ReposAdapter.RepoViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            RepoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReposAdapter.RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RepoViewHolder(private val binding: RepoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repoUI: RepoUI) = with(binding) {
            tvRepoName.text = repoUI.name
            tvNumOfRepoIssues.text =
                root.context.getString(R.string.number_of_issues, repoUI.openIssuesCount.toString())

            binding.root.setOnClickListener {
                onRepoClick(repoUI.name)
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<RepoUI>() {
        override fun areItemsTheSame(oldItem: RepoUI, newItem: RepoUI): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: RepoUI, newItem: RepoUI): Boolean {
            return oldItem == newItem
        }
    }
}