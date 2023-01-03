package com.example.shoppingapp.view
//amna

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.shoppingapp.Screens
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.google.firebase.auth.FirebaseAuth

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RegistrationPreview2(){
    Registration(navController = NavController(LocalContext.current))
}

@Composable
fun Registration(navController: NavController){
    val context = LocalContext.current

    var showDialog = remember{
        mutableStateOf(false)
    }

    if (showDialog.value) {
        Dialog(
            onDismissRequest = { showDialog.value = false },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment= Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(White, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator()
            }
        }
    }

    Box( modifier = Modifier.fillMaxSize() ){
        ClickableText(
            text = AnnotatedString("Login here."),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(40.dp),
            onClick = {
                navController.navigate(Screens.LOGIN)
            }
        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){

        Text("Registeration", fontSize = 30.sp, fontWeight = FontWeight.Bold )
        Spacer(modifier = Modifier.height(20.dp))

        ////////////////// Email textfield
        var email by remember { mutableStateOf("" ) }
        OutlinedTextField( value = email, onValueChange = {
                newText -> email = newText },
            maxLines = 1 ,
            placeholder = {Text(text = "Email....")},
            label = { Text(text="Email")},
            //label
            leadingIcon = {
                IconButton(onClick = {/*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Email Icon"
                    )
                }
            },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    Log.d("ImeAction", "Clicked")
                }
            )
        )
        Spacer(modifier = Modifier.height(10.dp))

        //////////////////Password textfield
        var password by rememberSaveable { mutableStateOf("") }
        var passwordVisibility by remember { mutableStateOf(false) }
        // val icon = if(passwordVisibility)
        //    painterResource(id = R.drawable.design_ic_visibility)
        // else
        //   painterResource(id = R.drawable.design_ic_visibility_off)
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            placeholder = { Text(text = "Password") },
            label = { Text(text = "Password") },
//            trailingIcon = {
//                IconButton(
//                    onClick = {
//                        passwordVisibility = !passwordVisibility
//                    }) {
//                    //  Icon(
//                    //  painter = icon,
//                    //  contentDescription = "Visibility Icon"
//                    //  )
//                }
//            },
            leadingIcon = {
                IconButton(onClick = {/*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Email Icon"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if(passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(10.dp))

        /////////////// firstname field
        //var firstname by rememberSaveable { mutableStateOf("") }
        /*OutlinedTextField(
            value = firstname,
            onValueChange = {
                firstname = it
            },
            placeholder = { Text(text = "First name") },
            label = { Text(text = "First name") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),

            leadingIcon = {
                IconButton(onClick = {/*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Email Icon"
                    )
                }
            },
        )*/


        //Spacer(modifier = Modifier.height(10.dp))

        /////////////// lastname field
       /* var lastname by rememberSaveable { mutableStateOf("") }
        OutlinedTextField(
            value = lastname,
            onValueChange = {
                lastname = it
            },
            placeholder = { Text(text = "Last name") },
            label = { Text(text = "Last name") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),

            leadingIcon = {
                IconButton(onClick = {/*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Email Icon"
                    )
                }
            },
        )*/

       // Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                /*val db = Room.databaseBuilder(context, AppDatabase::class.java, "usersDB")
                    .allowMainThreadQueries().build()
                db.UsersDao().addUser(Users(firstname,lastname,email,password))*/
                showDialog.value = true
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(context, "Account created successfully", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(context, "Error ${it.exception!!.message}", Toast.LENGTH_LONG).show()
                    }
                    showDialog.value=false
                }
            }, modifier = Modifier.width(100.dp))
        {
            Text(text = "Register", color = Color.White)
        }
    }//end of column
}//end of function

@Composable
fun RegistrationPreview(navController: NavController) {
    ShoppingAppTheme {
        Registration(navController = navController)
    }
}