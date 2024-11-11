package com.educat.orteacher.assos.screens

import android.media.MediaPlayer
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.educat.orteacher.assos.MainActivity
import com.educat.orteacher.assos.NavigationGraph
import com.educat.orteacher.assos.R
import com.educat.orteacher.assos.database.AppDatabase
import com.educat.orteacher.assos.database.ClassStudents
import com.educat.orteacher.assos.database.PlannedEvents
import com.educat.orteacher.assos.database.SheduleOfLessons
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navController: NavController, initialTab: String? = "Home") {
    val activity = LocalContext.current as MainActivity
    val subNavController = rememberNavController()
    val selectedTab = remember { mutableStateOf(initialTab) }

    BackHandler {
        if (!subNavController.popBackStack()) {
            navController.popBackStack()
        }
        activity.finishAffinity()
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding()
        .background(
            brush = Brush.verticalGradient(
                listOf(
                    colorResource(R.color.gradup),
                    colorResource(R.color.graddown)
                )
            )
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally) {
            when (selectedTab.value) {
                "Students" -> NavigationGraph(subNavController, navController, selectedTab,"StudentsScreen")
                "PlanLessons" -> NavigationGraph(subNavController, navController, selectedTab,"PlanLessonsScreen")
                "OcenkiOtcheti" -> NavigationGraph(subNavController, navController, selectedTab,"OcenkiOtchetiScreen")
                else -> NavigationGraph(subNavController, navController, selectedTab,"SubMainScreen")
            }
        }
        Row { MainNavigation(subNavController, selectedTab) }
    }
}

