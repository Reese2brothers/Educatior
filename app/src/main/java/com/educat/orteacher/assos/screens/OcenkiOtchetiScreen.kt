package com.educat.orteacher.assos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import com.educat.orteacher.assos.R
import com.educat.orteacher.assos.database.AppDatabase
import com.educat.orteacher.assos.database.LanguagePoints
import com.educat.orteacher.assos.database.LiteraturePoints
import com.educat.orteacher.assos.database.MathematicsPoints
import kotlinx.coroutines.launch

@Composable
fun OcenkiOtchetiScreen(navController: NavController){
        var expanded by remember { mutableStateOf(false) }
        var selectedItem by remember { mutableStateOf("Mathematics") }
        val options = listOf("Mathematics", "Language", "Literature")

        Column(modifier = Modifier.fillMaxSize().systemBarsPadding().background(brush = Brush.verticalGradient
            (listOf(colorResource(R.color.gradup), colorResource(R.color.graddown))))) {
            Row(modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 8.dp)
                .background(colorResource(R.color.graddown), shape = RoundedCornerShape(24.dp)).clickable { expanded = true },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1f).background(colorResource(R.color.graddown), shape = RoundedCornerShape(24.dp)).padding(8.dp)) {
                    Text(text = selectedItem, fontSize = 20.sp, fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
                    )
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false },
                        modifier = Modifier.background(colorResource(R.color.graddown)).wrapContentWidth()) {
                        options.forEach { option ->
                            DropdownMenuItem(onClick = { selectedItem = option
                                expanded = false },
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                                    .background(colorResource(R.color.graddown)).fillMaxWidth()
                            ) {
                                Text(text = option, fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center, modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                                )
                            }
                        }
                    }
                }
                Image(painter = painterResource(R.drawable.iconcaret), contentDescription = "choise",
                    modifier = Modifier.size(35.dp).padding(end = 16.dp)
                )
            }
            Column(modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally) {
                when (selectedItem) {
                    "Mathematics" -> {
                        Column(modifier = Modifier.fillMaxWidth().weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            MathematicsPoints()
                        }
                    }
                    "Language" -> {
                        Column(modifier = Modifier.fillMaxWidth().weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            LanguagePoints()
                        }
                    }
                    "Literature" -> {
                        Column(modifier = Modifier.fillMaxWidth().weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            LiteraturePoints()
                        }
                    }
                }
            }
            Button(onClick = {  navController.navigate("ReportScreen") },
                modifier = Modifier.fillMaxWidth().padding(start = 32.dp, end = 32.dp, bottom = 24.dp, top = 8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.buttoncontinue)),
                shape = RoundedCornerShape(24.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 4.dp)){
                Text(text = "Generate a report",
                    color = colorResource(R.color.blacktext),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
}

@Composable
fun MathematicsPoints(){
    val context = LocalContext.current
    val db = remember { Room.databaseBuilder(context, AppDatabase::class.java, "database").build() }
    val mathematicsPointsList by db.mathematicsPointsDao().getAll().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    var showDialog = remember { mutableStateOf(false) }
    val field1 = remember { mutableStateOf("") }
    val field2 = remember { mutableStateOf("") }
    val field3 = remember { mutableStateOf("") }
    val field4 = remember { mutableStateOf("") }
    val field5 = remember { mutableStateOf("") }
    val field6 = remember { mutableStateOf("") }
    val field7 = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(R.drawable.baseline_delete), contentDescription = "delete",
                modifier = Modifier.size(35.dp).padding(end = 8.dp).clickable {
                    scope.launch {
                        db.mathematicsPointsDao().deleteAll()
                    }
                }
            )
            Image(painter = painterResource(R.drawable.add), contentDescription = "newdata",
                modifier = Modifier.size(35.dp).padding(end = 16.dp).clickable {
                    showDialog.value = true
                }
            )
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    containerColor = colorResource(id = R.color.graddown),
                    title = { Text(text = "Enter new data!", color = colorResource(id = R.color.continuetext),
                        fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = field1.value,
                                onValueChange = { field1.value = it },
                                placeholder = { Text("enter name...") }
                            )
                            OutlinedTextField(
                                value = field2.value,
                                onValueChange = { field2.value = it },
                                placeholder = { Text("enter rate for Mon...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field3.value,
                                onValueChange = { field3.value = it },
                                placeholder = { Text("enter rate for Tue...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field4.value,
                                onValueChange = { field4.value = it },
                                placeholder = { Text("enter rate for Wen...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field5.value,
                                onValueChange = { field5.value = it },
                                placeholder = { Text("enter rate for Thu...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field6.value,
                                onValueChange = { field6.value = it },
                                placeholder = { Text("enter rate for Fri...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field7.value,
                                onValueChange = { field7.value = it },
                                placeholder = { Text("enter rate for Sat...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    },
                    confirmButton = {
                        androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.continuetext)),
                            onClick = {
                                scope.launch {
                                    val rates = MathematicsPoints(name = field1.value, mon = field2.value, tue = field3.value,
                                        wen = field4.value, thu = field5.value, fri = field6.value, sat = field7.value)
                                    db.mathematicsPointsDao().insert(rates)
                                }
                                showDialog.value = false
                            }) { Text("Save", color = colorResource(id = R.color.white), fontSize = 16.sp)
                        }
                    },
                    dismissButton = { androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.continuetext)
                    ),
                        onClick = { showDialog.value = false }) {
                        Text("Cancel", color = colorResource(id = R.color.white), fontSize = 16.sp)
                    }
                    })
            }
        }
        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxHeight().padding(start = 140.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 183.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 222.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 268.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 306.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 343.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 388.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item{
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Students",
                            color = colorResource(R.color.blacktext),
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.interv)),
                            modifier = Modifier.padding(start = 16.dp))
                        Row( modifier = Modifier.weight(1f).padding(start = 24.dp), horizontalArrangement = Arrangement.SpaceBetween ) {
                            listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                                Row( modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center ) {
                                    Text( text = day,
                                        color = colorResource(R.color.blacktext),
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily(Font(R.font.interv)),
                                        modifier = Modifier.padding(horizontal = 4.dp) )
                                    Column(modifier = Modifier.height(28.dp).background(colorResource(R.color.black))
                                        .width(1.dp).padding(start = 4.dp) ){}
                                }
                            }
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp).background(colorResource(R.color.black)).height(1.dp)) {  }
                }
                itemsIndexed(mathematicsPointsList){ index, item ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(text = item.name,
                                color = colorResource(R.color.blacktext),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.interv)),
                                modifier = Modifier.width(140.dp)
                            )
                            Row(modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                listOf(item.mon, item.tue, item.wen, item.thu, item.fri, item.sat).forEach { day ->
                                    Row( modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center ) {
                                        Text( text = day,
                                            color = colorResource(R.color.blacktext),
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily(Font(R.font.interv)),
                                            modifier = Modifier.padding(horizontal = 4.dp) )
                                    }
                                }
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp).background(colorResource(R.color.black)).height(1.dp)) {}
                    }
                }
            }
        }
    }


}

