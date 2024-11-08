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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.educat.orteacher.assos.ParentsGraph
import com.educat.orteacher.assos.R
import com.educat.orteacher.assos.database.AppDatabase
import com.educat.orteacher.assos.database.Dads
import com.educat.orteacher.assos.database.Mums
import kotlinx.coroutines.launch

@Composable
fun ParentsScreen(navController: NavController, name : String){
    var isToggled by remember { mutableStateOf(false) }
    val parNavController = rememberNavController() as NavHostController

    Column(modifier = Modifier.fillMaxSize().systemBarsPadding().verticalScroll(rememberScrollState()).background(brush = Brush.verticalGradient(listOf(
        colorResource(R.color.gradup), colorResource(R.color.graddown))))) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start) {
            Image(painter = painterResource(R.drawable.solarbackmain), contentDescription = "backtostudentslist",
                modifier = Modifier.size(60.dp).padding(top = 20.dp, start = 20.dp).clickable {
                    navController.navigate("MainScreen/Students")
                })
        }
        ToggleSwitch( isOn = isToggled, onToggle = { newState -> isToggled = newState
            val route = if (newState) "ParentDad" else "ParentMum"
            parNavController.navigate(route) { popUpTo(parNavController.graph.startDestinationId) { inclusive = true } } } )
        Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
            ParentsGraph(parNavController, name = name)
        }
        Column(modifier = Modifier.fillMaxWidth().padding(bottom = 64.dp)) {
            Button(onClick = {
                //navController.navigate("ParentsScreen")
            },
                modifier = Modifier.fillMaxWidth().padding(start = 32.dp, end = 32.dp, top = 8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.buttoncontinue)),
                shape = RoundedCornerShape(24.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 4.dp)){
                Text(text = "Meeting history",
                    color = colorResource(R.color.blacktext),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(onClick = { // navController.navigate("AssessmentsScreen")
            },
                modifier = Modifier.fillMaxWidth().padding(start = 32.dp, end = 32.dp, top = 8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.buttoncontinue)),
                shape = RoundedCornerShape(24.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 4.dp)){
                Text(text = "Contact",
                    color = colorResource(R.color.blacktext),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
@Composable fun ToggleSwitch( isOn: Boolean, onToggle: (Boolean) -> Unit ) {
    val alignment = if (isOn) Alignment.CenterEnd else Alignment.CenterStart
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val toggleWidth = (screenWidthDp - 32.dp) * 0.5f
    Box( modifier = Modifier.fillMaxWidth().height(80.dp).padding(horizontal = 32.dp, vertical = 16.dp)
        .background(color = colorResource(R.color.graddown), shape = RoundedCornerShape(30.dp)).clickable { onToggle(!isOn) } ) {
            Text( text = if (isOn) "Mum" else "Dad",
                modifier = Modifier.align(if (isOn) Alignment.CenterStart else Alignment.CenterEnd)
                    .padding(start = 60.dp, end = 60.dp).alpha(1f),
                color = colorResource(R.color.blacktext),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        Box( modifier = Modifier.fillMaxHeight().width(toggleWidth)
            .background(color = colorResource(R.color.continuetext),
                shape = RoundedCornerShape(30.dp)).align(alignment),
            contentAlignment = Alignment.Center ) {
            Text( text = if (isOn) "Dad" else "Mum",
                color = if (isOn) colorResource(R.color.graddown) else colorResource(R.color.graddown),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Composable
fun ParentMum(name : String){
    val context = LocalContext.current
    val db = remember { Room.databaseBuilder(context, AppDatabase::class.java, "database").build() }
    val mum by db.mumsDao().getMumByKey(name).collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    var showDialog = remember { mutableStateOf(false) }
    val field1 = remember { mutableStateOf("") }
    val field2 = remember { mutableStateOf("") }
    val field3 = remember { mutableStateOf("") }
    val field4 = remember { mutableStateOf("") }
    val field5 = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(R.drawable.baseline_delete), contentDescription = "delete",
                    modifier = Modifier.size(40.dp).padding(top = 8.dp, end = 8.dp).clickable {
                    scope.launch{
                        db.mumsDao().deleteByKey(mum!!.titlekey)
                    }
                })
            Image(painter = painterResource(R.drawable.add), contentDescription = "addmumdata",
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
                                placeholder = { Text("enter name...") }
                            )
                            OutlinedTextField(
                                value = field2.value,
                                onValueChange = { field2.value = it },
                                placeholder = { Text("enter years...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field3.value,
                                onValueChange = { field3.value = it },
                                placeholder = { Text("enter home address...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field4.value,
                                onValueChange = { field4.value = it },
                                placeholder = { Text("enter work address...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field5.value,
                                onValueChange = { field5.value = it },
                                placeholder = { Text("enter position...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    },
                    confirmButton = {
                        androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.continuetext)),
                            onClick = {
                                scope.launch {
                                    val mums = Mums(name = field1.value, yers = field2.value, homeaddress = field3.value,
                                        workaddress = field4.value, position = field5.value, titlekey = name)
                                    db.mumsDao().insert(mums)
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
        mum?.let {
            Text(text = it.name,
                color = colorResource(R.color.blacktext),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.interv))
            )
        }
        mum?.let {
            Text(text = it.yers,
                color = colorResource(R.color.blacktext),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.interv))
            )
        }
        Column(modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly) {
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Home address",
                    color = colorResource(R.color.blacktext),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.interv))
                )
                Card(modifier = Modifier.width(200.dp).height(60.dp),
                    shape = RoundedCornerShape(24.dp)) {
                    mum?.let {
                        Text(text = it.homeaddress,
                            color = colorResource(R.color.blacktext),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.interv)),
                            modifier = Modifier.background(colorResource(R.color.graddown)).padding(8.dp)
                        )
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Work address",
                    color = colorResource(R.color.blacktext),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.interv))
                )
                Card(modifier = Modifier.width(200.dp).height(60.dp),
                    shape = RoundedCornerShape(24.dp)) {
                    mum?.let {
                        Text(text = it.workaddress,
                            color = colorResource(R.color.blacktext),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.interv)),
                            modifier = Modifier.background(colorResource(R.color.graddown)).padding(8.dp)
                        )
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Position",
                    color = colorResource(R.color.blacktext),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.interv))
                )
                Card(modifier = Modifier.width(200.dp).height(35.dp),
                    shape = RoundedCornerShape(24.dp)) {
                    mum?.let {
                        Text(text = it.position,
                            color = colorResource(R.color.blacktext),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.interv)),
                            modifier = Modifier.background(colorResource(R.color.graddown)).padding(8.dp)
                        )
                    }
                }
            }

        }
    }
}
@Composable
fun ParentDad(name : String){
    val context = LocalContext.current
    val db = remember { Room.databaseBuilder(context, AppDatabase::class.java, "database").build() }
    val dad by db.dadsDao().getDadByKey(name).collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    var showDialog = remember { mutableStateOf(false) }
    val field1 = remember { mutableStateOf("") }
    val field2 = remember { mutableStateOf("") }
    val field3 = remember { mutableStateOf("") }
    val field4 = remember { mutableStateOf("") }
    val field5 = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(R.drawable.baseline_delete), contentDescription = "delete",
                modifier = Modifier.size(40.dp).padding(top = 8.dp, end = 8.dp).clickable {
                    scope.launch{
                        db.dadsDao().deleteByKey(dad!!.titlekey)
                    }
                })
            Image(painter = painterResource(R.drawable.add), contentDescription = "adddaddata",
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
                                placeholder = { Text("enter name...") }
                            )
                            OutlinedTextField(
                                value = field2.value,
                                onValueChange = { field2.value = it },
                                placeholder = { Text("enter years...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field3.value,
                                onValueChange = { field3.value = it },
                                placeholder = { Text("enter home address...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field4.value,
                                onValueChange = { field4.value = it },
                                placeholder = { Text("enter work address...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field5.value,
                                onValueChange = { field5.value = it },
                                placeholder = { Text("enter position...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    },
                    confirmButton = {
                        androidx.compose.material3.Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.continuetext)),
                            onClick = {
                                scope.launch {
                                    val dads = Dads(name = field1.value, yers = field2.value, homeaddress = field3.value,
                                        workaddress = field4.value, position = field5.value, titlekey = name)
                                    db.dadsDao().insert(dads)
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
        dad?.let {
            Text(text = it.name,
                color = colorResource(R.color.blacktext),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.interv))
            )
        }
        dad?.let {
            Text(text = it.yers,
                color = colorResource(R.color.blacktext),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.interv))
            )
        }
        Column(modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly) {
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Home address",
                    color = colorResource(R.color.blacktext),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.interv))
                )
                Card(modifier = Modifier.width(200.dp).height(60.dp),
                    shape = RoundedCornerShape(24.dp)) {
                    dad?.let {
                        Text(text = it.homeaddress,
                            color = colorResource(R.color.blacktext),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.interv)),
                            modifier = Modifier.background(colorResource(R.color.graddown)).padding(8.dp)
                        )
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Work address",
                    color = colorResource(R.color.blacktext),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.interv))
                )
                Card(modifier = Modifier.width(200.dp).height(60.dp),
                    shape = RoundedCornerShape(24.dp)) {
                    dad?.let {
                        Text(text = it.workaddress,
                            color = colorResource(R.color.blacktext),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.interv)),
                            modifier = Modifier.background(colorResource(R.color.graddown)).padding(8.dp)
                        )
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Position",
                    color = colorResource(R.color.blacktext),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.interv))
                )
                Card(modifier = Modifier.width(200.dp).height(35.dp),
                    shape = RoundedCornerShape(24.dp)) {
                    dad?.let {
                        Text(text = it.position,
                            color = colorResource(R.color.blacktext),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.interv)),
                            modifier = Modifier.background(colorResource(R.color.graddown)).padding(8.dp)
                        )
                    }
                }
            }

        }
    }
}