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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.educat.orteacher.assos.R
import androidx.compose.ui.unit.sp

@Composable
fun StudentsScreen(navController: NavController){
    Column(modifier = Modifier.fillMaxSize().background(brush = Brush.verticalGradient(listOf(
        colorResource(R.color.gradup), colorResource(R.color.graddown)))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

//        LazyColumn(modifier = Modifier.fillMaxSize()) {
//            itemsIndexed(){ index, item ->
//               StudentsCard()
//            }
//        }
    }
}
@Preview(showBackground = true)
@Composable
fun StudentsCard(){
    Card(modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Transparent),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp)){
        Box(modifier = Modifier.fillMaxSize()) {
            Card(modifier = Modifier.fillMaxSize().padding(start = 20.dp, end = 24.dp),
                shape = RoundedCornerShape(16.dp)){
                Row(modifier = Modifier.fillMaxSize()
                    .background(colorResource(R.color.white)),
                    verticalAlignment = Alignment.CenterVertically) {
                    Card(modifier = Modifier.size(100.dp),
                        shape = RoundedCornerShape(12.dp)) {
                        Image(painter = painterResource(R.drawable.avatar), contentDescription = "avatar",
                            modifier = Modifier.height(100.dp).width(100.dp),
                            contentScale = ContentScale.Crop)
                    }
                    Column(modifier = Modifier.fillMaxHeight().wrapContentWidth().padding(start = 16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start) {
                        Text(text = "Savkin Oleg",
                            color = colorResource(R.color.blacktext),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "2-A",
                            color = colorResource(R.color.blacktext),
                            fontSize = 20.sp
                        )
                        Text(text = "Requires attention",
                            color = colorResource(R.color.red),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Row(modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(R.drawable.solarto), contentDescription = "gotostudentsdetails",
                    modifier = Modifier.size(60.dp).padding(end = 8.dp).clickable {  })
            }
        }
    }
}