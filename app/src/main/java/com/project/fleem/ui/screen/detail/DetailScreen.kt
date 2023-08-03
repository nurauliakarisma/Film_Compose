package com.project.fleem.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.fleem.R
import com.project.fleem.di.DInjection
import com.project.fleem.ui.ViewModelFactory
import com.project.fleem.ui.components.OrderButton
import com.project.fleem.ui.components.ProductCounter
import com.project.fleem.ui.theme.FleemTheme
import com.project.fleem.utils.UiState

@Composable
fun DetailScreen(
    filmId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            DInjection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    Scaffold() { innerPadding ->
        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    viewModel.getRewardById(filmId)
                }
                is UiState.Success -> {
                    val data = uiState.data
                    Box(modifier = Modifier.padding(innerPadding)) {
                        DetailContent(
                            data.film.image,
                            data.film.title,
                            data.film.desc,
                            data.film.price,
                            data.count,
                            onBackClick = navigateBack,
                            onAddToCart = { count ->
                                viewModel.addToCart(data.film, count)
                                navigateBack()
                            }
                        )
                    }
                }
                is UiState.Error -> {}
            }
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    title: String,
    desc: String,
    basePrice: Int,
    count: Int,
    onBackClick: () -> Unit,
    onAddToCart: (count: Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    var totalPrice by rememberSaveable { mutableStateOf(0) }
    var orderCount by rememberSaveable { mutableStateOf(count) }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = "Rp $basePrice",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                )
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .background(Color.LightGray))
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ProductCounter(
                1,
                orderCount,
                onProductIncreased = { orderCount++ },
                onProductDecreased = { if (orderCount > 0) orderCount-- },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )
            totalPrice = basePrice * orderCount
            OrderButton(
                text = "Rp $totalPrice",
                enabled = orderCount > 0,
                onClick = {
                    onAddToCart(orderCount)
                }
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    FleemTheme() {
        DetailContent(
            R.drawable.sevensamurai,
            "Seven Samurai",
            "Seven Samurai Seven Samurai",
            150000,
            1,
            onBackClick = {},
            onAddToCart = {}
        )
    }
}