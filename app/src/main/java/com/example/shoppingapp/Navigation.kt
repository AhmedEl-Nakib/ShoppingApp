package com.example.shoppingapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.bottomNavigationController.NavigationItem
import com.example.shoppingapp.view.*

@Composable
fun Nav(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.SPLASH_SCREEN){


//        composable(Screens.HomeScreen){
//            HomeScreenPage(navController)
//        }

        composable(Screens.AddProductScreen){
            AddProductScreenPage(navController)
        }
//        composable(Screens.ShowDataScreen){
//            ShowDataScreen(navController)
//        }

        composable(Screens.AddNewAddress + "/{userID}"){
            val item  = it.arguments?.getString("userID")
            AddNewAddressScreenPage(navController, item)
        }

        /*composable(Screens.ShowAllAddress+ "/{userID}"){
            val item  = it.arguments?.getString("userID")
            ShowAddressScreen(navController, item)
        }*/

//        composable(Screens.ADD_REVIEW){
//            AddReview()
//        }
        composable(Screens.SHOW_REVIEWS){
            Review()
        }
        composable(Screens.ON_BOARDING){
            OnBoarding(navController = navController)
        }
        composable(Screens.LOGIN){
            LoginPreview(navController = navController)
        }

        composable(Screens.REGISTRATION){
            RegistrationPreview(navController = navController)
        }
        composable(Screens.NEW_HOME + "/{userID}" ){
            val item  = it.arguments?.getString("userID")
            MainScreen(navController = navController,item)
        }
        composable(Screens.ADMIN_HOME){
            AdminHomeScreenPage(navController = navController)
        }
        composable(Screens.ADMIN_SHOW_PRODUCTS){
            AdminShowProducts(navController = navController)
        }
        composable(Screens.ADMIN_UPDATE_PRODUCTS+"/{productId}"){
            val item  = it.arguments?.getString("productId")
            AdminUpdateProduct(navController = navController,item)
        }

        composable(Screens.SPLASH_SCREEN){
            SplashScreen(navController = navController)
        }

        composable(Screens.AdminShowOrder + "/{status}"){
            val item  = it.arguments?.getString("status")
            AdminShowOrdersScreen(navController = navController,item)
        }
       composable(Screens.ChangeOrderStatus+"/{orderID}") {
           val item  = it.arguments?.getString("orderID")
           ChangeOrderStatusScreen(navController = navController,item)
       }

        composable(Screens.AdminReportDetails+"/{fromTime}/{toTime}/{status}") {
            val fromTime  = it.arguments?.getString("fromTime")
            val toTime = it.arguments?.getString("toTime")
            val status = it.arguments?.getString("status")
            AdminReportDetails(fromTime,toTime,status)
        }

//        composable("ProductDetails/{productId}") {
//            val item  = it.arguments?.getString("productId")
//            ProductDetails(item)
//        }

        // bottom navigation
        composable(Screens.AdminReport){
            AdminReportPage(navController = navController)
        }
    }


}