package com.example.shoppingapp.view
//hanan

import android.content.Context
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.FirestoreModel.FireStoreCart
import com.example.shoppingapp.FirestoreModel.FireStoreWishlist
import com.example.shoppingapp.R
import com.example.shoppingapp.RemoteDB.RetrofitAPI
import com.example.shoppingapp.ShowDataScreen
import com.example.shoppingapp.bottomNavigationController.NavigationItem
import com.example.shoppingapp.entity.Product
import com.example.shoppingapp.room.AppDatabase
import com.example.shoppingapp.ui.theme.Purple700
import com.example.shoppingapp.view.review.AddReviewScreenPage
import com.example.shoppingapp.view.review.ShowReviewsScreen
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MainScreenPreview2(){
    MainScreen(navController = NavController(LocalContext.current),"")
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SearchSectionPreview2(){
    SearchSection("", onTextChange = {}, onCloseClicked = {}, onSearchClicked = {})
}
@Composable
fun MainScreen(navController: NavController, item:String?) {
    val nController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(nController,item) },
        content = { padding -> // We have to pass the scaffold inner padding to our content. That's why we use Box.

            NavHost(navController = nController, startDestination = "NavHomeScreen") {

                composable("NavHomeScreen") {
                    HomeScreen(nController,item)
                }
                 composable("NavCartScreen/{userID}") {
                     val userID  = it.arguments?.getString("userID")
                     ShowDataScreen(nController,userID)
                }
                composable("NavWishlistScreen/{userID}") {
                    val userID = it.arguments?.getString("userID")
                    WishlistPage(nController,userID)
                }
                composable("NavOrdersScreen/{userID}") {
                    val userID  = it.arguments?.getString("userID")
                    ShowOrdersScreen(nController,userID)
                }
                composable("ShowAllAddress/{userID}") {
                    val userID  = it.arguments?.getString("userID")
                    ShowAddressScreen(navController = nController,userID)
                }
                composable("AddNewAddress/{userID}") {
                    val userID  = it.arguments?.getString("userID")
                    AddNewAddressScreenPage(navController = nController,userID)
                }
                composable("Payment/{userID}") {
                    val userID  = it.arguments?.getString("userID")
                    pay(navController = nController,userID)
                }
                composable("ProductDetails/{productId}") {
                    val item  = it.arguments?.getString("productId")
                    ProductDetails(item,nController)
                }
                composable("AddReview/{productId}") {
                    val item  = it.arguments?.getString("productId")
                    AddReviewScreenPage(nController,item)
                }
                composable("ShowReviews/{productId}") {
                    val item  = it.arguments?.getString("productId")
                    ShowReviewsScreen(item)
                }
            }
        },
        backgroundColor = colorResource(R.color.white) // Set background color to avoid the white flashing when you switch between screens
    )
}

@Composable
fun SearchSection(
    text:String,
    onTextChange:(String)-> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
){
    Surface(
        modifier= Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp)),
        elevation= AppBarDefaults.TopAppBarElevation,
        color= MaterialTheme.colors.primary
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = { onTextChange(it)},
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color=Color.Black
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                color = Color.Black
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(modifier = Modifier
                    .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Black
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                cursorColor = Color.Black.copy(alpha = ContentAlpha.medium))
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController, userID: String?) {
    val db = Room.databaseBuilder(LocalContext.current, AppDatabase::class.java, "usersDB")
        .allowMainThreadQueries().build()

    val products = remember { db.ProductDao().getAllProducts() }
    var searchText = remember {
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxSize()){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SearchSection(
                text=searchText.value,
                onTextChange={it->
                    searchText.value = it
                },
                onCloseClicked={},
                onSearchClicked={},
            )
            val context = LocalContext.current

            var productsListApi = remember{
                mutableStateListOf<Product>()
            }
            if(db.ProductDao().getAllProducts().isEmpty()){
                getAPIData(productsListApi,context,db)
            }else{
                db.ProductDao().getAllProducts().forEach{
                    productsListApi.add(it)
                }
                //productsListApi = db.ProductDao().getAllProducts() as SnapshotStateList<Product>
            }

            LazyVerticalGrid( GridCells.Fixed(2),
                contentPadding =PaddingValues(start = 7.5.dp,end= 7.5.dp,
                    bottom = 100.dp),
                modifier = Modifier.fillMaxSize()){
                items(products.filter {
                    it.title.contains(searchText.value,ignoreCase = true)
                }
                ){
                    ProductItem(product = it,navController,userID)
                }
            }
//            LazyColumn(
//                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
//            ) {
//                items(
//                    products
//                ) {
//                    ProductCard(it)
//                }
//            }
        }

    }
}

