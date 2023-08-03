package com.project.fleem

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.fleem.ui.nav.Screen
import com.project.fleem.ui.screen.aboutme.AboutMeScreen
import com.project.fleem.ui.screen.cart.CartScreen
import com.project.fleem.ui.screen.detail.DetailScreen
import com.project.fleem.ui.screen.home.HomeScreen

@Composable
fun FleemApp(
    navController: NavHostController = rememberNavController(),
) {

    Scaffold {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToAboutMe = {
                        navController.navigate(Screen.About.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    navigateToDetail = { filmId ->
                        navController.navigate(Screen.Detail.createRoute(filmId))
                    },
                    navigateToCart = {
                        navController.navigate(Screen.Cart.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("filmId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("filmId") ?: -1L
                DetailScreen(
                    filmId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screen.Cart.route) {
                CartScreen()
            }
            composable(Screen.About.route) {
                AboutMeScreen(navigateBack = {
                    navController.navigateUp()
                })
            }
        }

    }
}
