package com.example.githubreposapp.ui.userRepos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.githubreposapp.databinding.FragmentUserReposBinding
import com.example.githubreposapp.ui.userRepos.UserReposFragmentDirections.Companion.toRepoDetailsScreen
import com.example.githubreposapp.utils.UiState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserReposFragment : Fragment() {

    private var _binding: FragmentUserReposBinding? = null
    private val binding
        get() = _binding!!

    private val userReposViewModel: UserReposViewModel by viewModel()

    private lateinit var reposAdapter: ReposAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserReposBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            reposAdapter = ReposAdapter { name ->
                findNavController().navigate(toRepoDetailsScreen(repoName = name))
            }
            rvRepos.adapter = reposAdapter
            val dividerItemDecoration = DividerItemDecoration(
                rvRepos.context,
                DividerItemDecoration.VERTICAL
            )
            rvRepos.addItemDecoration(dividerItemDecoration)

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    userReposViewModel.repos.collect { state ->
                        when (state) {
                            is UiState.Loading -> {
                                noDataView.root.isVisible = false
                                loader.isVisible = true
                                rvRepos.isVisible = false
                            }

                            is UiState.Success -> {
                                noDataView.root.isVisible = false
                                loader.isVisible = false
                                rvRepos.isVisible = true
                                reposAdapter.submitList(state.data)
                            }

                            is UiState.Error -> {
                                loader.isVisible = false
                                setupNoDataView(state.message)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupNoDataView(errorMessage: String? = null) {
        binding.noDataView.root.isVisible = true
        binding.noDataView.apply {
            btnTryAgain.text = "Try again"
            tvTitle.text = "Something went wrong"
            tvErrorMessage.isVisible = errorMessage != null
            tvErrorMessage.text = errorMessage

            btnTryAgain.setOnClickListener {
                userReposViewModel.fetchRepos()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}