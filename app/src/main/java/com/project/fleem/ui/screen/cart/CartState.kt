package com.project.fleem.ui.screen.cart

import com.project.fleem.models.Cart


data class CartState(
    val cartList: List<Cart>,
    val totalPrice: Int
)