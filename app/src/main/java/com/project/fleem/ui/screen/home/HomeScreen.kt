package com.project.fleem.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.fleem.di.DInjection
import com.project.fleem.models.Cart
import com.project.fleem.ui.ViewModelFactory
import com.project.fleem.ui.components.AppBar
import com.project.fleem.ui.components.FilmItem
import com.project.fleem.utils.UiState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(DInjection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
    navigateToAboutMe: () -> Unit,
    navigateToCart: () -> Unit,
) {
    Scaffold(
        topBar = { AppBar(navigateToAboutMe) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navigateToCart()
            }) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
            }
        },
    ) { innerPadding ->
        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    viewModel.getAllFleemCart()
                }
                is UiState.Success -> {
                    Box(modifier = Modifier.padding(innerPadding)) {
                        HomeContent(
                            fleemCartList = uiState.data,
                            navigateToDetail = navigateToDetail,
                        )
                    }
                }
                is UiState.Error -> {}
            }
        }
    }
}

@Composable
fun HomeContent(
    fleemCartList: List<Cart>,
    navigateToDetail: (Long) -> Unit,
) {
    LazyColumn(
    ) {
        items(items = fleemCartList, itemContent = { item ->
            FilmItem(
                image = item.film.image,
                title = item.film.title,
                price = item.film.price,
                modifier = Modifier.clickable {
                    navigateToDetail(item.film.id)
                }
            )
        })
    }
}