fun getAPIData(productsListApi: MutableList<Product>, context: Context, db: AppDatabase) {

    //val db = Room.databaseBuilder(LocalContext.current, AppDatabase::class.java, "usersDB") .allowMainThreadQueries().build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://gist.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create()).build()

    val call : Call<ArrayList<Product>> = retrofit.create(RetrofitAPI::class.java).getProducts()
    call.enqueue(object : Callback<ArrayList<Product>>{
        override fun onResponse(
            call: Call<ArrayList<Product>>,
            response: Response<ArrayList<Product>>
        ) {
            val responseData = response.body()!!
            db.ProductDao().insertAll(responseData)
            responseData.forEach {
                productsListApi.add(it)
            }
        }

        override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
            Toast.makeText(context,t.message,Toast.LENGTH_SHORT).show()
        }

    })
}


//@Composable
//fun ProductCard(product: Product) {
//    val db = Room.databaseBuilder(LocalContext.current, AppDatabase::class.java, "usersDB")
//        .allowMainThreadQueries().build()
//    val context = LocalContext.current
//    Card(
//        modifier = Modifier
//            .padding(horizontal = 8.dp, vertical = 8.dp)
//            .fillMaxWidth(),
//        elevation = 2.dp,
//        backgroundColor = Color.White,
//
//    ) {
//        Row(modifier = Modifier.padding(20.dp)) {
//            Column(modifier = Modifier.weight(1f),
//                Arrangement.Center) {
//                Text(
//                    text = product.title,
//                    style = TextStyle(
//                        color = Color.Black,
//                        fontSize = 22.sp,
//                        fontWeight = FontWeight.Bold,
//                    )
//                )
//                Text(
//                    text = "Category :- "+product.category,
//                    style = TextStyle(
//                        color = Color.Black,
//                        fontSize = 15.sp
//                    )
//                )
//                Text(
//                    text = "Price :- "+product.price,
//                    style = TextStyle(
//                        color = Color.Blue,
//                        fontSize = 15.sp
//                    )
//                )
//                Spacer(modifier = Modifier.width(30.dp))
//                Row(){
//                    Image(painter = painterResource(id = R.drawable.ic_baseline_add_shopping_cart_24),
//                        contentDescription = "",
//                        modifier = Modifier
//                            .size(25.dp, 25.dp)
//                            .clickable {
//                                if (db
//                                        .CartDataAccessObject()
//                                        .getProductById(product.id.toString())
//                                        .isNotEmpty()
//                                ) {
//                                    Toast
//                                        .makeText(
//                                            context,
//                                            "Product already added",
//                                            Toast.LENGTH_SHORT
//                                        )
//                                        .show()
//                                } else {
//                                    db
//                                        .CartDataAccessObject()
//                                        .addProduct(
//                                            Cart(
//                                                product.title,
//                                                product.price,
//                                                product.description,
//                                                product.category,
//                                                product.image
//                                            )
//                                        )
//                                    Toast
//                                        .makeText(context, "Added to cart", Toast.LENGTH_SHORT)
//                                        .show()
//                                }
//                            })
//                    Spacer(modifier = Modifier.width(50.dp))
////                    Image(painter = painterResource(id = R.drawable.ic_baseline_favorite_24_red),
////                        contentDescription = "",
////                        modifier = Modifier
////                            .size(25.dp, 25.dp)
////                            .clickable {
////                            })
//                }
//            }
////            AsyncImage(
////                model = "https://example.com/image.jpg",
////                contentDescription = null // Avoid `null` and set this to a localized string if possible.
////            )
//            Image(painter =rememberAsyncImagePainter(product.image),
//                "",
//                contentScale = ContentScale.FillHeight,
//                modifier = Modifier
//                    .padding(8.dp)
//                    .size(110.dp)
//                    .clip((CircleShape)))
//            Image(painter = painterResource(id = R.drawable.ic_baseline_favorite_24_red),
//                contentDescription = "",
//                modifier = Modifier
//                    .size(25.dp, 25.dp)
//                    .clickable {
//                        if (db
//                                .WishlistDataAccessObject()
//                                .getWishlistProductById(product.id.toString())
//                                .isNotEmpty()
//                        ) {
//                            Toast
//                                .makeText(
//                                    context,
//                                    "Already added to wishlist",
//                                    Toast.LENGTH_SHORT
//                                )
//                                .show()
//                        } else {
//                            db
//                                .WishlistDataAccessObject()
//                                .addProductToWishlist(
//                                    Wishlist(
//                                        product.title,
//                                        product.price,
//                                        product.description,
//                                        product.category,
//                                        product.image
//                                    )
//                                )
//                            Toast
//                                .makeText(context, "Added to wishlist", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                    })
//        }
//    }
//}

