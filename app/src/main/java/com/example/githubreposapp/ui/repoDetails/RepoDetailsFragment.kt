package com.example.githubreposapp.ui.repoDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.example.githubreposapp.R
import com.example.githubreposapp.databinding.FragmentRepoDetailsBinding
import com.example.githubreposapp.ui.RepoDetailsUI
import com.example.githubreposapp.ui.UserUI
import com.example.githubreposapp.utils.UiState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RepoDetailsFragment : Fragment() {

    private var _binding: FragmentRepoDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: RepoDetailsFragmentArgs by navArgs()

    private val repoDetailsViewModel: RepoDetailsViewModel by viewModel {
        parametersOf(args.repoName)
    }

    private val tagsAdapter = TagsListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvTags.text = "Tags"
            rvTags.adapter = tagsAdapter
            val dividerItemDecoration = DividerItemDecoration(
                rvTags.context,
                DividerItemDecoration.VERTICAL
            )
            rvTags.addItemDecoration(dividerItemDecoration)

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    repoDetailsViewModel.tags.collect { state ->
                        when (state) {
                            is UiState.Loading -> {
                                noDataView.root.isVisible = false
                                loader.isVisible = true
                                rvTags.isVisible = false
                            }

                            is UiState.Success -> {
                                rvTags.isVisible = state.data.isNotEmpty()
                                if (state.data.isEmpty()) {
                                    setupNoDataView()
                                } else {
                                    noDataView.root.isVisible = false
                                    tagsAdapter.submitList(state.data)
                                }
                                loader.isVisible = false
                            }

                            is UiState.Error -> {
                                loader.isVisible = false
                            }
                        }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    repoDetailsViewModel.userData.collect { state ->
                        when (state) {
                            is UiState.Loading -> {
                                loader.isVisible = true
                            }

                            is UiState.Success -> {
                                noDataView.root.isVisible = false
                                loader.isVisible = false
                                setUserData(state.data)
                            }

                            is UiState.Error -> {
                                loader.isVisible = false
                            }
                        }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    repoDetailsViewModel.repoDetails.collect { state ->
                        when (state) {
                            is UiState.Loading -> {
                                loader.isVisible = true
                            }

                            is UiState.Success -> {
                                noDataView.root.isVisible = false
                                loader.isVisible = false
                                setRepoData(state.data)
                            }

                            is UiState.Error -> {
                                loader.isVisible = false
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setUserData(data: UserUI) = with(binding) {
        tvFullName.text = data.fullName
        Glide.with(ivAvatarImage.context)
            .load("null")
            .placeholder(R.drawable.ic_avatar_default)
            .error(R.drawable.ic_avatar_default)
            .into(ivAvatarImage)
    }

    private fun setRepoData(data: RepoDetailsUI) = with(binding) {
        tvRepoName.text = data.name
        tvNumOfForks.text = "Forks: ${data.forksCount}"
        tvNumOfWatchers.text = "Watchers: ${data.watchersCount}"
    }

    private fun setupNoDataView() = with(binding) {
        tvTags.isVisible = false
        noDataView.root.isVisible = true
        noDataView.apply {
            btnTryAgain.isVisible = false
            tvTitle.text = "No tags for selected repository"
            tvErrorMessage.isVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}