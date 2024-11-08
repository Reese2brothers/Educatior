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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import com.educat.orteacher.assos.R
import com.educat.orteacher.assos.database.AppDatabase
import com.educat.orteacher.assos.database.Language
import com.educat.orteacher.assos.database.Literature
import com.educat.orteacher.assos.database.Mathematics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

@Composable
fun AssessmentsScreen(navController:NavController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Mathematics") }
    val options = listOf("Mathematics", "Language", "Literature")
    var showDialog = remember { mutableStateOf(false) }
    val field1 = remember { mutableStateOf("") }
    val field2 = remember { mutableStateOf("") }
    val field3 = remember { mutableStateOf("") }
    val db = remember { Room.databaseBuilder(context, AppDatabase::class.java, "database").build() }
    val mathematicsList: Flow<List<Mathematics>> = db.mathematicsDao().getAll()
    val languageList: Flow<List<Language>> = db.languageDao().getAll()
    val literatureList: Flow<List<Literature>> = db.literatureDao().getAll()

    Column(modifier = Modifier.fillMaxSize().systemBarsPadding().background(brush = Brush.verticalGradient(listOf(
        colorResource(R.color.gradup), colorResource(R.color.graddown))))){
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start) {
            Box(modifier = Modifier.size(60.dp).padding(top = 16.dp, start = 16.dp).clickable {
                navController.navigate("MainScreen/Students")
            },
                contentAlignment = Alignment.Center) {
                Image(painter = painterResource(R.drawable.solarbackmain), contentDescription = "backtostudentsdetails",
                    modifier = Modifier.size(60.dp)
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp)
            .background(colorResource(R.color.graddown), shape = RoundedCornerShape(24.dp)).clickable { expanded = true },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(R.drawable.add), contentDescription = "newdata",
                modifier = Modifier.size(35.dp).padding(start = 16.dp).clickable {
                    showDialog.value = true
                }
            )
            Image(painter = painterResource(R.drawable.baseline_delete), contentDescription = "newdata",
                modifier = Modifier.size(40.dp).padding(start = 16.dp).clickable {
                    when(selectedItem){
                        "Mathematics" -> { scope.launch { db.mathematicsDao().deleteAll() } }
                        "Language" -> { scope.launch { db.languageDao().deleteAll() } }
                        "Literature" -> { scope.launch { db.literatureDao().deleteAll() } }
                    }
                }
            )

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
                                placeholder = { Text("enter day...") }
                            )
                            OutlinedTextField(
                                value = field2.value,
                                onValueChange = { field2.value = it },
                                placeholder = { Text("enter type of work...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field3.value,
                                onValueChange = { field3.value = it },
                                placeholder = { Text("enter popints...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    },
                    confirmButton = {
                        Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.continuetext)),
                            onClick = {
                                scope.launch {
                                    when(selectedItem){
                                        "Mathematics" -> {
                                            val mathematics = Mathematics(day = field1.value, typeofwork = field2.value, points =  field3.value)
                                            db.mathematicsDao().insert(mathematics)
                                        }
                                        "Language" -> {
                                            val language = Language(day = field1.value, typeofwork = field2.value, points =  field3.value)
                                            db.languageDao().insert(language)
                                        }
                                        "Literature" -> {
                                            val literature = Literature(day = field1.value, typeofwork = field2.value, points =  field3.value)
                                            db.literatureDao().insert(literature)
                                        }
                                    }
                                }
                                showDialog.value = false
                            }) { Text("Save", color = colorResource(id = R.color.white), fontSize = 16.sp)
                        }
                    },
                    dismissButton = { Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.continuetext)
                    ),
                        onClick = { showDialog.value = false }) {
                        Text("Cancel", color = colorResource(id = R.color.white), fontSize = 16.sp)
                    }
                    })
            }
            Image(painter = painterResource(R.drawable.iconcaret), contentDescription = "choise",
                modifier = Modifier.size(35.dp).padding(end = 16.dp)
            )
        }
        when (selectedItem) {
            "Mathematics" -> {
                Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                    SelectedContent(mathematicsList) { item ->
                        val mathematicsItem = item as Mathematics
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 24.dp, top = 4.dp, bottom = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = mathematicsItem.day,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(Font(R.font.interv)),
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = mathematicsItem.typeofwork,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(Font(R.font.interv))
                            )
                            Text(
                                text = mathematicsItem.points,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(Font(R.font.interv)),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
            "Language" -> {
                    Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                        SelectedContent(languageList) { item ->
                            val languageItem = item as Language
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 24.dp, top = 4.dp, bottom = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = languageItem.day,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    fontFamily = FontFamily(Font(R.font.interv)),
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = languageItem.typeofwork,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    fontFamily = FontFamily(Font(R.font.interv))
                                )
                                Text(
                                    text = languageItem.points,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    fontFamily = FontFamily(Font(R.font.interv)),
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
            }
            "Literature" -> {
                Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                    SelectedContent(literatureList) { item ->
                        val literatureItem = item as Literature
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 24.dp, top = 4.dp, bottom = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = literatureItem.day,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(Font(R.font.interv)),
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = literatureItem.typeofwork,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(Font(R.font.interv))
                            )
                            Text(
                                text = literatureItem.points,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(Font(R.font.interv)),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun <T> SelectedContent(listFlow: Flow<List<T>>, itemContent: @Composable (T) -> Unit){
    val list by listFlow.collectAsState(initial = emptyList())

    Box(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.fillMaxHeight().padding(start = 100.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
            Column(modifier = Modifier.fillMaxHeight().padding(end = 100.dp).background(colorResource(R.color.black)).width(1.dp)) {  }
        }
        LazyColumn(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            item {
                Column {
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround) {
                        Text(text = "Day", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center, fontFamily = FontFamily(Font(R.font.interv)),
                            modifier = Modifier.padding(end = 16.dp)
                        )
                        Text(text = "Type of work", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center, fontFamily = FontFamily(Font(R.font.interv))
                        )
                        Text(text = "Points", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center, fontFamily = FontFamily(Font(R.font.interv)),
                            modifier = Modifier.padding(start = 24.dp)
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp).background(colorResource(R.color.black)).height(1.dp)) {}
                }
            }
            items(list){ item ->
                Column {
                    Row(modifier = Modifier.fillMaxWidth().padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        itemContent(item)
                    }
                }
                Row(modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp).background(colorResource(R.color.black)).height(1.dp)) {}
            }
        }
    }
}