@Composable
fun ProductItem(product: Product, navController: NavHostController, userID: String?){
    val db = Room.databaseBuilder(LocalContext.current, AppDatabase::class.java, "usersDB")
        .allowMainThreadQueries().build()
    val context = LocalContext.current

    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // AnimationEffect
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(3f).getInterpolation(it)
                })
        )
        delay(3000L)
    }

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(180.dp)
            .scale(scale.value)
            .clickable {
//                                      navController.navigate("ProductDetails/${product.id}")
                navController.navigate("ProductDetails/${product.id}")
            },
        elevation = 2.dp,
        backgroundColor = Color.White,

        ) {
        BoxWithConstraints(
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color.White)
                .height(180.dp)
        ) {
            val width = constraints.maxWidth
            val height = constraints.maxHeight
            //}
            /* Box(modifier = Modifier
             .fillMaxSize()
             .padding(15.dp)
             .clickable {
             }
         ){*/
            Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxSize()) {
                Image(
                    painter =  rememberAsyncImagePainter(product.image),
                    contentDescription = product.title,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .size(100.dp),
                )

                Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.subtitle1,
                        lineHeight = 15.sp,
                        maxLines = 2,
                    )
                    var ProductPriceinString = product.price.toString(); //convert int -> string for Text
                    Text(
                        text = "Price: $ProductPriceinString QAR",
                        style = MaterialTheme.typography.body1,
                        lineHeight = 16.sp,
                    )
                }
            }
            Icon(imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite",
                tint = Color.Red,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clickable {
                        val db = Firebase.firestore

                        val mod =  FireStoreWishlist(
                            product.id,
                            product.title,
                            product.price,
                            product.description,
                            product.category,
                            product.image
                        )

                        db.collection("Favorite/users/${userID}").whereEqualTo("id",product.id).get().addOnSuccessListener {
                            if(it.isEmpty){
                                db.collection("Favorite/users/${userID}").document(product.id.toString()).set(mod).addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        Toast
                                            .makeText(context, "Added to wishlist", Toast.LENGTH_SHORT)
                                            .show()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Error ${it.exception!!.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }else{
                                Toast.makeText(
                                    context,
                                    "Already added to wishlist",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                            }
                        }
                        /*if (db
                                .WishlistDataAccessObject()
                                .getWishlistProductById(product.id.toString())
                                .isNotEmpty()
                        ) {
                            Toast
                                .makeText(
                                    context,
                                    "Already added to wishlist",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        } else {
                            db
                                .WishlistDataAccessObject()
                                .addProductToWishlist(
                                    Wishlist(
                                        product.id,
                                        product.title,
                                        product.price,
                                        product.description,
                                        product.category,
                                        product.image
                                    )
                                )
                            Toast
                                .makeText(context, "Added to wishlist", Toast.LENGTH_SHORT)
                                .show()
                        }*/
                    }
            )
            Icon(imageVector = Icons.Default.AddCircle,
                contentDescription = "Cart",
                tint = Purple700,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clickable {
                        val db = Firebase.firestore

                        val model =  FireStoreCart(
                            product.id,
                            product.title,
                            product.price,
                            product.description,
                            product.category,
                            product.image
                        )

                        db.collection("Cart/users/${userID}").whereEqualTo("id",product.id).get().addOnSuccessListener {
                            if(it.isEmpty){
                                db.collection("Cart/users/${userID}").document(product.id.toString()).set(model).addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        Toast
                                            .makeText(context, "Added to Cart", Toast.LENGTH_SHORT)
                                            .show()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Error ${it.exception!!.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }else{
                                Toast.makeText(
                                    context,
                                    "Already added to Cart",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }

                        /*
                        if (db
                                .CartDataAccessObject()
                                .getProductById(product.id.toString())
                                .isNotEmpty()
                        ) {
                            Toast
                                .makeText(
                                    context,
                                    "Product already added",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        } else {
                            db
                                .CartDataAccessObject()
                                .addProduct(
                                    Cart(
                                        product.id,
                                        product.title,
                                        product.price,
                                        product.description,
                                        product.category,
                                        product.image
                                    )
                                )
                            Toast
                                .makeText(context, "Added to cart", Toast.LENGTH_SHORT)
                                .show()
                        }*/
                    }
            )
        }
    }
}
@Composable
fun BottomNavigationBar(navController:NavController,userID:String?) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Cart,
        NavigationItem.Favorite,
        NavigationItem.Orders,
    )
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor =  colorResource(id = R.color.purple_700),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
            .border(border = BorderStroke(width = 1.dp, Color.LightGray))
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title) },
                selectedContentColor = colorResource(id = R.color.purple_700),
                unselectedContentColor = colorResource(id = R.color.purple_700).copy(0.7f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    if(item.route == "NavCartScreen" || item.route == "NavOrdersScreen" || item.route == "NavWishlistScreen"){
                        navController.navigate(item.route+"/${userID}"){
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
//                                saveState = true
                                }
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
//                        restoreState = true
                        }}
                    else{
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
//                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
//                        restoreState = true
                    }}
                }
            )
        }
    }
}
