package com.example.shoppingapp.view

import com.example.shoppingapp.R
import android.view.MotionEvent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.ui.theme.JetpackComposeRatingBarTheme

@Composable
fun Review() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Reviews", fontSize = 20.sp)
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pd),
                    contentDescription = "insta",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(
                            width = 0.5.dp,
                            color = Color.Black,
                            shape = CircleShape
                        )
                        .padding(end = 5.dp),
                    contentScale = ContentScale.Crop
                )
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
        ) {
            Card(
                elevation = 6.dp, modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp, start = 16.dp, end = 16.dp, bottom = 50.dp)
                    .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(30.dp))
            ) {
                Row() {
                    Card(
                        elevation = 6.dp, modifier = Modifier
                            .padding(top = 20.dp, start = 10.dp)
                            .width(340.dp)
                            .height(200.dp)
                            .border(
                                width = 2.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(15.dp)
                            )
                    ) {
                        Row(modifier = Modifier
                            .padding(top = 20.dp, start = 10.dp)) {
                            Column(modifier = Modifier
                                .padding(top = 20.dp, start = 10.dp)) {


                                Text(
                                    text = "Name",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(modifier = Modifier
                                .padding(top = 20.dp, start = 0.dp)) {
                                JetpackComposeRatingBarTheme {
                                    Surface(color = Color.White) {
//                                        RatingBar2(rating = 3)
                                    }
                                }
                            }
                            Column(modifier = Modifier
                                .padding(top = 20.dp, start = 0.dp)) {
                                Text(text = "5/9/2022")
                            }
                        }
                        Row(modifier = Modifier
                            .padding(top = 70.dp, start = 10.dp)) {
                            Text(text = "Color: Red", fontWeight = FontWeight.Bold)
                        }
                        Row(modifier = Modifier.padding(top = 100.dp, start = 10.dp)) {
                            Text(text = "Size : M", fontSize = 15.sp)
                        }

                        Row(modifier = Modifier.padding(top = 140.dp, start = 10.dp)) {
                            Text(
                                text = "“Such a beautiful nice \n" +
                                        "dress!”", fontSize = 15.sp
                            )
                        }
                    }
                }

                Row(modifier = Modifier
                    .padding(top = 240.dp, start = 10.dp)) {
                    Card(
                        elevation = 6.dp, modifier = Modifier
                            .padding(top = 10.dp, start = 0.dp)
                            .width(340.dp)
                            .height(200.dp)
                            .border(
                                width = 2.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(15.dp)
                            )
                    ) {
                        Row(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                            Column(modifier = Modifier.padding(start = 10.dp)) {


                                Text(
                                    text = "Name",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(modifier = Modifier.padding(start = 5.dp)) {
                                JetpackComposeRatingBarTheme {
                                    Surface(color = Color.White) {
                                        //RatingBar2(rating = 2)
                                    }
                                }
                            }
                            Column(modifier = Modifier.padding(start = 5.dp)) {
                                Text(text = "5/9/2022")
                            }
                        }
                        Row(modifier = Modifier.padding(top = 40.dp, start = 10.dp)) {
                            Text(text = "Color: Red", fontWeight = FontWeight.Bold)
                        }
                        Row(modifier = Modifier.padding(top = 80.dp, start = 10.dp)) {
                            Text(text = "Size : XS", fontSize = 15.sp)
                        }

                        Row(modifier = Modifier.padding(top = 100.dp, start = 10.dp)) {
                            Text(
                                text = "I like the color but the \n" +
                                        "material is very cheap", fontSize = 15.sp
                            )
                        }
                    }
                }
                Row(modifier = Modifier
                    .padding(top = 500.dp, start = 70.dp)) {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .width(200.dp)
                            .height(50.dp)
                            .border(
                                width = 2.dp,
                                shape = RoundedCornerShape(20.dp),
                                color = Color.Black
                            )
                            .background(color = Color.White)
                    ) {

                        Text(text = "Add Review", fontWeight = FontWeight.Bold)
                    }
                }

            }


        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 20.dp, end = 20.dp)
        )
        {

            Card(
                elevation = 6.dp, modifier = Modifier
                    .padding(start = 10.dp, end = 20.dp)
                    .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(15.dp))
            ) {

                Column(modifier = Modifier.padding(start = 15.dp)) {
                    /*Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_home_24),
                        contentDescription = "star",
                        modifier = Modifier
                            .width(64.dp)
                            .height(64.dp),
                        tint = Color.Black
                    )*/
                }

                Column(modifier = Modifier.padding(start = 75.dp)) {
                    /*Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_add_shopping_cart_24),
                        contentDescription = "star",
                        modifier = Modifier
                            .width(64.dp)
                            .height(64.dp),
                        tint = Color.Black
                    )*/
                }
                Column(modifier = Modifier.padding(start = 135.dp)) {
                    /*Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_apps_24),
                        contentDescription = "star",
                        modifier = Modifier
                            .width(64.dp)
                            .height(64.dp),
                        tint = Color.Black
                    )*/
                }
                Column(modifier = Modifier.padding(start = 195.dp)) {
                   /*Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_favorite_24),
                        contentDescription = "star",
                        modifier = Modifier
                            .width(64.dp)
                            .height(64.dp),
                        tint = Color.Black
                    )*/
                }
                Column(modifier = Modifier.padding(start = 255.dp)) {
                    /*Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_account_circle_24),
                        contentDescription = "star",
                        modifier = Modifier
                            .width(64.dp)
                            .height(64.dp),
                        tint = Color.Black
                    )*/
                }

            }
        }


    }
}



@ExperimentalComposeUiApi
@Composable
@Preview(showBackground = true)
fun Reviewprev()
{
    Review()
}