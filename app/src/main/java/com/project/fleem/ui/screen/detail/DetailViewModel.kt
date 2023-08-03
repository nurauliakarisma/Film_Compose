package com.project.fleem.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.fleem.data.FleemRepository
import com.project.fleem.models.Cart
import com.project.fleem.models.Film
import com.project.fleem.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val fleemRepository: FleemRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Cart>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Cart>>
        get() = _uiState

    fun getRewardById(filmId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(fleemRepository.getFilmById(filmId))
        }
    }

    fun addToCart(film: Film, count: Int) {
        viewModelScope.launch {
            fleemRepository.updateFilmCart(film.id, count)
        }
    }
}