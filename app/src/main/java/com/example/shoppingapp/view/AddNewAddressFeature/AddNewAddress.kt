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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shoppingapp.FirestoreModel.FireStoreAddress
import com.example.shoppingapp.entity.Address
import com.example.shoppingapp.view.AddNewAddressFeature.AddNewAddressVM
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun AddNewAddressScreenPage(navController: NavHostController,userID:String?) {

    var viewModel : AddNewAddressVM = viewModel()

    Scaffold(
         content = {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                var context = LocalContext.current
                val fullName = remember {
                    mutableStateOf("")
                }

                val country = remember {
                    mutableStateOf("")
                }

                val city = remember {
                    mutableStateOf("")
                }

                val area = remember {
                    mutableStateOf("")
                }

                val streetNumber = remember {
                    mutableStateOf("")
                }

                val house = remember {
                    mutableStateOf("")
                }

                val postalCode = remember {
                    mutableStateOf("")
                }

                val phoneNumber = remember {
                    mutableStateOf("")
                }
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = fullName.value, onValueChange = { it ->
                    fullName.value = it
                },
                    label = {
                        Text(text = "Please Enter fullName")
                    })

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = country.value, onValueChange = { it ->
                    country.value = it
                },
                    label = {
                        Text(text = "Please Enter Country")
                    })

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = city.value, onValueChange = { it ->
                    city.value = it
                }, label = {
                    Text(text = "Please Enter city")
                })

                Spacer(modifier = Modifier.height(10.dp))


                OutlinedTextField(value = area.value, onValueChange = { it ->
                    area.value = it
                }, label = {
                    Text(text = "Please Enter Area")
                },keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = streetNumber.value, onValueChange = { it ->
                    streetNumber.value = it
                }, label = {
                    Text(text = "Please Enter Street Number")
                },keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = house.value, onValueChange = { it ->
                    house.value = it
                }, label = {
                    Text(text = "Please Enter House Number")
                },keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = postalCode.value, onValueChange = { it ->
                    postalCode.value = it
                }, label = {
                    Text(text = "Please Enter Postal Code")
                },keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = phoneNumber.value, onValueChange = { it ->
                    phoneNumber.value = it
                }, label = {
                    Text(text = "Please Enter Phone Number")
                },keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = {
                    try {
                        val db = Firebase.firestore

                        val mod = FireStoreAddress(
                            fullName.value,
                            country.value,
                            city.value,
                            area.value.toInt(),
                            streetNumber.value.toInt(),
                            house.value.toInt(),
                            postalCode.value.toInt(),
                            phoneNumber.value.toInt(),
                            false
                        )

                        db.collection("Address/users/${userID}").add(mod).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(context, "Address Added Successfully", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Error ${it.exception!!.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Err ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }, modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    Text(text = "Add Address")
                }
            }
        })

}