package com.example.shoppingapp.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
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
import com.example.shoppingapp.FirestoreModel.FireStoreAddress
import com.example.shoppingapp.FirestoreModel.FireStoreReview
import com.example.shoppingapp.room.AppDatabase
import com.example.shoppingapp.entity.Address
import com.example.shoppingapp.Screens
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun AddressCard(address: FireStoreAddress, userID:String?,event: () -> Unit) {

    val db = Room.databaseBuilder(LocalContext.current, AppDatabase::class.java, "usersDB")
        .allowMainThreadQueries().build()

    Card(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable {
                val db = Firebase.firestore

                db.collection("Address/users/${userID}").get().addOnSuccessListener {
                    for (document in it){
                      document.reference.update("homeDeliveryAddress",false)
                    }
                    for (document in it){
                        if (document.data.get("house").toString().toInt()==address.house){
                            document.reference.update("homeDeliveryAddress",true)
                        }
                    }
                }

                //db.AddressDao().updateUnselectedAddress(false)
                //db.AddressDao().updateSelectedAddress(true,address.id)
                event()
            },
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(modifier = Modifier.padding(20.dp)) {
            Column(
                modifier = Modifier.weight(1f),
                Arrangement.Center
            ) {
                Text(
                    text = address.city + "," + address.country,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
                Text(
                    text = "Area:  " + address.area,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                )
                Text(
                    text = "House/Appartment" + address.house,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp,
                    )
                )
                Text(
                    text = "Delivery home address ? " + address.homeDeliveryAddress,
                    style = TextStyle(
                        color = if (address.homeDeliveryAddress) Color.Green else Color.Red,
                        fontSize = 15.sp,
                    )
                )
            }
        }
    }
}


@Composable
fun ShowAddressScreen(navController: NavController,userID:String?) {

    val db = Room.databaseBuilder(LocalContext.current, AppDatabase::class.java, "usersDB")
        .allowMainThreadQueries().build()

    var firebaseAddress = remember{
        mutableStateListOf<FireStoreAddress>()
    }
    Scaffold(
         content = {
             val db = Firebase.firestore

                 /*db.collection("Address/users/${userID}").get().addOnSuccessListener {
                 for (document in it){
                     var model = FireStoreAddress(
                         document.data.get("fullName").toString(),
                         document.data.get("country").toString(),
                         document.data.get("city").toString(),
                         document.data.get("area").toString().toInt(),
                         document.data.get("streetNumber").toString().toInt(),
                         document.data.get("house").toString().toInt(),
                         document.data.get("postalCode").toString().toInt(),
                         document.data.get("phoneNumber").toString().toInt(),
                         document.data.get("homeDeliveryAddress").toString().toBoolean()
                     )
                     val check = firebaseAddress.find {
                         it.house == document.data["house"].toString().toInt()
                     }
                     if(check == null){
                         firebaseAddress.add(model)
                     }
                 }
             }*/
            Column(modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "Saved Address",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(
                        firebaseAddress
                    ) {
                        AddressCard(address = it,userID) {
                            navController.navigate(Screens.ShowAllAddress+"/${userID}") {
                                popUpTo(Screens.ShowDataScreen)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))

                Button(onClick = {
                    navController.navigate("AddNewAddress/${userID}")
                }) {
                    Text(text = "Add New Address")
                }

                Button(onClick = {
                    navController.navigate("Payment/${userID}")
                }) {
                    Text(text = "Proceed to Payment")
                }

            }

        })

}