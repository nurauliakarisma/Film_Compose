package com.project.fleem.ui.nav

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object About : Screen("about")
    object Detail : Screen("home/{filmId}") {
        fun createRoute(filmId: Long) = "home/$filmId"
    }
}
