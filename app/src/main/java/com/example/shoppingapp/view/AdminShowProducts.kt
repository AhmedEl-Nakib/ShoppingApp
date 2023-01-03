package com.example.shoppingapp.view


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.R
import com.example.shoppingapp.Screens
import com.example.shoppingapp.room.AppDatabase
import com.example.shoppingapp.entity.Product

@Composable
fun ProductCard(product: Product, navController: NavController, event: () -> Unit) {

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
                modifier = Modifier.weight(1f).clickable {
                },
                Arrangement.Center
            ) {
                Text(
                    text = "Title :" + product.title,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
                Text(
                    text = "Price :- " + product.price,
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
                    text = "Category :- " + product.category,
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

                Spacer(modifier = Modifier.height(10.dp))

                Image(painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp, 30.dp)
                        .clickable {
                            db
                                .ProductDao()
                                .deleteProductByID(product.id.toString())
                            event()

                        })

                Image(painter = painterResource(id = R.drawable.ic_baseline_mode_edit_outline_24),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp, 30.dp)
                        .clickable {
                            navController.navigate(Screens.ADMIN_UPDATE_PRODUCTS+"/${product.id}")
                        })
            }
            Image(painter = rememberAsyncImagePainter(product.image),
                "",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .padding(8.dp)
                    .size(110.dp)
                    .clip((CircleShape)))
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun AdminShowProducts(navController: NavController) {

    val db = Room.databaseBuilder(LocalContext.current, AppDatabase::class.java, "usersDB")
        .allowMainThreadQueries().build()

    var context = LocalContext.current


    Scaffold(
//        bottomBar = { BottomNavigationBar(navController) },
        content = {
            Column(modifier = Modifier.padding(10.dp)) {

                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(
                        db.ProductDao().getAllProducts()
                    ) {
                        ProductCard(product = it,navController) {
                            navController.navigate(Screens.ADMIN_SHOW_PRODUCTS) {
                                popUpTo(Screens.ADMIN_HOME)
                            }
                        }
                    }
                }
            }

        })

}