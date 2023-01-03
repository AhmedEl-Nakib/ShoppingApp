package com.example.shoppingapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shoppingapp.Screens

@Composable
fun AdminHomeScreenPage(navController: NavController) {

    ScaffoldWithTopBar(navController)
}


@Composable
fun ScaffoldWithTopBar(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "ADMIN PAGE")
                },
//                navigationIcon = {
//                    IconButton(onClick = {
//                        navController.navigateUp()
//                    }) {
//                        Icon(Icons.Filled.ArrowBack, "backIcon")
//                    }
//                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                elevation = 10.dp
            )
        }, content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Welcome to QShopping", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(30.dp))
                Button(onClick = {
                    navController.navigate(Screens.AddProductScreen)
                }) {
                    Text(text = "Add New Product")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {
                    navController.navigate(Screens.ADMIN_SHOW_PRODUCTS)
                }) {
                    Text(text = "Show Products")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {
                    navController.navigate(Screens.AdminReport)
                }) {
                    Text(text = "Reports")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {
                    navController.navigate(Screens.AdminShowOrder+"/Processing")
                }) {
                    Text(text = "Show Orders")
                }
//                Spacer(modifier = Modifier.height(10.dp))
//                Button(onClick = {
//                    navController.navigate(Screens.ADD_REVIEW)
//                }) {
//                    Text(text = "Add review to product")
//                }
//                Spacer(modifier = Modifier.height(10.dp))
//                Button(onClick = {
//                    navController.navigate(Screens.SHOW_REVIEWS)
//                }) {
//                    Text(text = "View products reviews")
//                }
            }
        })
}
