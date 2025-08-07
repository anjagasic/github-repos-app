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
            tvTags.text = getString(R.string.tags)
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
                                rvTags.isVisible = false
                            }

                            is UiState.Success -> {
                                rvTags.isVisible = state.data.isNotEmpty()
                                if (state.data.isEmpty()) {
                                    tvTags.isVisible = false
                                    showNoTagsView(title = getString(R.string.no_tags_message))
                                } else {
                                    noDataView.root.isVisible = false
                                    tagsAdapter.submitList(state.data)
                                }
                            }

                            is UiState.Error -> {
                                tvTags.isVisible = false
                                showNoTagsView(
                                    title = getString(R.string.generic_error_message),
                                    message = state.message,
                                    showRetry = true,
                                    onRetry = { repoDetailsViewModel.fetchRepoTags() }
                                )
                            }
                        }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    repoDetailsViewModel.userData.collect { state ->
                        when (state) {
                            is UiState.Loading -> {}

                            is UiState.Success -> {
                                setUserData(state.data)
                            }

                            is UiState.Error -> {
                                setNoUserDataView()
                            }
                        }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    repoDetailsViewModel.repoDetails.collect { state ->
                        when (state) {
                            is UiState.Loading -> {}

                            is UiState.Success -> {
                                setRepoData(state.data)
                            }

                            is UiState.Error -> {
                                setNoRepoDataView()
                            }
                        }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    repoDetailsViewModel.isLoading.collect { isLoading ->
                        loader.isVisible = isLoading
                    }
                }
            }
        }
    }

    private fun showNoTagsView(
        title: String,
        message: String? = null,
        showRetry: Boolean = false,
        onRetry: (() -> Unit)? = null
    ) = with(binding.noDataView) {
        root.isVisible = true
        tvTitle.text = title

        if (!message.isNullOrBlank()) {
            tvErrorMessage.isVisible = true
            tvErrorMessage.text = message
        } else {
            tvErrorMessage.isVisible = false
        }

        if (showRetry && onRetry != null) {
            btnTryAgain.isVisible = true
            btnTryAgain.setOnClickListener { onRetry() }
        } else {
            btnTryAgain.isVisible = false
        }
    }

    private fun setUserData(data: UserUI) = with(binding) {
        tvFullName.text = data.fullName
        Glide.with(ivAvatarImage.context)
            .load(data.avatarImage)
            .placeholder(R.drawable.ic_avatar_default)
            .error(R.drawable.ic_avatar_default)
            .into(ivAvatarImage)
    }

    private fun setNoUserDataView() = with(binding){
        tvFullName.text = getString(R.string.unknown_user)
        ivAvatarImage.setImageResource(R.drawable.ic_avatar_default)
    }

    private fun setRepoData(data: RepoDetailsUI) = with(binding) {
        tvRepoName.text = data.name
        tvNumOfForks.text = getString(R.string.number_of_forks, data.forksCount.toString())
        tvNumOfWatchers.text = getString(R.string.number_of_watchers, data.watchersCount.toString())
    }

    private fun setNoRepoDataView() = with(binding) {
        tvRepoName.text = getString(R.string.unknown_repo)
        tvNumOfForks.text = getString(R.string.number_of_forks, "/")
        tvNumOfWatchers.text = getString(R.string.number_of_watchers, "/")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}