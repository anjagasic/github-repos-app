package com.example.githubreposapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubreposapp.databinding.RepoItemBinding

class ReposAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items = arrayListOf<RepoUI>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RepoViewHolder(
            RepoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RepoViewHolder).bind(items[position])
    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setRepos(list: List<RepoUI>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class RepoViewHolder(private val binding: RepoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repoUI: RepoUI) = with(binding) {
            tvRepoName.text = repoUI.name
            tvNumOfRepoIssues.text = "Open issues: ${repoUI.openIssuesCount}"
        }

    }
}