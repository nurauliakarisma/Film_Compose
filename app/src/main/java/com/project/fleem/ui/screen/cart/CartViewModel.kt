package com.project.fleem.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.fleem.data.FleemRepository
import com.project.fleem.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val fleemRepository: FleemRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedCart() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            fleemRepository.getAddedFilmCarts()
                .collect { cart ->
                    val totalPrice =
                        cart.sumOf { it.film.price * it.count }
                    _uiState.value = UiState.Success(CartState(cart, totalPrice))
                }
        }
    }

    fun updateAddedCart(filmId: Long, count: Int) {
        viewModelScope.launch {
            fleemRepository.updateFilmCart(filmId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedCart()
                    }
                }
        }
    }
}