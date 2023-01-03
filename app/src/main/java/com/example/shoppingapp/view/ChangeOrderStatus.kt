package com.example.shoppingapp.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.room.Room
import com.example.shoppingapp.room.AppDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun ChangeOrderStatusScreen(navController: NavController,item:String?){
    OrderStatus(item)
}

@Composable
fun OrderStatus(orderid:String?) {

    val db = Room.databaseBuilder(LocalContext.current, AppDatabase::class.java, "usersDB")
        .allowMainThreadQueries().build()

    val mContext = LocalContext.current

    var newStatus = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        val radioOptions = listOf("Processing", "Shipped", "Delivered")
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
                                    newStatus.value = text
                                }
                            )
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                                newStatus.value=text
                            }
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

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            val db = Firebase.firestore

            db.collection("Orders").get().addOnCompleteListener {
                if (it.isSuccessful()) {
                    for (document in it.getResult()) {
                        if(document.data.get("orderID").toString().toLong() == orderid?.toLong()){
                            document.reference.update("status",newStatus.value).addOnCompleteListener {
                                if(it.isSuccessful){
                                    Toast.makeText(mContext, "Status Successfully Changed", Toast.LENGTH_LONG).show()
                                }
                                else{
                                    Toast.makeText(mContext, it.exception?.message.toString(), Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                        /*db.collection("Orders").document(document.id).update("status",newStatus.value).addOnCompleteListener {
                            if(it.isSuccessful){
                                Toast.makeText(mContext, "Status Successfully Changed", Toast.LENGTH_LONG).show()
                            }
                            else{
                                Toast.makeText(mContext, it.exception?.message.toString(), Toast.LENGTH_LONG).show()
                            }
                        }*/
                    }
                }
                else{
                    Toast.makeText(mContext, it.exception?.message.toString(), Toast.LENGTH_LONG).show()
                }
                }
            //db.OrdersDAO().updateOrder(newStatus.value,orderid!!)
        }) {
            Text(text = "Change Status")
        }

    }
}