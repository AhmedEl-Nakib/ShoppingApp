package com.example.shoppingapp.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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
import androidx.navigation.NavHostController
import androidx.room.Room
import com.example.shoppingapp.FirestoreModel.FireStoreCart
import com.example.shoppingapp.FirestoreModel.FireStoreOrders
import com.example.shoppingapp.entity.Orders
import com.example.shoppingapp.room.AppDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.math.abs

@Composable
fun pay(navController: NavHostController,userID:String?) {

    val db = Room.databaseBuilder(LocalContext.current, AppDatabase::class.java, "usersDB")
        .allowMainThreadQueries().build()

    var totalCost = remember {
        mutableStateOf(0.0)
    }

    var firebaseCart = remember{
        mutableStateListOf<FireStoreCart>()
    }

    var shipping = remember{
        mutableStateOf(0.0)
    }

    var cartSize = remember{
        mutableStateOf(0)
    }

    var context = LocalContext.current

    val openDialog = remember { mutableStateOf(false)}
    if (openDialog.value){
        AlertDialog(onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Thank you for placing your order!")},
            text = { Text(text = "A confirmation message has been sent to your email. ")},
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End,
                ){
                    Button(onClick = {
                        openDialog.value = false
                        navController.navigate("NavHomeScreen"){
                        }
                    }) {
                        Text(text = "Back to shopping")
                    }
                }
            }
        )
    }

    val discountApplied = remember { mutableStateOf(false)}
    val promoCode = remember { mutableStateOf("")}
    val invalidPromoCodeValidity = remember { mutableStateOf(false)}

    Scaffold(
         content = {
             val db = Firebase.firestore
             db.collection("Cart/users/${userID}").get().addOnSuccessListener {
                 for (document in it){
                     var model = FireStoreCart(
                         document.data.get("id").toString().toInt(),
                         document.data.get("title").toString(),
                         document.data.get("price").toString().toDouble(),
                         document.data.get("description").toString(),
                         document.data.get("category").toString(),
                         document.data.get("image").toString(),
                         document.data.get("quantity").toString().toInt())

                     val check = firebaseCart.find {
                         it.id == document.data["id"].toString().toInt()
                     }
                     if(check == null){
                         firebaseCart.add(model)
                         totalCost.value += document.data.get("price").toString().toDouble() * document.data.get("quantity").toString().toInt()
                         cartSize.value +=1
                     }
                 }
                 shipping.value = totalCost.value*0.2
             }
            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)) {
                Text(text = "Payment Method",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                RadioButtonSample()

                Spacer(modifier = Modifier.height(30.dp))

                Text(text = "Promo Code",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = promoCode.value,
                    onValueChange = { it ->
                        promoCode.value = it
                    },         label = {
                        Text (text = "Please Enter Promo Code")
                    }
                )

                if(invalidPromoCodeValidity.value){
                    Text(text = "Invalid Promo Code",
                        style = TextStyle(
                            color = Color.Red,
                            fontSize = 15.sp
                        ))
                }
                Spacer(modifier = Modifier.height(10.dp).padding(10.dp).fillMaxWidth())

                Button(onClick = {
                    if (promoCode.value == "CMPS315"){
                        discountApplied.value = true
                        invalidPromoCodeValidity.value = false
                    }
                    else {
                        invalidPromoCodeValidity.value = true
                    }
                }) {
                    Text(text = "Apply")
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(text = "Payment Details",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(corner = CornerSize(16.dp))
                    ) {
                        Column(modifier = Modifier
                            .padding(20.dp)
                            .fillMaxSize()) {
                            Row( horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxSize()) {
                                Text(
                                    text = "Total",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 15.sp
                                    )
                                )
                                Text(
                                    text = totalCost.value.toString(),
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 15.sp
                                    )
                                )
                            }
                            Row( horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxSize()) {
                                Text(
                                    text = "Delivery",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 15.sp
                                    )
                                )
                                Text(
                                    text = shipping.value.toString(),
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 15.sp
                                    )
                                )
                            }
                            Row( horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxSize()) {
                                Text(
                                    text = "Discount",
                                    style = TextStyle(
                                        color = Color.Red,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                )
                                Text(
                                    text = if (discountApplied.value) {
                                        (totalCost.value*0.1).toString()
                                    } else "0.0",
                                    style = TextStyle(
                                        color = Color.Red,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                )
                            }
                            Row( horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxSize()) {
                                Text(
                                    text = "Grand Total",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                )
                                Text(
                                    text = if (discountApplied.value) {
                                        (totalCost.value + shipping.value - (totalCost.value*0.1)).toString()
                                    } else (totalCost.value + shipping.value).toString(),
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                )
                            }
                        }
                    }
                }
                Button(onClick = {
                    val formatter: DateFormat = SimpleDateFormat("dd-MM-yyyy")
                    val str_date = formatter.format(Date())
                    val date: Date = formatter.parse(str_date) as Date

                    val random = abs((0..999999999999).random())

                    try {
                        val db = Firebase.firestore
                        var model = FireStoreOrders(cartSize.value, if (discountApplied.value) {
                            (totalCost.value + shipping.value - (totalCost.value * 0.1)).toInt()
                        } else (totalCost.value + shipping.value).toInt(),userID!!,date.time,random)

                        db.collection("Orders").add(model).addOnCompleteListener{
                            if(it.isSuccessful){
                                db.collection("Cart/users/${userID}").get().addOnCompleteListener{
                                    for (item in it.result){
                                        item.reference.delete()
                                    }
                                    openDialog.value = true
                                }
                            }
                        }
                        /*var cart = db.CartDataAccessObject().getAllCart()
                        db.OrdersDAO().addOrder(
                            Orders(
                                cart.size, if (discountApplied.value) {
                                    (totalCost + shipping - (totalCost * 0.1)).toInt()
                                } else (totalCost + shipping).toInt(), cart,userID!!,date.time
                            )
                        )
                        db.CartDataAccessObject().deleteAllCartData()*/
                    } catch (E: Exception){
                        Toast.makeText(context, "Error ${E.message}", Toast.LENGTH_LONG)
                            .show()
                    }
                }, modifier = Modifier.padding(10.dp).fillMaxWidth()
                ) {
                    Text(text = "Confirm Payment")
                }
            }
        })
}

@Composable
fun RadioButtonSample() {
    val radioOptions = listOf("Credit / Debit Card", "Cash on Delivery")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0] ) }
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Column() {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                            }
                        )
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }
        }
    }
}