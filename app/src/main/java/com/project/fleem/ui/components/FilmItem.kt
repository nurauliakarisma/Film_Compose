package com.project.fleem.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.fleem.R
import com.project.fleem.ui.theme.FleemTheme

@Composable
fun FilmItem(
    image: Int,
    title: String,
    price: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Row(modifier = modifier,
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(image),
                contentScale = ContentScale.Crop,
                contentDescription = title,
                modifier = Modifier
                    .size(80.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold)
                Text(
                    text = "Rp $price",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RewardItemPreview() {
    FleemTheme {
        FilmItem(R.drawable.sevensamurai, "cek",  4000)
    }
}