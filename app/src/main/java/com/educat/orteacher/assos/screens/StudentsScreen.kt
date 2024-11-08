package com.educat.orteacher.assos.screens

import android.annotation.SuppressLint
import android.net.Uri
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import coil.compose.rememberAsyncImagePainter
import com.educat.orteacher.assos.R
import com.educat.orteacher.assos.database.AppDatabase
import com.educat.orteacher.assos.database.Students
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@Composable
fun StudentsScreen(subNavController: NavController, navController: NavController){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val db = remember { Room.databaseBuilder(context, AppDatabase::class.java, "database").build() }
    val studentsList by db.studentsDao().getAll().collectAsState(initial= emptyList())

    Column(modifier = Modifier.fillMaxSize().background(brush = Brush.verticalGradient(listOf(
        colorResource(R.color.gradup), colorResource(R.color.rozoviy)))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Students",
                color = colorResource(R.color.blacktext),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.interv)),
                    modifier = Modifier.padding(start = 24.dp)
            )
            Row(horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(R.drawable.baseline_delete),
                    contentDescription = "deletestudents",
                    modifier = Modifier.size(40.dp).padding(end = 16.dp).clickable {
                        scope.launch {
                            db.studentsDao().deleteAll()
                        }
                    })
                    Image(painter = painterResource(R.drawable.add), contentDescription = "add",
                        modifier = Modifier.size(35.dp).padding(end = 16.dp).clickable {
                            navController.navigate("NewStudentsDetailsScreen")
                        }
                    )
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(studentsList){ index, item ->
               StudentsCard(item, navController)
            }
        }
    }
}

@Composable
fun StudentsCard(item: Students, navController: NavController) {
    Card(modifier = Modifier.fillMaxWidth().height(100.dp).padding(top = 4.dp, start = 16.dp, end = 16.dp).background(Color.Transparent),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp)){
        Box(modifier = Modifier.fillMaxSize().background(colorResource(R.color.gradup))) {
            Card(modifier = Modifier.fillMaxSize().padding(end = 24.dp).background(Color.Transparent),
                shape = RoundedCornerShape(16.dp)){
                Row(modifier = Modifier.fillMaxSize()
                    .background(colorResource(R.color.rozoviy)),
                    verticalAlignment = Alignment.CenterVertically) {
                    Card(modifier = Modifier.size(100.dp),
                        shape = RoundedCornerShape(12.dp)) {
                        Image(painter = rememberAsyncImagePainter(item.image),
                            contentDescription = "avatar",
                            modifier = Modifier.height(100.dp).width(100.dp),
                            contentScale = ContentScale.Crop)
                    }
                    Column(modifier = Modifier.fillMaxHeight().wrapContentWidth().padding(start = 16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start) {
                        Text(text = item.name,
                            color = colorResource(R.color.blacktext),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.interv))
                        )
                        Text(text = item.office,
                            color = colorResource(R.color.blacktext),
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.interv))
                        )
                        Text(text = item.performance,
                            color = colorResource(R.color.red),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.interv))
                        )
                    }
                }
            }
            Row(modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(R.drawable.solarto), contentDescription = "gotostudentsdetails",
                    modifier = Modifier.size(60.dp).padding(end = 8.dp).clickable {
                        navController.navigate( "StudentsDetailsScreen/" +
                                "${Uri.encode(item.name)}/${Uri.encode(item.data)}" +
                                "/${Uri.encode(item.office)}/${Uri.encode(item.performance)}" +
                                "/${Uri.encode(item.behaviour)}/${Uri.encode(item.hobbies)}" +
                                "/${Uri.encode(item.pet)}/${Uri.encode(item.image)}" )
                    })
            }
        }
    }
}