@Composable
fun LanguagePoints(){
    val context = LocalContext.current
    val db = remember { Room.databaseBuilder(context, AppDatabase::class.java, "database").build() }
    val languagePointsList by db.languagePointsDao().getAll().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    var showDialog = remember { mutableStateOf(false) }
    val field1 = remember { mutableStateOf("") }
    val field2 = remember { mutableStateOf("") }
    val field3 = remember { mutableStateOf("") }
    val field4 = remember { mutableStateOf("") }
    val field5 = remember { mutableStateOf("") }
    val field6 = remember { mutableStateOf("") }
    val field7 = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(R.drawable.baseline_delete), contentDescription = "delete",
                modifier = Modifier.size(35.dp).padding(end = 8.dp).clickable {
                    scope.launch {
                        db.languagePointsDao().deleteAll()
                    }
                }
            )
            Image(painter = painterResource(R.drawable.add), contentDescription = "newdata",
                modifier = Modifier.size(35.dp).padding(end = 16.dp).clickable {
                    showDialog.value = true
                }
            )
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    containerColor = colorResource(id = R.color.graddown),
                    title = { Text(text = "Enter new data!", color = colorResource(id = R.color.continuetext),
                        fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = field1.value,
                                onValueChange = { field1.value = it },
                                placeholder = { Text("enter name...") }
                            )
                            OutlinedTextField(
                                value = field2.value,
                                onValueChange = { field2.value = it },
                                placeholder = { Text("enter rate for Mon...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field3.value,
                                onValueChange = { field3.value = it },
                                placeholder = { Text("enter rate for Tue...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field4.value,
                                onValueChange = { field4.value = it },
                                placeholder = { Text("enter rate for Wen...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field5.value,
                                onValueChange = { field5.value = it },
                                placeholder = { Text("enter rate for Thu...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field6.value,
                                onValueChange = { field6.value = it },
                                placeholder = { Text("enter rate for Fri...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field7.value,
                                onValueChange = { field7.value = it },
                                placeholder = { Text("enter rate for Sat...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    },
                    confirmButton = {
                        androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.continuetext)),
                            onClick = {
                                scope.launch {
                                    val rates = LanguagePoints(name = field1.value, mon = field2.value, tue = field3.value,
                                        wen = field4.value, thu = field5.value, fri = field6.value, sat = field7.value)
                                    db.languagePointsDao().insert(rates)
                                }
                                showDialog.value = false
                            }) { Text("Save", color = colorResource(id = R.color.white), fontSize = 16.sp)
                        }
                    },
                    dismissButton = { androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.continuetext)
                    ),
                        onClick = { showDialog.value = false }) {
                        Text("Cancel", color = colorResource(id = R.color.white), fontSize = 16.sp)
                    }
                    })
            }
        }
        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxHeight().padding(start = 140.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 183.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 222.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 268.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 306.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 343.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 388.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item{
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Students",
                            color = colorResource(R.color.blacktext),
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.interv)),
                            modifier = Modifier.padding(start = 16.dp))
                        Row( modifier = Modifier.weight(1f).padding(start = 24.dp), horizontalArrangement = Arrangement.SpaceBetween ) {
                            listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                                Row( modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center ) {
                                    Text( text = day,
                                        color = colorResource(R.color.blacktext),
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily(Font(R.font.interv)),
                                        modifier = Modifier.padding(horizontal = 4.dp) )
                                    Column(modifier = Modifier.height(28.dp).background(colorResource(R.color.black))
                                        .width(1.dp).padding(start = 4.dp) ){}
                                }
                            }
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp).background(colorResource(R.color.black)).height(1.dp)) {  }
                }
                itemsIndexed(languagePointsList){ index, item ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(text = item.name,
                                color = colorResource(R.color.blacktext),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.interv)),
                                modifier = Modifier.width(140.dp)
                            )
                            Row(modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                listOf(item.mon, item.tue, item.wen, item.thu, item.fri, item.sat).forEach { day ->
                                    Row( modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center ) {
                                        Text( text = day,
                                            color = colorResource(R.color.blacktext),
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily(Font(R.font.interv)),
                                            modifier = Modifier.padding(horizontal = 4.dp) )
                                    }
                                }
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp).background(colorResource(R.color.black)).height(1.dp)) {}
                    }
                }
            }
        }
    }


}

