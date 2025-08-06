package com.example.githubreposapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.githubreposapp.databinding.FragmentUserReposBinding
import com.example.githubreposapp.utils.UiState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserReposFragment : Fragment() {

    private var _binding: FragmentUserReposBinding? = null
    private val binding
        get() = _binding!!

    private val userReposViewModel: UserReposViewModel by viewModel()

    private val reposAdapter = ReposAdapter()

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
            rvRepos.adapter = reposAdapter
            val dividerItemDecoration = DividerItemDecoration(
                rvRepos.context,
                DividerItemDecoration.VERTICAL
            )
            rvRepos.addItemDecoration(dividerItemDecoration)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                userReposViewModel.repos.collect { state ->
                    when (state) {
                        is UiState.Loading -> {}

                        is UiState.Success -> {
                            reposAdapter.setRepos(state.data)
                        }

                        is UiState.Error -> {}
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}