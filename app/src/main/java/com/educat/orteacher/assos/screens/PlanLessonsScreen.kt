package com.educat.orteacher.assos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.room.Room
import com.educat.orteacher.assos.R
import com.educat.orteacher.assos.database.AppDatabase
import com.educat.orteacher.assos.database.LanguageItem
import com.educat.orteacher.assos.database.LiteratureItem
import com.educat.orteacher.assos.database.MathematicsItem
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PlanLessonsScreen(){
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Mathematics") }
    val options = listOf("Mathematics", "Language", "Literature")

    Column(modifier = Modifier.fillMaxSize().systemBarsPadding().background(brush = Brush.verticalGradient
        (listOf(colorResource(R.color.gradup), colorResource(R.color.graddown))))) {
        Row(modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp)
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
                    MathematicsItem()
                }
                "Language" -> {
                    LanguageItem()
                }
                "Literature" -> {
                    LiteratureItem()
                }
            }
        }
    }
}
@Composable
fun MathematicsItem(){
    val dayList = listOf( "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
        "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" )
    var selectedDay by remember { mutableStateOf<String?>(null) }
    var key by remember { mutableStateOf("") }
    val context = LocalContext.current
    val db = remember { Room.databaseBuilder(context, AppDatabase::class.java, "database").build() }
    val mathematicsItem by db.mathematicsItemDao().getByKey(key).collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    var showDialog = remember { mutableStateOf(false) }
    val field1 = remember { mutableStateOf("") }
    val field2 = remember { mutableStateOf("") }
    val field3 = remember { mutableStateOf("") }
    val field4 = remember { mutableStateOf("") }
    val field5 = remember { mutableStateOf("") }
Column(modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally) {
    LazyRow(modifier = Modifier.fillMaxWidth().height(100.dp)) {
        itemsIndexed(dayList) { index, day ->
            val isSelected = day == selectedDay
            Box( modifier = Modifier .wrapContentSize().clickable {
                key = (index+1).toString()
                selectedDay = if (isSelected) null else day },
                contentAlignment = Alignment.Center ) {
                val imageResource = if (isSelected) R.drawable.ellipsedark else R.drawable.ellipselight
                val textColor = if (isSelected) colorResource(R.color.graddown) else colorResource(R.color.continuetext)
                Image( painter = painterResource(imageResource), contentDescription = "day",
                    modifier = Modifier .height(100.dp).width(70.dp).padding(4.dp) )
                Text(text = day,
                    color = textColor,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.interv)),
                    modifier = Modifier.padding(bottom = 16.dp)) }
        }
    }
    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp).background(colorResource(R.color.black)).height(1.dp)) {}
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(R.drawable.baseline_delete), contentDescription = "delete",
                modifier = Modifier.size(40.dp).padding(top = 8.dp, end = 8.dp).clickable {
                    scope.launch{
                        db.mathematicsItemDao().deleteByKey(key)
                    }
                })
            Image(painter = painterResource(R.drawable.add), contentDescription = "adddayplan",
                modifier = Modifier.size(40.dp).padding(top = 8.dp, end = 16.dp).clickable {
                    showDialog.value = true
                })
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
                                placeholder = { Text("enter topic of the lesson...") }
                            )
                            OutlinedTextField(
                                value = field2.value,
                                onValueChange = { field2.value = it },
                                placeholder = { Text("enter homework...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field3.value,
                                onValueChange = { field3.value = it },
                                placeholder = { Text("enter class...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field4.value,
                                onValueChange = { field4.value = it },
                                placeholder = { Text("enter class room...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field5.value,
                                onValueChange = { field5.value = it },
                                placeholder = { Text("enter comment...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    },
                    confirmButton = {
                        androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.continuetext)),
                            onClick = {
                                scope.launch {
                                    val dayPlan = MathematicsItem(topic = field1.value, homework = field2.value, classnumber = field3.value,
                                        classroom = field4.value, comment = field5.value, numberkey = key)
                                    db.mathematicsItemDao().insert(dayPlan)
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
        Text(text = "Topic of the lesson",
            color = colorResource(R.color.blacktext),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.interv)),
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        mathematicsItem?.let {
            Text(text = it.topic,
                color = colorResource(R.color.blacktext),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.interv)),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )
        }
        Text(text = "Homework",
            color = colorResource(R.color.blacktext),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.interv)),
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
        mathematicsItem?.let {
            Text(text = it.homework,
                color = colorResource(R.color.blacktext),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.interv)),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )
        }
        Text(text = "Class",
            color = colorResource(R.color.blacktext),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.interv)),
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
        mathematicsItem?.let {
            Text(text = it.classnumber,
                color = colorResource(R.color.blacktext),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.interv)),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )
        }
        Text(text = "Class room",
            color = colorResource(R.color.blacktext),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.interv)),
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
        mathematicsItem?.let {
            Text(text = it.classroom,
                color = colorResource(R.color.blacktext),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.interv)),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )
        }
        Text(text = "Comment",
            color = colorResource(R.color.blacktext),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.interv)),
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
        mathematicsItem?.let {
            Text(text = it.comment,
                color = colorResource(R.color.blacktext),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.interv)),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )
        }
    }
}
}

