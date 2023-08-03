package com.project.fleem.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.fleem.data.FleemRepository
import com.project.fleem.models.Cart
import com.project.fleem.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val fleemRepository: FleemRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Cart>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Cart>>>
        get() = _uiState

    fun getAllFleemCart() {
        viewModelScope.launch {
            fleemRepository.getAllFLeemCart()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderRewards ->
                    _uiState.value = UiState.Success(orderRewards)
                }
        }
    }
}