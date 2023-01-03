//package com.example.shoppingapp.view
//
//import com.example.shoppingapp.R
//import android.view.MotionEvent
//import android.widget.Toast
//import androidx.compose.animation.core.Spring
//import androidx.compose.animation.core.animateDpAsState
//import androidx.compose.animation.core.spring
//import androidx.compose.foundation.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.pointer.pointerInteropFilter
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.sp
//import androidx.room.Room
//import com.example.shoppingapp.entity.UserReview
//import com.example.shoppingapp.room.AppDatabase
//import com.example.shoppingapp.ui.theme.JetpackComposeRatingBarTheme
//import kotlinx.coroutines.launch
//
//@Composable
//fun AddReview()
//{
//    val corotinScp = rememberCoroutineScope()
//    val context = LocalContext.current
//    var usrName by remember {
//        mutableStateOf("")
//    }
//
//    var revDate by remember {
//        mutableStateOf("")
//    }
//    var prodColor by remember {
//        mutableStateOf("")
//    }
//    var prodSize by remember {
//        mutableStateOf("")
//    }
//
//    var usrComment by remember {
//        mutableStateOf("")
//    }
//
//    var usrrate by remember {
//        mutableStateOf("")
//    }
//
//
//    Column(modifier = Modifier
//        .fillMaxSize()
//        .padding(top = 20.dp)) {
//        Row(modifier = Modifier
//            .fillMaxWidth().verticalScroll(rememberScrollState())
//            .height(60.dp)) {
//            Column(modifier = Modifier
//                .fillMaxHeight()
//                .padding(top = 5.dp)
//                .width(300.dp), horizontalAlignment = Alignment.CenterHorizontally) {
//                Text(text = "Add a review", fontSize = 20.sp)
//            }
//            Column(modifier = Modifier
//                .fillMaxHeight()
//                .padding(end = 10.dp)) {
//                Image(
//                    painter = painterResource(id = R.drawable.pd2),
//                    contentDescription = "person",
//                    modifier = Modifier
//                        .size(50.dp)
//                        .clip(CircleShape)
//                        .border(
//                            width = 0.5.dp,
//                            color = Color.Black,
//                            shape = CircleShape
//                        )
//                        .padding(end = 5.dp),
//                    contentScale = ContentScale.Crop)
//            }
//
//        }
//        Row(modifier = Modifier
//            .fillMaxWidth()
//            .height(900.dp)) {
//          Card(elevation = 6.dp, modifier = Modifier
//              .fillMaxSize()
//              .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 50.dp)
//              .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(30.dp))
//             ) {
//              Row(modifier = Modifier
//                  .fillMaxWidth()
//                  .height(50.dp)
//                  .padding(top = 50.dp))
//              { Column( modifier = Modifier
//                  .fillMaxSize()
//                  .padding(start = 50.dp)) {
//                  Text(text = "Name (you can keep \n" +
//                          "it anonymous)", color = Color.Black)
//              }
//                 }
//              Row(modifier = Modifier
//                  .fillMaxWidth()
//                  .height(50.dp)
//                  .padding(top = 100.dp)) {
//
//                      Column(modifier = Modifier
//                          .fillMaxSize()
//                          .padding(start = 50.dp)) {
//                          TextField(
//                              value = usrName,
//                              onValueChange = {usrName = it },
//                              placeholder = { Text("Enter your name") } ,
//                              modifier = Modifier
//                                  .width(250.dp)
//                                  .height(50.dp)
//                                  .border(
//                                      width = 2.dp,
//                                      shape = RoundedCornerShape(20.dp),
//                                      color = Color.Black
//                                  )
//                                  .background(color = Color.White)
//                          )
//                      }
//                  }
//              Row(modifier = Modifier
//                  .fillMaxWidth()
//                  .height(50.dp)
//                  .padding(top = 170.dp))
//              {
//                   Column( modifier = Modifier
//                       .fillMaxSize()
//                       .padding(start = 50.dp)) {
//                       Text(text = "Date")
//                   }
//                  }
//              Row(modifier = Modifier
//                  .fillMaxWidth()
//                  .height(50.dp)
//                  .padding(top = 200.dp)) {
//                  Column(modifier = Modifier
//                      .fillMaxSize()
//                      .padding(start = 50.dp)) {
//
//                      TextField(
//                          value = revDate,
//                          onValueChange = {revDate = it },
//                          placeholder = { Text("Enter Date") },
//                          label = {
//                              Text("Date")
//                          },
//                          modifier = Modifier
//                              .width(250.dp)
//                              .height(50.dp)
//                              .border(
//                                  width = 2.dp,
//                                  shape = RoundedCornerShape(20.dp),
//                                  color = Color.Black
//                              )
//                              .background(color = Color.White)
//                      )
//                  }
//              }
//              Row(modifier = Modifier
//                  .fillMaxWidth()
//                  .height(50.dp)
//                  .padding(top = 270.dp))
//              {
//                  Column(modifier = Modifier
//                      .fillMaxSize()
//                      .padding(start = 50.dp)) {
//
//                      Text(text = "Describtion of the product")
//                  }
//              }
//              Row(modifier = Modifier
//                  .fillMaxWidth()
//                  .height(50.dp)
//                  .padding(top = 300.dp))
//              {
//                  Column( modifier = Modifier
//                      .padding(start = 30.dp, top = 20.dp)
//                      .width(50.dp)) {
//
//                      Text(text = "Color")}
//                  Column(modifier = Modifier
//                      .padding(start = 20.dp)
//                      .width(250.dp)) {
//                      TextField(
//                          value = prodColor, onValueChange = { prodColor  = it}, placeholder = { Text("Enter Color") }, label = {
//                              Text("Color")
//                          },
//                          modifier = Modifier
//                              .width(200.dp)
//                              .height(50.dp)
//                              .border(
//                                  width = 2.dp,
//                                  shape = RoundedCornerShape(20.dp),
//                                  color = Color.Black
//                              )
//                              .background(color = Color.White)
//                      )
//                  }
//              }
//              Row(modifier = Modifier
//                  .fillMaxWidth()
//                  .height(50.dp)
//                  .padding(top = 350.dp)) {
//                  Column(modifier = Modifier
//                      .padding(start = 30.dp, top = 20.dp)
//                      .width(50.dp)) {
//
//                  Text(text = "Size")}
//              Column(modifier = Modifier
//                  .padding(start = 20.dp)
//                  .width(250.dp)) {
//                  TextField(
//                      value = prodSize, onValueChange = {prodSize = it }, placeholder = { Text("Enter Size") }, label = {
//                          Text("Size")
//                      },
//                      modifier = Modifier
//                          .width(200.dp)
//                          .height(50.dp)
//                          .border(
//                              width = 2.dp,
//                              shape = RoundedCornerShape(20.dp),
//                              color = Color.Black
//                          )
//                          .background(color = Color.White)
//                  )
//              }
//              }
//              Row(modifier = Modifier
//                  .fillMaxWidth()
//                  .height(50.dp)
//                  .padding(top = 420.dp))
//              {
//                  Column(modifier = Modifier
//                      .fillMaxSize()
//                      .padding(start = 50.dp)) {
//
//                      Text(text = "Your brief description")
//                  }
//              }
//              Row(modifier = Modifier
//                  .fillMaxWidth()
//                  .height(50.dp)
//                  .padding(top = 450.dp)) {
//                  Column(modifier = Modifier
//                      .fillMaxSize()
//                      .padding(start = 50.dp)) {
//                      TextField(
//                          value = usrComment,
//                          onValueChange = { usrComment = it},
//                          placeholder = { Text("Enter Description") },
//                          modifier = Modifier
//                              .width(250.dp)
//                              .height(50.dp)
//                              .border(
//                                  width = 2.dp,
//                                  shape = RoundedCornerShape(20.dp),
//                                  color = Color.Black
//                              )
//                              .background(color = Color.White)
//                      )
//                  }
//              }
//              Row(modifier = Modifier
//                  .fillMaxWidth()
//                  .height(50.dp)
//                  .padding(top = 530.dp))
//              {Column(modifier = Modifier
//                  .fillMaxSize()
//                  .padding(start = 50.dp)) {
//
//                  Text(text = "Rate")
//              }}
//              Row(modifier = Modifier
//                  .fillMaxWidth()
//                  .height(50.dp)
//                  .padding(top = 560.dp))
//              {
//
//                  Card(elevation = 6.dp, modifier = Modifier
//                      .fillMaxSize()
//                      .padding(top = 5.dp, start = 16.dp, end = 16.dp, bottom = 60.dp)
//                      .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(30.dp))
//                  ) {
//                      JetpackComposeRatingBarTheme {
//                          Surface(color = Color.White) {
//                              usrrate = "3"
//                              RatingBar(rating = 3)
//                          }
//                  }
//
//              }}
//              Row(modifier = Modifier
//                  .fillMaxWidth()
//                  .height(50.dp)
//                  .padding(top = 650.dp,start=150.dp)) {
//                  Button(onClick = {
//                      corotinScp.launch {
////                          var usr = UserReview(1,usrName,usrrate.toInt(),prodColor,prodSize,1,1,usrComment,revDate)
////                          val db = Room.databaseBuilder(context, AppDatabase::class.java, "usersDB")
////                              .allowMainThreadQueries().build()
////                          db.getUserReviewDao().insert(usr)
////                          Toast.makeText(context, "Review Added Successfully", Toast.LENGTH_LONG).show()
//
//                      }
//
//                  }) {
//                      Text(text = "Submit", color = Color.Black)
//                  }
//              }
//          }
//        }
//
//    }
//}
//
//
//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun RatingBar(
//    modifier: Modifier = Modifier,
//    rating: Int
//) {
//    var ratingState by remember {
//        mutableStateOf(rating)
//    }
//
//    var selected by remember {
//        mutableStateOf(false)
//    }
//    val size by animateDpAsState(
//        targetValue = if (selected) 52.dp else 54.dp,
//        spring(Spring.DampingRatioMediumBouncy)
//    )
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(40.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
//    ) {
//        for (i in 1..5) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_baseline_star_rate_24),
//                contentDescription = "star",
//                modifier = modifier
//                    .width(size)
//                    .height(size)
//                    .pointerInteropFilter {
//                        when (it.action) {
//                            MotionEvent.ACTION_DOWN -> {
//                                selected = true
//                                ratingState = i
//                            }
//                            MotionEvent.ACTION_UP -> {
//                                selected = false
//                            }
//                        }
//                        true
//                    },
//                tint = if (i <= ratingState) Color(0xFFFFD700) else Color(0xFFA2ADB1)
//            )
//        }
//    }
//}
//
//
//@ExperimentalComposeUiApi
//@Composable
//@Preview(showBackground = true)
//fun AddReviewPrev()
//{
//    AddReview()
//}