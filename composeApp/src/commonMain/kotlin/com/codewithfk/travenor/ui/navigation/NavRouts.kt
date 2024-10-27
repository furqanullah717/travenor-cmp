package com.codewithfk.travenor.ui.navigation

sealed class NavRouts(val route: String) {
    data object Loading : NavRouts("loading")
    data object Login : NavRouts("login")
    data object Home : NavRouts("home")
    data object Register : NavRouts("register")
}