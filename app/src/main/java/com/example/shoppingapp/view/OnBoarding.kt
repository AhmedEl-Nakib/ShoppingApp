package com.example.shoppingapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shoppingapp.R
import com.example.shoppingapp.Screens

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun OnBoardingPreview2(){
    OnBoarding(navController = NavController(LocalContext.current))
}

@Composable
fun OnBoarding(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "QShopping")
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

                Image(painter = painterResource(id = R.drawable.ic_main_logo),
                    contentDescription = "Logo"
                )

                Spacer(modifier = Modifier.height(50.dp))
                Button(onClick = {
                    navController.navigate(Screens.REGISTRATION)
                }, modifier = Modifier.width(200.dp)) {
                    Text(text = "Sign Up")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {
                    navController.navigate(Screens.LOGIN)
                }, modifier = Modifier.width(200.dp)) {
                    Text(text = "Login")
                }
            }
        })
}