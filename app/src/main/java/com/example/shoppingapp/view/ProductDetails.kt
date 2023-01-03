package com.example.shoppingapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.room.Room
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.R
import com.example.shoppingapp.room.AppDatabase

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProductDetailsPreview2(){
    ProductDetails("",nController = NavHostController(LocalContext.current))
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProductDetails(item: String?, nController: NavHostController) {
    val context = LocalContext.current
    val db = Room.databaseBuilder(LocalContext.current, AppDatabase::class.java, "usersDB")
        .allowMainThreadQueries().build()

    var product = db.ProductDao().getProductById(item!!)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(800.dp)
            .padding(top = 20.dp)) {
        Column(horizontalAlignment = Alignment.Start) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .height(200.dp),
                elevation = 2.dp,
                backgroundColor = Color.White,
                ){
                Image(
                    painter = rememberAsyncImagePainter(product.image),
                    contentDescription = "insta",
                    modifier = Modifier
                        .size(200.dp)
                        .fillMaxSize()
                        .padding(16.dp)
                    ,
                    contentScale = ContentScale.Fit)
            }
            Text(text = product.title, fontSize = 18.sp,fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 16.dp , end = 16.dp))
            Spacer(Modifier.height(10.dp))
            Text(text = product.description, fontSize = 16.sp, modifier = Modifier.padding(start = 16.dp , end = 16.dp))
            Spacer(Modifier.height(10.dp))
            Text(text = "Price "+product.price+" QAR", fontSize = 16.sp, modifier = Modifier.padding(start = 16.dp , end = 16.dp))
            Spacer(Modifier.height(10.dp))
            Row() {
                Text(text = "Rate : ", fontSize = 16.sp, modifier = Modifier.padding(start = 16.dp))
                RatingBar2(rating = product.rate.toInt())
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row() {
            Button(onClick = {
                nController.navigate("AddReview/${product.id}")
            }, modifier = Modifier.padding(start = 16.dp)) {
                Text(text = "Add Review")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {
                nController.navigate("ShowReviews/${product.id}")
            }, modifier = Modifier.padding(end = 16.dp)) {
                Text(text = "Show reviews")
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@ExperimentalComposeUiApi
@Composable
fun RatingBar2(
    modifier: Modifier = Modifier,
    rating: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..rating) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_star_rate_24),
                contentDescription = "star",
                modifier = modifier
                    .width(40.dp)
                    .height(40.dp),
                tint = Color(0xFFFFD700)
            )
        }
    }
}