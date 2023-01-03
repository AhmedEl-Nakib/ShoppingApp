package com.example.shoppingapp

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.room.Room
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.FirestoreModel.FireStoreCart
import com.example.shoppingapp.room.AppDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun ProductCard(product: FireStoreCart, userID: String?, event: () -> Unit) {

    var showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        Dialog(
            onDismissRequest = { showDialog.value = false },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator()
            }
        }
    }

    val firebaseCart = Firebase.firestore
    var context = LocalContext.current

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
                    text = "Title :" + product.title,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    maxLines = 2
                )
                Text(
                    text = "Price: " + product.price,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                )
                Text(
                    text = "Category: " + product.category,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                )

                Row {
                    Image(painter = painterResource(id = R.drawable.ic_baseline_add_circle_24),
                        contentDescription = "",
                        modifier = Modifier
                            .size(20.dp, 20.dp)
                            .clickable {
                                showDialog.value = true

                                firebaseCart
                                    .collection("Cart/users/${userID}")
                                    .document(product.id.toString())
                                    .get()
                                    .addOnSuccessListener {
                                        val newQuantity = it
                                            .get("quantity")
                                            .toString()
                                            .toInt() + 1
                                        it.reference
                                            .update("quantity", newQuantity)
                                            .addOnSuccessListener {
                                                showDialog.value = false
                                                event()
                                            }
                                    }
                                /*
                                val newQuantity = product.quantity + 1
                                db
                                    .CartDataAccessObject()
                                    .updateCartProduct(
                                        quantity = newQuantity.toString(),
                                        ID = product.id.toString()
                                    )
                                event()*/
                            })
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = product.quantity.toString(),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(painter = painterResource(id = R.drawable.ic_baseline_remove_circle_24),
                        contentDescription = "",
                        modifier = Modifier
                            .size(20.dp, 20.dp)
                            .clickable {

                                showDialog.value = true

                                firebaseCart
                                    .collection("Cart/users/${userID}")
                                    .document(product.id.toString())
                                    .get()
                                    .addOnSuccessListener {
                                        if (it
                                                .get("quantity")
                                                .toString()
                                                .toInt() > 1
                                        ) {
                                            val newQuantity = it
                                                .get("quantity")
                                                .toString()
                                                .toInt() - 1
                                            it.reference
                                                .update("quantity", newQuantity)
                                                .addOnSuccessListener {
                                                    showDialog.value = false
                                                    event()
                                                }
                                        }
                                    }
//                                if (product.quantity > 1) {
//                                    val newQuantity = product.quantity - 1
//                                    db
//                                        .CartDataAccessObject()
//                                        .updateCartProduct(
//                                            quantity = newQuantity.toString(),
//                                            ID = product.id.toString()
//                                        )
//                                    event()
//                                }
                            })
                }
                Spacer(modifier = Modifier.height(10.dp))

                Image(painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp, 30.dp)
                        .clickable {
                            firebaseCart
                                .collection("Cart/users/${userID}")
                                .document(product.id.toString())
                                .delete()
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        Toast
                                            .makeText(
                                                context,
                                                "Deleted from Cart",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                        event()
                                    } else {
                                        Toast
                                            .makeText(
                                                context,
                                                "Error ${it.exception!!.message}",
                                                Toast.LENGTH_LONG
                                            )
                                            .show()
                                    }
                                }
                            /*db
                                .CartDataAccessObject()
                                .deleteFromCartByID(product.id.toString())
                            event()*/
                        })
            }
            Image(painter = rememberAsyncImagePainter(product.image),
                "",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .padding(8.dp)
                    .size(90.dp)
                    .clip((CircleShape)))
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun ShowDataScreen(navController: NavController, item:String?) {

   // val db = Room.databaseBuilder(LocalContext.current, AppDatabase::class.java, "usersDB")
       // .allowMainThreadQueries().build()

//    var totalCost = 0.0
    /*db.CartDataAccessObject().getAllCart().forEach { product ->
        totalCost += product.price * product.quantity
    }*/

    var context = LocalContext.current

    var firebaseCart = remember{
        mutableStateListOf<FireStoreCart>()
    }

    var totalCost = remember {
        mutableStateOf(0.00)
    }
    val db = Firebase.firestore

    Toast.makeText(context, "${item}", Toast.LENGTH_LONG).show()
    Scaffold(
//        bottomBar = { BottomNavigationBar(navController) },
       content = {

           db.collection("Cart/users/${item}").get().addOnSuccessListener {
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
                   }
               }
           }
            Column(modifier = Modifier.padding(10.dp)) {

                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(
                        firebaseCart
                    ) {
                        ProductCard(product = it,item) {
                            navController.navigate("NavCartScreen/${item}") {
                                popUpTo("NavHomeScreen")
                            }
                        }
                    }
                }
                Text(
                    text = "Total Cost: " + totalCost.value.toFloat() + " QAR",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )

                Button(onClick = {
                   if(firebaseCart.isEmpty())
                    {
                        Toast.makeText(context, "Your cart is empty!", Toast.LENGTH_LONG).show()
                    }else
                    navController.navigate("ShowAllAddress/${item}")

                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Check out")
                }
                Spacer(modifier = Modifier.height(300.dp))
                Text(text = " ")
            }
        })
}