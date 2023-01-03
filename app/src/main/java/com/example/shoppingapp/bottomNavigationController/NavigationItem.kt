package com.example.shoppingapp.bottomNavigationController

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.shoppingapp.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("NavHomeScreen", R.drawable.ic_baseline_home_24 , "Home")
    object Cart : NavigationItem("NavCartScreen", R.drawable.ic_baseline_shopping_cart_24, "Cart")
    object Favorite : NavigationItem("NavWishlistScreen", R.drawable.ic_baseline_favorite_24, "Favorite")
    object Orders : NavigationItem("NavOrdersScreen", R.drawable.ic_baseline_airport_shuttle_24, "Orders")
}