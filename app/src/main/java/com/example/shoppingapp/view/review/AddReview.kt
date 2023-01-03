package com.example.shoppingapp.view.review

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.room.Room
import com.example.shoppingapp.FirestoreModel.FireStoreReview
import com.example.shoppingapp.FirestoreModel.FireStoreWishlist
import com.example.shoppingapp.entity.UserReview
import com.example.shoppingapp.room.AppDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

@Composable
fun AddReviewScreenPage(navController: NavHostController, item: String?) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add Review")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                elevation = 10.dp
            )
        }, content = {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                var context = LocalContext.current

                val review = remember {
                    mutableStateOf("")
                }
                val userName = remember {
                    mutableStateOf("")
                }
                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(value = userName.value, onValueChange = { it ->
                    userName.value = it
                }, label = {
                    Text(text = "Please write your name")
                },keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = review.value, onValueChange = { it ->
                    review.value = it
                }, label = {
                    Text(text = "Please write review")
                },keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
                )
                Spacer(modifier = Modifier.height(10.dp))


                Button(onClick = {
                    try {
                        if(review.value.isEmpty()){
                            Toast.makeText(context, "You need to write review", Toast.LENGTH_LONG).show()
                        }else{
                            val db = Firebase.firestore

                            val mod =  FireStoreReview(
                                item.toString(),
                                review.value,
                                userName.value
                            )

                           db.collection("Reviews/product/${item}").add(mod).addOnCompleteListener {
                               if (it.isSuccessful) {
                                   Toast.makeText(context, "Review Added Successfully", Toast.LENGTH_LONG).show()
                               } else {
                                   Toast.makeText(
                                       context,
                                       "Error ${it.exception!!.message}",
                                       Toast.LENGTH_LONG
                                   ).show()
                               }
                           }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Err ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }) {
                    Text(text = "Add review")
                }
            }
        })


}