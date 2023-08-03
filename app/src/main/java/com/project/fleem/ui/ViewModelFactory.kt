package com.project.fleem.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.fleem.data.FleemRepository
import com.project.fleem.ui.screen.cart.CartViewModel
import com.project.fleem.ui.screen.detail.DetailViewModel
import com.project.fleem.ui.screen.home.HomeViewModel

class ViewModelFactory(private val fleemRepository: FleemRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel(fleemRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                return DetailViewModel(fleemRepository) as T
            }
            modelClass.isAssignableFrom(CartViewModel::class.java) -> {
                return CartViewModel(fleemRepository) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}