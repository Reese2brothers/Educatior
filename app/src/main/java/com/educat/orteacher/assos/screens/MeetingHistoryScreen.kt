package com.educat.orteacher.assos.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.educat.orteacher.assos.R
import com.educat.orteacher.assos.database.AppDatabase
import com.educat.orteacher.assos.database.Language
import com.educat.orteacher.assos.database.Literature
import com.educat.orteacher.assos.database.Mathematics
import com.educat.orteacher.assos.database.MeetingHistory
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun MeetingHistoryScreen(){
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var showDialog = remember { mutableStateOf(false) }
    val field1 = remember { mutableStateOf("") }
    val field2 = remember { mutableStateOf("") }
    val field3 = remember { mutableStateOf("") }
    val db = remember { Room.databaseBuilder(context, AppDatabase::class.java, "database").build() }
    val historyList by db.meetinghistoryDao().getAll().collectAsState(initial= emptyList())

    Column(modifier = Modifier.fillMaxSize().systemBarsPadding()
        .background(brush = Brush.verticalGradient(listOf(
        colorResource(R.color.gradup), colorResource(R.color.graddown))))) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End) {
            Image(painter = painterResource(R.drawable.add), contentDescription = "addnewnotice",
                modifier = Modifier.size(40.dp).padding(top = 16.dp, end = 16.dp).clickable {
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
                                placeholder = { Text("enter day...") }
                            )
                            OutlinedTextField(
                                value = field2.value,
                                onValueChange = { field2.value = it },
                                placeholder = { Text("enter label...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedTextField(
                                value = field3.value,
                                onValueChange = { field3.value = it },
                                placeholder = { Text("enter comments...") },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    },
                    confirmButton = {
                        Button(colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.continuetext)),
                            onClick = {
                                scope.launch {
                                   val history = MeetingHistory(data = field1.value, label = field2.value, coments = field3.value)
                                    db.meetinghistoryDao().insert(history)
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
        }
        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
            itemsIndexed(historyList){ index, item ->
                Card(modifier = Modifier.fillMaxWidth().padding(top = 4.dp, start = 16.dp, end = 16.dp)
                    .background(Color.Transparent),
                    elevation = 4.dp,
                    shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.fillMaxSize().background(colorResource(R.color.graddown)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly) {
                        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(text = item.data,
                                color = colorResource(R.color.blacktext),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.interv)),
                                modifier = Modifier.padding(start = 120.dp)
                            )
                            Image(painter = painterResource(R.drawable.baseline_delete), contentDescription = "deletenotice",
                                modifier = Modifier.size(30.dp).padding(end = 8.dp).clickable {
                                    scope.launch {
                                        db.meetinghistoryDao().deleteById(item.id)
                                    }
                                }
                            )
                        }

                        Text(text = item.label,
                            color = colorResource(R.color.blacktext),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.interv))
                        )
                        Text(text = item.coments,
                            color = colorResource(R.color.blacktext),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.interv)),
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}