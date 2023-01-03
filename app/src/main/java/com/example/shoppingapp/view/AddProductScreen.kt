package com.example.shoppingapp.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
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
import com.example.shoppingapp.room.AppDatabase
import com.example.shoppingapp.entity.Product

@Composable
fun AddProductScreenPage(navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add Product")
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
                val title = remember {
                    mutableStateOf("")
                }

                val price = remember {
                    mutableStateOf("")
                }

                val description = remember {
                    mutableStateOf("")
                }

                val category = remember {
                    mutableStateOf("")
                }

                val rate = remember {
                    mutableStateOf("")
                }

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(value = title.value, onValueChange = { it ->
                    title.value = it
                },
                    label = {
                        Text(text = "Please Enter Title")
                    })

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = price.value, onValueChange = { it ->
                    price.value = it
                },
                    label = {
                        Text(text = "Please Enter Price")
                    },keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ))
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = description.value, onValueChange = { it ->
                    description.value = it
                }, label = {
                    Text(text = "Please Enter Description")
                })
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = category.value, onValueChange = { it ->
                    category.value = it
                }, label = {
                    Text(text = "Please Enter Category")
                }
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = rate.value, onValueChange = { it ->
                    rate.value = it
                }, label = {
                    Text(text = "Please Enter Rate")
                },keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
                )
                Spacer(modifier = Modifier.height(10.dp))


                Button(onClick = {
                    try {
                        if(title.value.isEmpty() || price.value.isEmpty() || description.value.isEmpty() || category.value.isEmpty()){
                            Toast.makeText(context, "All fields are mandatory", Toast.LENGTH_LONG).show()
                        }else{
                            val db = Room.databaseBuilder(context, AppDatabase::class.java, "usersDB")
                                .allowMainThreadQueries().build()
                            db.ProductDao().addProduct(
                                Product(
                                    title.value,
                                    price.value.toDouble(),
                                    description.value,
                                    category.value,
                                    "",
                                    rate.value.toDouble()
                                )
                            )
                            Toast.makeText(context, "Product Added Successfully", Toast.LENGTH_LONG).show()
                        }
                    } catch (e: java.lang.Exception) {
                        Toast.makeText(context, "Err ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }) {
                    Text(text = "Add to database")
                }
            }
        })


}