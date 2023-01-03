package com.example.shoppingapp.view

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shoppingapp.Screens
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AdminReportPage(navController: NavController){
    MyContent(navController)
}

@Composable
fun MyContent(navController: NavController) {

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            if(mDayOfMonth<10){
                if(mMonth+1 < 10)
                   mDate.value = "0$mDayOfMonth-0${mMonth + 1}-$mYear"
                else
                    mDate.value = "0$mDayOfMonth-${mMonth + 1}-$mYear"
            }else{
                if(mMonth+1 < 10)
                    mDate.value = "$mDayOfMonth-0${mMonth + 1}-$mYear"
                else
                    mDate.value = "$mDayOfMonth-${mMonth + 1}-$mYear"
            }
        }, mYear, mMonth, mDay
    )

    val tDate = remember { mutableStateOf("") }

    var status = remember { mutableStateOf("All")}

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val tDatePickerDialog = DatePickerDialog(
        mContext, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            if(mDayOfMonth<10){
                if(mMonth+1 < 10)
                    tDate.value = "0$mDayOfMonth-0${mMonth + 1}-$mYear"
                else
                    tDate.value = "0$mDayOfMonth-${mMonth + 1}-$mYear"
            }else{
                if(mMonth+1 < 10)
                    tDate.value = "$mDayOfMonth-0${mMonth + 1}-$mYear"
                else
                    tDate.value = "$mDayOfMonth-${mMonth + 1}-$mYear"
            } }, mYear, mMonth, mDay
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(){
        Text(text = "From date: ")
        // Creating a button that on
        // click displays/shows the DatePickerDialog
            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = {
            mDatePickerDialog.show()
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58))) {
            Text(
                text = if (mDate.value == "") "Open Date Picker" else "Selected Date: ${mDate.value}",
                color = Color.White
            )

        }}
        Spacer(modifier = Modifier.height(10.dp))

        Row(){

            Text(text = "To date: ")
            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = {
            tDatePickerDialog.show()
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58))) {
            Text(
                text = if (tDate.value == "") "Open Date Picker" else "Selected Date: ${tDate.value}",
                color = Color.White
            )
    }}
        Spacer(modifier = Modifier.height(10.dp))

        val radioOptions = listOf("All","Processing", "Shipped", "Delivered")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0] ) }
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(16.dp))

        ) {
            Column() {
                radioOptions.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = {
                                    onOptionSelected(text)
                                    status.value=text
                                }
                            )
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                                status.value=text
                            }
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.body1.merge(),
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
//09-5-2022
            val formatter: DateFormat = SimpleDateFormat("dd-MM-yyyy")
            val from_date: Date = formatter.parse(mDate.value) as Date
            var fromDateTime = from_date.time

            val to_date: Date = formatter.parse(tDate.value) as Date
            var toDateTime = to_date.time

            navController.navigate(Screens.AdminReportDetails+"/${fromDateTime.toString()}/${toDateTime}/${status.value}")
        }) {
            Text(text = "Proceed")
        }

    }
}