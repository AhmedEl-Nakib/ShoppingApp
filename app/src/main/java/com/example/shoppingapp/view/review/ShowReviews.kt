package com.example.shoppingapp.view.review

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import androidx.room.Room
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.FirestoreModel.FireStoreReview
import com.example.shoppingapp.FirestoreModel.FireStoreWishlist
import com.example.shoppingapp.R
import com.example.shoppingapp.entity.Cart
import com.example.shoppingapp.entity.UserReview
import com.example.shoppingapp.room.AppDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun ReviewCard(userReview: FireStoreReview) {

    val db = Room.databaseBuilder(LocalContext.current, AppDatabase::class.java, "usersDB")
        .allowMainThreadQueries().build()

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
                    text = "Username :" + userReview.userName,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
                Text(
                    text = "Review :- " + userReview.review,
                    style = TextStyle(
                        color = Color.Blue,
                        fontSize = 15.sp
                    )
                )
//                Image(
//                    painter = painterResource( id= R.drawable.bin),
//                    contentDescription = "",
//                    modifier = Modifier.height(30.dp).width(30.dp).clickable {
//                        db.getUserReviewDao()
//                            .deleteFromReviewByID(userReview.userReviewID.toString())
//                    }
//                )

            }
//            Image(painter = rememberAsyncImagePainter(userReview.image),
//                "",
//                contentScale = ContentScale.FillHeight,
//                modifier = Modifier
//                    .padding(8.dp)
//                    .size(110.dp)
//                    .clip((CircleShape)))
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun ShowReviewsScreen(item:String?) {

    var firebaseReview = remember{
        mutableStateListOf<FireStoreReview>()
    }

    val db = Room.databaseBuilder(LocalContext.current, AppDatabase::class.java, "usersDB")
        .allowMainThreadQueries().build()

    Scaffold(
//        bottomBar = { BottomNavigationBar(navController) },
        content = {
            val db = Firebase.firestore

            db.collection("Reviews/product/${item}").get().addOnSuccessListener {
                for (document in it){
                    var model = FireStoreReview(
                        document.data.get("productID").toString(),
                        document.data.get("review").toString(),
                        document.data.get("userName").toString())

                        firebaseReview.add(model)
                }
            }
            Column(modifier = Modifier.padding(10.dp)) {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(
                      firebaseReview
                    ) {
                        ReviewCard(userReview = it)
                    }
                }
            }

        })

}