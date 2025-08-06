package com.example.githubreposapp.ui.userRepos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubreposapp.data.mappers.toUI
import com.example.githubreposapp.data.repository.UserReposRepository
import com.example.githubreposapp.ui.RepoUI
import com.example.githubreposapp.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserReposViewModel(private val userReposRepository: UserReposRepository) : ViewModel() {

    private val _repos = MutableStateFlow<UiState<List<RepoUI>>>(UiState.Loading)
    val repos: StateFlow<UiState<List<RepoUI>>> = _repos

    init {
        fetchRepos()
    }

    fun fetchRepos() {
        viewModelScope.launch {
            _repos.value = UiState.Loading
            val result = userReposRepository.getUserRepos()

            _repos.value = when (result) {
                is UiState.Success -> UiState.Success(result.data.map { it.toUI() })
                is UiState.Error -> UiState.Error(result.message)
                is UiState.Loading -> UiState.Loading
            }
        }
    }
}