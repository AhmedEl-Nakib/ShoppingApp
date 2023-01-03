package com.example.shoppingapp.view

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import com.example.shoppingapp.FirestoreModel.FireStoreOrders
import com.example.shoppingapp.Screens
import com.example.shoppingapp.room.AppDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun AdminOrdersPage(order: FireStoreOrders,navController: NavController, event: () -> Unit) {

    val db = Room.databaseBuilder(LocalContext.current, AppDatabase::class.java, "usersDB")
        .allowMainThreadQueries().build()

    Card(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable { navController.navigate(Screens.ChangeOrderStatus + "/${order.orderID}") },
        shape = RoundedCornerShape(corner = CornerSize(16.dp))

    ) {
        Row(modifier = Modifier.padding(20.dp)) {
            Column(
                modifier = Modifier.weight(1f),
                Arrangement.Center
            ) {
                Text(
                    text = "ID :" + order.userID,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    maxLines = 2
                )
                Text(
                    text = "Status: " + order.status,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                )
                Text(
                    text = "Total Amount: " + order.totalAmount,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                )
                Text(
                    text = "Total Count: " + order.totalCount,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }}

@SuppressLint("SuspiciousIndentation")
@Composable
fun AdminShowOrdersScreen(navController: NavController,navStatus:String?) {

    //val db = Room.databaseBuilder(LocalContext.current, AppDatabase::class.java, "usersDB").allowMainThreadQueries().build()

    var status = remember{
        mutableStateOf("Processing")
    }

    var firebaseOrders = remember{
        mutableStateListOf<FireStoreOrders>()
    }

    val db = Firebase.firestore

    var context = LocalContext.current

    Scaffold(
//        bottomBar = { BottomNavigationBar(navController) },
        content = {
            db.collection("Orders").whereEqualTo("status",navStatus).get().addOnSuccessListener {
                for (document in it){
                    var model = FireStoreOrders(
                        document.data.get("totalCount").toString().toInt(),
                        document.data.get("totalAmount").toString().toInt(),
                        document.data.get("userID").toString(),
                        document.data.get("insertedDate").toString().toLong(),
                        document.data.get("orderID").toString().toLong(),
                        document.data.get("status").toString()
                    )

                    val check = firebaseOrders.find {
                        it.orderID == document.data["orderID"].toString().toLong()
                    }

                    if(check == null){
                        firebaseOrders.add(model)
                    }
                }
            }

            Column(modifier = Modifier.padding(10.dp)) {

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Button(
                        onClick = {
                                  status.value = "Processing"
                            navController.navigate(Screens.AdminShowOrder+"/${status.value}") {
                                popUpTo(Screens.ADMIN_HOME)
                            }
                        })
                    {
                        Text(text = "Processing", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Button(
                        onClick = {
                            status.value = "Shipped"
                            navController.navigate(Screens.AdminShowOrder+"/${status.value}") {
                                popUpTo(Screens.ADMIN_HOME)
                            }
                        })
                    {
                        Text(text = "Shipped", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Button(
                        onClick = {
                            status.value = "Delivered"
                            navController.navigate(Screens.AdminShowOrder+"/${status.value}") {
                                popUpTo(Screens.ADMIN_HOME)
                            }
                        })
                    {
                        Text(text = "Delivered", color = Color.White)
                    }
                }

                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(
                        firebaseOrders
                    ) {
                        AdminOrdersPage(order = it,navController) {
                            navController.navigate("NavCartScreen") {
                                popUpTo("NavHomeScreen")
                            }
                        }
                    }
                }
            }
        })
}