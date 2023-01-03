package com.example.shoppingapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.shoppingapp.FirestoreModel.FireStoreOrders
import com.example.shoppingapp.room.AppDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable

fun AdminReportDetails(fromTime: String?, toTime: String?, status: String?){

   // val db = Room.databaseBuilder(LocalContext.current, AppDatabase::class.java, "usersDB") .allowMainThreadQueries().build()

    val db = Firebase.firestore

    var firebaseOrders = remember{
        mutableStateListOf<FireStoreOrders>()
    }

    var total_amount = remember{ mutableStateOf(0)}
    var total_count = remember { mutableStateOf(0)}

    /*
    val list :List<Orders>

    list = if(status == "All")
        db.OrdersDAO().getOrdersTime(fromTime!!.toLong(),toTime!!.toLong())
    else
        db.OrdersDAO().getOrdersTime(fromTime!!.toLong(),toTime!!.toLong(),status!!)
    Log.i("adminreport", "AdminReportDetails: ${list.toString()}")

    var total_amount = remember{ mutableStateOf(0)}
    var total_count = remember { mutableStateOf(0) }

    if(status == "Processing" ){
        list.forEach {
            if (it.status == "Processing") {
                total_amount.value = total_amount.value + it.totalAmount
                total_count.value = total_count.value + it.totalCount
            }
        }
    }
    if(status == "Shipped"){
        list.forEach {
            if (it.status == "Shipped") {
                total_amount.value = total_amount.value + it.totalAmount
                total_count.value = total_count.value + it.totalCount
            }
        }
    }
    if(status == "Delivered"){
        list.forEach {
            if (it.status == "Delivered") {
                total_amount.value = total_amount.value + it.totalAmount
                total_count.value = total_count.value + it.totalCount
            }
        }
    }
    if(status == "All"){
        list.forEach{
            total_amount.value = total_amount.value + it.totalAmount
            total_count.value = total_count.value + it.totalCount
        }
    }
*/
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top, content = {

        if(status == "All"){
            db.collection("Orders").get().addOnSuccessListener {
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
                        if (document.data.get("insertedDate").toString().toLong() > fromTime!!.toLong() &&
                            document.data.get("insertedDate").toString().toLong() < toTime!!.toLong()) {
                            total_amount.value = total_amount.value + document.data.get("totalAmount").toString().toInt()
                            total_count.value = total_count.value + document.data.get("totalCount").toString().toInt()
                            firebaseOrders.add(model)
                        }
                    }
                }
            }
        }
        else{
            db.collection("Orders").whereEqualTo("status",status).get().addOnSuccessListener {
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
                        if (document.data.get("insertedDate").toString().toLong() > fromTime!!.toLong() &&
                            document.data.get("insertedDate").toString().toLong() < toTime!!.toLong()) {
                            total_amount.value = total_amount.value + document.data.get("totalAmount").toString().toInt()
                            total_count.value = total_count.value + document.data.get("totalCount").toString().toInt()
                            firebaseOrders.add(model)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp).padding(16.dp))

        Row() {
            Text(text = "Total Amount: " )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "${total_amount.value}")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row() {
            Text(text = "Total Count: " )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "${total_count.value}")
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                firebaseOrders
            ) {
                AdminReport(order = it)
            }
        }
    })
}

@Composable
fun AdminReport(order: FireStoreOrders) {

    val db = Room.databaseBuilder(LocalContext.current, AppDatabase::class.java, "usersDB")
        .allowMainThreadQueries().build()

    Card(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))

    ) {
        Row(modifier = Modifier.padding(20.dp)) {
            Column(
                modifier = Modifier.weight(1f),
                Arrangement.Center
            ) {
                Text(
                    text = "ID :" + order.orderID,
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
    }
}
