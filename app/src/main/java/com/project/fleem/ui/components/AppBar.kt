package com.project.fleem.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navigateToAboutMe: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = "Fleem",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary)
        },
        actions = {
            IconButton(onClick = { navigateToAboutMe() }) {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "About Me")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutMeAppBar(
    navigateBack: () -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { navigateBack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Menu")
            }
        },
        title = {
            Text(text = "About Me",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary)
        }
    )
}