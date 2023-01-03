package com.example.shoppingapp.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.FirestoreModel.FireStoreWishlist
import com.example.shoppingapp.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//Aya
@Composable
fun WishlistPage(nController: NavHostController,userId:String?) {
    var context = LocalContext.current

    var firebaseWishlist = remember{
        mutableStateListOf<FireStoreWishlist>()
    }

    Scaffold(
//        bottomBar = { BottomNavigationBar(navController) },
        content = {

            val db = Firebase.firestore

            db.collection("Favorite/users/${userId}").get().addOnSuccessListener {
                for (document in it){
                    var model = FireStoreWishlist(document.data.get("id").toString().toInt(),
                        document.data.get("title").toString(),
                        document.data.get("price").toString().toDouble(),
                        document.data.get("description").toString(),
                        document.data.get("category").toString(),
                        document.data.get("image").toString())

                    val check = firebaseWishlist.find {
                        it.id == document.data["id"].toString().toInt()
                    }
                    if(check == null){
                        firebaseWishlist.add(model)
                    }
                }
            }
            Column(modifier = Modifier.padding(10.dp)) {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(
                        firebaseWishlist.distinct()
                    ) {
                        WishlistCard(it,userId) {
                            nController.navigate("NavWishlistScreen/${userId}") {
                                popUpTo("NavHomeScreen")
                            }
                        }
                    }
                }

            }

        })
}

@Composable
fun WishlistCard(wishlist: FireStoreWishlist, userID:String?,event: () -> Unit) {
    val firebaseWishlist = Firebase.firestore
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))

    ) {
        Row(modifier = Modifier.padding(20.dp)) {
            Column(
                modifier = Modifier.weight(1f),
                Arrangement.Center
            ) {
                Text(
                    text = "Title :" + wishlist.title,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    maxLines = 2
                )
                Text(
                    text = "Price: " + wishlist.price,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                )
//                Text(
//                    text = "Description :- " + product.description,
//                    style = TextStyle(
//                        color = Color.Black,
//                        fontSize = 15.sp,
//                        fontWeight = FontWeight.Bold,
//                    )
//                )
                Text(
                    text = "Category: " + wishlist.category,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                )
                /*Image(
                    painter = painterResource( id= R.drawable.bin),
                    contentDescription = "",
                    modifier = Modifier.height(30.dp).width(30.dp).clickable {
                    db.userDao().deleteUserByID(emp.id.toString())
                        event()
                    }
                )*/

                Image(painter = painterResource(id = R.drawable.ic_baseline_favorite_border_24),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp, 30.dp)
                        .clickable {
                            firebaseWishlist.collection("Favorite/users/${userID}")
                                .document(wishlist.id.toString()).delete().addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        Toast
                                            .makeText(context, "Deleted from wishlist", Toast.LENGTH_SHORT)
                                            .show()
                                        event()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Error ${it.exception!!.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                        })
            }
            Image(painter = rememberAsyncImagePainter(wishlist.image),
                "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(8.dp)
                    .size(90.dp))
        }
    }
}