@Composable
fun LiteraturePoints(){
    val context = LocalContext.current
    val db = remember { Room.databaseBuilder(context, AppDatabase::class.java, "database").build() }
    val literaturePointsList by db.literaturePointsDao().getAll().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    var showDialog = remember { mutableStateOf(false) }
    val field1 = remember { mutableStateOf("") }
    val field2 = remember { mutableStateOf("") }
    val field3 = remember { mutableStateOf("") }
    val field4 = remember { mutableStateOf("") }
    val field5 = remember { mutableStateOf("") }
    val field6 = remember { mutableStateOf("") }
    val field7 = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(R.drawable.baseline_delete), contentDescription = "delete",
                modifier = Modifier.size(35.dp).padding(end = 8.dp).clickable {
                    scope.launch {
                        db.literaturePointsDao().deleteAll()
                    }
                }
            )
            Image(painter = painterResource(R.drawable.add), contentDescription = "newdata",
                modifier = Modifier.size(35.dp).padding(end = 16.dp).clickable {
                    showDialog.value = true
                }
            )
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    containerColor = colorResource(id = R.color.graddown),
                    title = { Text(text = "Enter new data!", color = colorResource(id = R.color.continuetext),
                        fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = field1.value,
                                onValueChange = { field1.value = it },
                                placeholder = { Text("enter name...") }
                            )
                            OutlinedTextField(
                                value = field2.value,
                                onValueChange = { field2.value = it },
                                placeholder = { Text("enter rate for Mon...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field3.value,
                                onValueChange = { field3.value = it },
                                placeholder = { Text("enter rate for Tue...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field4.value,
                                onValueChange = { field4.value = it },
                                placeholder = { Text("enter rate for Wen...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field5.value,
                                onValueChange = { field5.value = it },
                                placeholder = { Text("enter rate for Thu...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field6.value,
                                onValueChange = { field6.value = it },
                                placeholder = { Text("enter rate for Fri...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field7.value,
                                onValueChange = { field7.value = it },
                                placeholder = { Text("enter rate for Sat...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    },
                    confirmButton = {
                        androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.continuetext)),
                            onClick = {
                                scope.launch {
                                    val rates = LiteraturePoints(name = field1.value, mon = field2.value, tue = field3.value,
                                        wen = field4.value, thu = field5.value, fri = field6.value, sat = field7.value)
                                    db.literaturePointsDao().insert(rates)
                                }
                                showDialog.value = false
                            }) { Text("Save", color = colorResource(id = R.color.white), fontSize = 16.sp)
                        }
                    },
                    dismissButton = { androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.continuetext)
                    ),
                        onClick = { showDialog.value = false }) {
                        Text("Cancel", color = colorResource(id = R.color.white), fontSize = 16.sp)
                    }
                    })
            }
        }
        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxHeight().padding(start = 140.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 183.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 222.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 268.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 306.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 343.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(start = 388.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item{
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Students",
                            color = colorResource(R.color.blacktext),
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.interv)),
                            modifier = Modifier.padding(start = 16.dp))
                        Row( modifier = Modifier.weight(1f).padding(start = 24.dp), horizontalArrangement = Arrangement.SpaceBetween ) {
                            listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                                Row( modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center ) {
                                    Text( text = day,
                                        color = colorResource(R.color.blacktext),
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily(Font(R.font.interv)),
                                        modifier = Modifier.padding(horizontal = 4.dp) )
                                    Column(modifier = Modifier.height(28.dp).background(colorResource(R.color.black))
                                        .width(1.dp).padding(start = 4.dp) ){}
                                }
                            }
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp).background(colorResource(R.color.black)).height(1.dp)) {  }
                }
                itemsIndexed(literaturePointsList){ index, item ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(text = item.name,
                                color = colorResource(R.color.blacktext),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.interv)),
                                modifier = Modifier.width(140.dp)
                            )
                            Row(modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                listOf(item.mon, item.tue, item.wen, item.thu, item.fri, item.sat).forEach { day ->
                                    Row( modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center ) {
                                        Text( text = day,
                                            color = colorResource(R.color.blacktext),
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily(Font(R.font.interv)),
                                            modifier = Modifier.padding(horizontal = 4.dp) )
                                    }
                                }
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp).background(colorResource(R.color.black)).height(1.dp)) {}
                    }
                }
            }
        }
    }
}