@Composable
fun LanguageItem(){
    val dayList = listOf( "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
        "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" )
    var selectedDay by remember { mutableStateOf<String?>(null) }
    var key by remember { mutableStateOf("") }
    val context = LocalContext.current
    val db = remember { Room.databaseBuilder(context, AppDatabase::class.java, "database").build() }
    val languageItem by db.languageItemDao().getByKey(key).collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    var showDialog = remember { mutableStateOf(false) }
    val field1 = remember { mutableStateOf("") }
    val field2 = remember { mutableStateOf("") }
    val field3 = remember { mutableStateOf("") }
    val field4 = remember { mutableStateOf("") }
    val field5 = remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        LazyRow(modifier = Modifier.fillMaxWidth().height(100.dp)) {
            itemsIndexed(dayList) { index, day ->
                val isSelected = day == selectedDay
                Box( modifier = Modifier .wrapContentSize().clickable {
                    key = (index+1).toString()
                    selectedDay = if (isSelected) null else day },
                    contentAlignment = Alignment.Center ) {
                    val imageResource = if (isSelected) R.drawable.ellipsedark else R.drawable.ellipselight
                    val textColor = if (isSelected) colorResource(R.color.graddown) else colorResource(R.color.continuetext)
                    Image( painter = painterResource(imageResource), contentDescription = "day",
                        modifier = Modifier .height(100.dp).width(70.dp).padding(4.dp) )
                    Text(text = day,
                        color = textColor,
                        fontSize = 32.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.interv)),
                        modifier = Modifier.padding(bottom = 16.dp)) }
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp).background(colorResource(R.color.black)).height(1.dp)) {}
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(R.drawable.baseline_delete), contentDescription = "delete",
                    modifier = Modifier.size(40.dp).padding(top = 8.dp, end = 8.dp).clickable {
                        scope.launch{
                            db.languageItemDao().deleteByKey(key)
                        }
                    })
                Image(painter = painterResource(R.drawable.add), contentDescription = "adddayplan",
                    modifier = Modifier.size(40.dp).padding(top = 8.dp, end = 16.dp).clickable {
                        showDialog.value = true
                    })
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
                                    placeholder = { Text("enter topic of the lesson...") }
                                )
                                OutlinedTextField(
                                    value = field2.value,
                                    onValueChange = { field2.value = it },
                                    placeholder = { Text("enter homework...") },
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                OutlinedTextField(
                                    value = field3.value,
                                    onValueChange = { field3.value = it },
                                    placeholder = { Text("enter class...") },
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                OutlinedTextField(
                                    value = field4.value,
                                    onValueChange = { field4.value = it },
                                    placeholder = { Text("enter class room...") },
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                OutlinedTextField(
                                    value = field5.value,
                                    onValueChange = { field5.value = it },
                                    placeholder = { Text("enter comment...") },
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        },
                        confirmButton = {
                            androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.continuetext)),
                                onClick = {
                                    scope.launch {
                                        val dayPlan = LanguageItem(topic = field1.value, homework = field2.value, classnumber = field3.value,
                                            classroom = field4.value, comment = field5.value, numberkey = key)
                                        db.languageItemDao().insert(dayPlan)
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
            Text(text = "Topic of the lesson",
                color = colorResource(R.color.blacktext),
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.interv)),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )
            languageItem?.let {
                Text(text = it.topic,
                    color = colorResource(R.color.blacktext),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.interv)),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )
            }
            Text(text = "Homework",
                color = colorResource(R.color.blacktext),
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.interv)),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            )
            languageItem?.let {
                Text(text = it.homework,
                    color = colorResource(R.color.blacktext),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.interv)),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )
            }
            Text(text = "Class",
                color = colorResource(R.color.blacktext),
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.interv)),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            )
            languageItem?.let {
                Text(text = it.classnumber,
                    color = colorResource(R.color.blacktext),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.interv)),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )
            }
            Text(text = "Class room",
                color = colorResource(R.color.blacktext),
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.interv)),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            )
            languageItem?.let {
                Text(text = it.classroom,
                    color = colorResource(R.color.blacktext),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.interv)),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )
            }
            Text(text = "Comment",
                color = colorResource(R.color.blacktext),
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.interv)),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            )
            languageItem?.let {
                Text(text = it.comment,
                    color = colorResource(R.color.blacktext),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.interv)),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )
            }
        }
    }
}