@Composable
fun MainNavigation(navController: NavController, selectedTab: MutableState<String?>) {
    val tabs = listOf(
        "SubMainScreen" to R.drawable.mainscreen,
        "StudentsScreen" to R.drawable.parents,
        "PlanLessonsScreen" to R.drawable.planlessons,
        "OcenkiOtchetiScreen" to R.drawable.ocenkiotcheti
    )

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(colorResource(R.color.continuetext))
    ) {
        tabs.forEach { (route, iconRes) ->
            Image(
                painter = painterResource(iconRes),
                contentDescription = route,
                modifier = Modifier
                    .size(50.dp)
                    .padding(top = 4.dp, bottom = 4.dp)
                    .clickable {
                        selectedTab.value = route
                        navController.navigate(route)
                    },
                colorFilter = ColorFilter.tint(
                    if (selectedTab.value == route) colorResource(R.color.black) else colorResource(
                        R.color.graddown
                    )
                )
            )
        }
    }
}
@Composable
fun SubMainScreen(navController: NavController, selectedTab: MutableState<String?>){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val db = remember { Room.databaseBuilder(context, AppDatabase::class.java, "database").build() }
    val plannedEvents = db.plannedEventsDao().getAll().collectAsState(initial = emptyList())
    val sheduleOfLessons = db.sheduleOfLessonsDao().getAll().collectAsState(initial = emptyList())
    val classstudents = db.classStudentsDao().getAll().collectAsState(initial = emptyList())
    var showDialog = remember { mutableStateOf(false) }
    var showDialogTwo = remember { mutableStateOf(false) }
    var showDialogThree = remember { mutableStateOf(false) }
    var showDialogFour = remember { mutableStateOf(false) }
    val field1 = remember { mutableStateOf("") }
    val field2 = remember { mutableStateOf("") }
    val field3 = remember { mutableStateOf("") }
    val field4 = remember { mutableStateOf("") }
    val field5 = remember { mutableStateOf("") }
    val field6 = remember { mutableStateOf("") }

    Column(modifier = Modifier.background(brush = Brush.verticalGradient(listOf(
        colorResource(R.color.gradup), colorResource(R.color.graddown)))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        var remainingTime by remember { mutableStateOf(0L) }
        var timerRunning by remember { mutableStateOf(false) }
        var showBell by remember { mutableStateOf(false) }
        var resetCounter by remember { mutableStateOf(0) }
        fun formatTime(timeInMillis: Long): String {
            val minutes = (timeInMillis / 1000 / 60).toInt()
            val seconds = (timeInMillis / 1000 % 60).toInt()
            return String.format("%02d:%02d", minutes, seconds)
        }
        LaunchedEffect(key1 = timerRunning, key2 = remainingTime, key3 = resetCounter) {
            if (timerRunning && remainingTime > 0) {
                delay(1000)
                remainingTime -= 1000
            } else if (timerRunning && remainingTime <= 0) {
                timerRunning = false
                remainingTime = 0L
                showBell = true
            }
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier.width(200.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start) {
                val formattedTime by remember { derivedStateOf { formatTime(remainingTime) } }
                Text(text = formattedTime,
                    modifier = Modifier.padding(start = 16.dp),
                    color = colorResource(R.color.continuetext),
                    fontSize = 33.sp,
                    fontWeight = FontWeight.Bold
                )
                Image(painter = painterResource(R.drawable.stop), contentDescription = "stop",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(start = 8.dp)
                        .clickable {
                            timerRunning = false
                            remainingTime = 0L
                            showBell = false
                            resetCounter++
                        })
                if (showBell) {
                    val imageLoader = ImageLoader.Builder(context).build()
                    val gifPainter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.bells)
                            .build(),
                        imageLoader = imageLoader
                    )
                    Image(
                        painter = gifPainter,
                        contentDescription = null,
                        modifier = Modifier.size(70.dp)
                    )
                }
            }
            Image(painter = painterResource(R.drawable.solarsettings), contentDescription = "gotosettings",
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 16.dp)
                    .clickable { navController.navigate("SettingsScreen") })
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Column {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 64.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(R.drawable.baseline_delete), contentDescription = "delete",
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 8.dp)
                            .clickable {
                                scope.launch {
                                    db
                                        .classStudentsDao()
                                        .deleteAll()
                                }
                            }
                    )
                    Image(painter = painterResource(R.drawable.add), contentDescription = "add",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { showDialogThree.value = true }
                    )
                    if (showDialogThree.value) {
                        AlertDialog(
                            onDismissRequest = { showDialogThree.value = false },
                            containerColor = colorResource(id = R.color.graddown),
                            title = { Text(text = "Enter new data!", color = colorResource(id = R.color.continuetext),
                                fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                            text = {
                                Column {
                                    OutlinedTextField(
                                        value = field4.value,
                                        onValueChange = { field4.value = it },
                                        placeholder = { Text("enter class...") },
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                    OutlinedTextField(
                                        value = field5.value,
                                        onValueChange = { field5.value = it },
                                        placeholder = { Text("enter quantity of students...") },
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                            },
                            confirmButton = {
                                androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.continuetext)),
                                    onClick = {
                                        scope.launch {
                                            db.classStudentsDao().insert(ClassStudents(classs = field4.value, students = field5.value))
                                        }
                                        showDialogThree.value = false
                                    }) { Text("Save", color = colorResource(id = R.color.white), fontSize = 16.sp)
                                }
                            },
                            dismissButton = { androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.continuetext)
                            ),
                                onClick = { showDialogThree.value = false }) {
                                Text("Cancel", color = colorResource(id = R.color.white), fontSize = 16.sp)
                            }
                            })
                    }
                }
            }
            if (classstudents.value.isNotEmpty()) {
                val firstStudent = classstudents.value.first()
                Text(
                    text = firstStudent.classs,
                    color = colorResource(R.color.blacktext),
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.interv)),
                )
                Text(
                    text = firstStudent.students,
                    color = colorResource(R.color.blacktext),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.interv)),
                )
            }
            Button(onClick = { showDialogFour.value = true },
                modifier = Modifier
                    .width(200.dp)
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.continuetext)),
                shape = RoundedCornerShape(24.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 4.dp)){
                Text(text = "Call timer", modifier = Modifier.padding(4.dp),
                    color = colorResource(R.color.graddown),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            if (showDialogFour.value) {
                AlertDialog(
                    onDismissRequest = { showDialogFour.value = false },
                    containerColor = colorResource(id = R.color.graddown),
                    title = { Text(text = "Enter time!", color = colorResource(id = R.color.continuetext),
                        fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = field6.value,
                                onValueChange = { field6.value = it },
                                placeholder = { Text("enter minutes...") },
                                modifier = Modifier.padding(top = 8.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            )
                        }
                    },
                    confirmButton = {
                        androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.continuetext)),
                            onClick = {
                                timerRunning = false
                                showBell = false
                                val minutes = field6.value.toLongOrNull() ?: 0
                                remainingTime = minutes * 60 * 1000
                                timerRunning = true
                                scope.launch {
                                    while (remainingTime > 0 && timerRunning) {
                                        delay(1000)
                                        remainingTime -= 1000
                                    }
                                    if (remainingTime == 0L && timerRunning) {
                                        val mediaPlayer = MediaPlayer.create(context, R.raw.zvukkolokolchika)
                                        mediaPlayer.start()
                                        showBell = true
                                    }
                                }
                                showDialogFour.value = false
                            }) {
                            Text("Ok", color = colorResource(id = R.color.white), fontSize = 16.sp)
                        }
                    },
                    dismissButton = { androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.continuetext)
                    ),
                        onClick = { showDialogFour.value = false }) {
                        Text("Cancel", color = colorResource(id = R.color.white), fontSize = 16.sp)
                    }
                    })
            }
        }


        Column(horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {
            Column(horizontalAlignment = Alignment.Start) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, end = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Planned events",
                        modifier = Modifier.padding(start = 24.dp),
                        color = colorResource(R.color.blacktext),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.interv)),
                        textAlign = TextAlign.Start
                    )
                    Image(painter = painterResource(R.drawable.add), contentDescription = "newlesson",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { showDialog.value = true })
                    if (showDialog.value) {
                        AlertDialog(
                            onDismissRequest = { showDialog.value = false },
                            containerColor = colorResource(id = R.color.graddown),
                            title = { Text(text = "Enter new lesson!", color = colorResource(id = R.color.continuetext),
                                fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                            text = {
                                Column {
                                    OutlinedTextField(
                                        value = field2.value,
                                        onValueChange = { field2.value = it },
                                        placeholder = { Text("enter event...") },
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                    OutlinedTextField(
                                        value = field3.value,
                                        onValueChange = { field3.value = it },
                                        placeholder = { Text("enter data...") },
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                            },
                            confirmButton = {
                                androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.continuetext)),
                                    onClick = {
                                        scope.launch {
                                            db.plannedEventsDao().insert(PlannedEvents(event = field2.value, data = field3.value))
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
                LazyRow(modifier = Modifier
                    .height(150.dp)
                    .padding(top = 16.dp, start = 24.dp, end = 8.dp)) {
                    itemsIndexed(plannedEvents.value){index, item ->
                        Card(modifier = Modifier
                            .width(260.dp)
                            .height(150.dp)
                            .padding(start = 16.dp)
                            .background(Color.Transparent),
                            elevation = 4.dp,
                            shape = RoundedCornerShape(24.dp)) {
                           Column(modifier = Modifier
                               .fillMaxSize()
                               .background(colorResource(R.color.graddown)),
                                horizontalAlignment = Alignment.Start,
                               verticalArrangement = Arrangement.SpaceBetween) {
                               Row(modifier = Modifier.fillMaxWidth(),
                                   horizontalArrangement = Arrangement.End){
                                   Image(painter = painterResource(R.drawable.baseline_delete), contentDescription = "delete",
                                       modifier = Modifier
                                           .size(30.dp)
                                           .padding(end = 8.dp)
                                           .clickable {
                                               scope.launch {
                                                   db
                                                       .plannedEventsDao()
                                                       .delete(item)
                                               }
                                           }
                                   )
                               }
                                Text(text = item.event,
                                    color = colorResource(R.color.continuetext),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily(Font(R.font.interv)),
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .padding(start = 12.dp)
                                        .weight(1f)
                                )
                                Text(text = item.data,
                                    color = colorResource(R.color.continuetext),
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.interv)),
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.padding(start = 130.dp, bottom = 4.dp)
                                )
                            }
                        }
                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, end = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Schedule of lessons",
                        modifier = Modifier.padding(start = 24.dp),
                        color = colorResource(R.color.blacktext),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.interv)),
                        textAlign = TextAlign.Start
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(R.drawable.baseline_delete), contentDescription = "delete",
                            modifier = Modifier
                                .size(30.dp)
                                .padding(end = 8.dp)
                                .clickable {
                                    scope.launch {
                                        db
                                            .sheduleOfLessonsDao()
                                            .deleteAll()
                                    }
                                }
                        )
                        Image(painter = painterResource(R.drawable.add), contentDescription = "newevent",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { showDialogTwo.value = true })
                        if (showDialogTwo.value) {
                            AlertDialog(
                                onDismissRequest = { showDialogTwo.value = false },
                                containerColor = colorResource(id = R.color.graddown),
                                title = { Text(text = "Enter new lesson!", color = colorResource(id = R.color.continuetext),
                                    fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                                text = {
                                    Column {
                                        OutlinedTextField(
                                            value = field1.value,
                                            onValueChange = { field1.value = it },
                                            placeholder = { Text("enter lesson...") }
                                        )
                                    }
                                },
                                confirmButton = {
                                    androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                        containerColor = colorResource(id = R.color.continuetext)),
                                        onClick = {
                                            scope.launch {
                                                db.sheduleOfLessonsDao().insert(SheduleOfLessons(lesson = field1.value))
                                            }
                                            showDialogTwo.value = false
                                        }) { Text("Save", color = colorResource(id = R.color.white), fontSize = 16.sp)
                                    }
                                },
                                dismissButton = { androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.continuetext)
                                ),
                                    onClick = { showDialogTwo.value = false }) {
                                    Text("Cancel", color = colorResource(id = R.color.white), fontSize = 16.sp)
                                }
                                })
                        }
                    }
                }
                LazyRow(modifier = Modifier
                    .height(60.dp)
                    .padding(top = 16.dp, start = 24.dp, end = 8.dp)) {
                    itemsIndexed(sheduleOfLessons.value) { index, item ->
                       Card(modifier = Modifier
                           .width(220.dp)
                           .height(100.dp)
                           .padding(start = 16.dp)
                           .background(Color.Transparent),
                            elevation = 4.dp,
                            shape = RoundedCornerShape(24.dp)
                        ) {
                           Row(modifier = Modifier
                               .fillMaxSize()
                               .background(colorResource(R.color.graddown)),
                               verticalAlignment = Alignment.CenterVertically) {
                               Text(text = "${index+1}. ",
                                   color = colorResource(R.color.continuetext),
                                   fontSize = 22.sp,
                                   fontWeight = FontWeight.Bold,
                                   fontFamily = FontFamily(Font(R.font.interv)),
                                   textAlign = TextAlign.Start,
                                   modifier = Modifier.padding(start = 8.dp)
                               )
                               Text(text = item.lesson,
                                   color = colorResource(R.color.continuetext),
                                   fontSize = 24.sp,
                                   fontWeight = FontWeight.Bold,
                                   fontFamily = FontFamily(Font(R.font.interv)),
                                   textAlign = TextAlign.Start
                               )
                           }
                        }
                    }
                }
            }
        }
    }
}