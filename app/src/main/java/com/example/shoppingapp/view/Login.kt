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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun Logo() {
    Row {
        Text("T", fontSize = 50.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
        Text(
            text = "REND",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp),
            color = Color.LightGray
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginPreview2() {
    Login(navController = NavController(LocalContext.current))
}

@Composable
fun Login(navController: NavController) {

    var context = LocalContext.current

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

    Box(modifier = Modifier.fillMaxSize()) {
//        Logo()
        ClickableText(
            text = AnnotatedString("Sign up here."),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(40.dp),
            onClick = {}
        )
    }
    /* Column(
         horizontalAlignment = Alignment.CenterHorizontally,
         verticalArrangement = Arrangement.Top
     ){
         //Text("T", fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align.width(40.dp))
     }*/
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Login", fontSize = 30.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(20.dp))

        var text by remember { mutableStateOf("") }
        OutlinedTextField(value = text, onValueChange = { newText -> text = newText },

            maxLines = 1,
            leadingIcon = {
                IconButton(onClick = {/*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Email Icon"
                    )
                }
            },
            placeholder = { Text(text = "Email....") },
            label = { Text(text = "Email") },
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

        Spacer(modifier = Modifier.height(20.dp))

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
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(10.dp))
        //Spacer(modifier = Modifier.width(70.dp))

        Button(
            // modifier =
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center,

            onClick = {
                //val db = Room.databaseBuilder(context, AppDatabase::class.java, "usersDB")
                //.allowMainThreadQueries().build()

                if (text == "admin" && password == "admin") {
                    navController.navigate(Screens.ADMIN_HOME)
                } else {
                    /*if (db.UsersDao().getUser(text, password).isNotEmpty()) {
                        var userID = db.UsersDao().getUser(text, password).get(0).userId
                        navController.navigate(Screens.NEW_HOME+"/${userID}")
                    } else {
                        Toast.makeText(context, "Invalid email or password", Toast.LENGTH_LONG)
                            .show()
                    }*/

                    var auth = FirebaseAuth.getInstance()
                    showDialog.value = true
                    auth.signInWithEmailAndPassword(text, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            navController.navigate(Screens.NEW_HOME + "/${auth.currentUser!!.uid}")
                        } else {
                            Toast.makeText(
                                context,
                                "Error ${it.exception!!.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        showDialog.value = false
                    }
                }
            }, modifier = Modifier.width(100.dp)
        )
        {
            Text(text = "Login", color = Color.White)
        }
    }
}

@Composable
fun LoginPreview(navController: NavController) {
    ShoppingAppTheme {
        Login(navController)
    }
}