@Composable
fun LiteratureItem(){
    val dayList = listOf( "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
        "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" )
    var selectedDay by remember { mutableStateOf<String?>(null) }
    var key by remember { mutableStateOf("") }
    val context = LocalContext.current
    val db = remember { Room.databaseBuilder(context, AppDatabase::class.java, "database").build() }
    val literatureItem by db.literatureItemDao().getByKey(key).collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    var showDialog = remember { mutableStateOf(false) }
    val field1 = remember { mutableStateOf("") }
    val field2 = remember { mutableStateOf("") }
    val field3 = remember { mutableStateOf("") }
    val field4 = remember { mutableStateOf("") }
    val field5 = remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        LazyRow(modifier = Modifier.fillMaxWidth().height(100.dp)) {
            itemsIndexed(dayList) { index, day ->
                val isSelected = day == selectedDay
                Box( modifier = Modifier .wrapContentSize().clickable {
                    key = (index+1).toString()
                    selectedDay = if (isSelected) null else day },
                    contentAlignment = Alignment.Center ) {
                    val imageResource = if (isSelected) R.drawable.ellipsedark else R.drawable.ellipselight
                    val textColor = if (isSelected) colorResource(R.color.graddown) else colorResource(R.color.continuetext)
                    Image( painter = painterResource(imageResource), contentDescription = "day",
                        modifier = Modifier .height(100.dp).width(70.dp).padding(4.dp) )
                    Text(text = day,
                        color = textColor,
                        fontSize = 32.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.interv)),
                        modifier = Modifier.padding(bottom = 16.dp)) }
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp).background(colorResource(R.color.black)).height(1.dp)) {}
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(R.drawable.baseline_delete), contentDescription = "delete",
                    modifier = Modifier.size(40.dp).padding(top = 8.dp, end = 8.dp).clickable {
                        scope.launch{
                            db.literatureItemDao().deleteByKey(key)
                        }
                    })
                Image(painter = painterResource(R.drawable.add), contentDescription = "adddayplan",
                    modifier = Modifier.size(40.dp).padding(top = 8.dp, end = 16.dp).clickable {
                        showDialog.value = true
                    })
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
                                    placeholder = { Text("enter topic of the lesson...") }
                                )
                                OutlinedTextField(
                                    value = field2.value,
                                    onValueChange = { field2.value = it },
                                    placeholder = { Text("enter homework...") },
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                OutlinedTextField(
                                    value = field3.value,
                                    onValueChange = { field3.value = it },
                                    placeholder = { Text("enter class...") },
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                OutlinedTextField(
                                    value = field4.value,
                                    onValueChange = { field4.value = it },
                                    placeholder = { Text("enter class room...") },
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                OutlinedTextField(
                                    value = field5.value,
                                    onValueChange = { field5.value = it },
                                    placeholder = { Text("enter comment...") },
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        },
                        confirmButton = {
                            androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.continuetext)),
                                onClick = {
                                    scope.launch {
                                        val dayPlan = LiteratureItem(topic = field1.value, homework = field2.value, classnumber = field3.value,
                                            classroom = field4.value, comment = field5.value, numberkey = key)
                                        db.literatureItemDao().insert(dayPlan)
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
            Text(text = "Topic of the lesson",
                color = colorResource(R.color.blacktext),
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.interv)),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )
            literatureItem?.let {
                Text(text = it.topic,
                    color = colorResource(R.color.blacktext),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.interv)),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )
            }
            Text(text = "Homework",
                color = colorResource(R.color.blacktext),
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.interv)),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            )
            literatureItem?.let {
                Text(text = it.homework,
                    color = colorResource(R.color.blacktext),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.interv)),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )
            }
            Text(text = "Class",
                color = colorResource(R.color.blacktext),
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.interv)),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            )
            literatureItem?.let {
                Text(text = it.classnumber,
                    color = colorResource(R.color.blacktext),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.interv)),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )
            }
            Text(text = "Class room",
                color = colorResource(R.color.blacktext),
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.interv)),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            )
            literatureItem?.let {
                Text(text = it.classroom,
                    color = colorResource(R.color.blacktext),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.interv)),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )
            }
            Text(text = "Comment",
                color = colorResource(R.color.blacktext),
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.interv)),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            )
            literatureItem?.let {
                Text(text = it.comment,
                    color = colorResource(R.color.blacktext),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.interv)),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )
            }
        }
    }
}