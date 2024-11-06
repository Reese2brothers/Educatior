package com.educat.orteacher.assos.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.educat.orteacher.assos.R


@Composable
fun StudentsDetailsScreen(navController: NavController){
    Column(modifier = Modifier.fillMaxSize().systemBarsPadding().background(brush = Brush.verticalGradient(listOf(
        colorResource(R.color.gradup), colorResource(R.color.graddown))))) {
        Card(modifier = Modifier.fillMaxWidth().height(300.dp),
            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)) {
            Box(modifier = Modifier.fillMaxSize().background(colorResource(R.color.rozoviy))) {
                Image(painter = painterResource(R.drawable.baseline_face), contentDescription = "bigavatar",
                    modifier = Modifier.fillMaxSize())
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start) {
                        Image(painter = painterResource(R.drawable.solarbackmain), contentDescription = "backtostudentslist",
                            modifier = Modifier.size(60.dp).padding(top = 20.dp, start = 20.dp).clickable {
                                //navController.navigate("StudentsScreen")
                            })
                    }
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End) {
                        Box(modifier = Modifier.size(60.dp).padding(end = 20.dp, bottom = 20.dp).clickable {
                            navController.navigate("NewStudentsDetailsScreen")
                        },
                            contentAlignment = Alignment.Center) {
                            Image(painter = painterResource(R.drawable.ellipse), contentDescription = "ellips",
                                modifier = Modifier.size(60.dp)
                            )
                            Image(painter = painterResource(R.drawable.baseline_edit), contentDescription = "add",
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Savkin Oleg",
                color = colorResource(R.color.blacktext),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "13/03/2016",
                color = colorResource(R.color.blacktext),
                fontSize = 20.sp
            )
            Text(text = "2-A",
                color = colorResource(R.color.blacktext),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly) {
            Row(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Academic performance",
                    color = colorResource(R.color.blacktext),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Card(modifier = Modifier.width(180.dp).wrapContentHeight(),
                    border = BorderStroke(width = 1.dp, colorResource(R.color.continuetext)),
                    shape = RoundedCornerShape(16.dp)) {
                        Box(modifier = Modifier.fillMaxWidth().background(colorResource(R.color.graddown))
                            .align(Alignment.CenterHorizontally)) {
                            Text(text = "Requires attention",
                                color = colorResource(R.color.red),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            )
                        }
                    }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Behaviour",
                    color = colorResource(R.color.blacktext),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Card(modifier = Modifier.width(180.dp).wrapContentHeight(),
                    border = BorderStroke(width = 1.dp, colorResource(R.color.continuetext)),
                    shape = RoundedCornerShape(16.dp)) {
                    Box(modifier = Modifier.fillMaxWidth().background(colorResource(R.color.graddown))
                        .align(Alignment.CenterHorizontally)) {
                        Text(text = "Educated",
                            color = colorResource(R.color.blacktext),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(4.dp)
                        )
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Hobbies",
                    color = colorResource(R.color.blacktext),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Card(modifier = Modifier.width(180.dp).wrapContentHeight(),
                    border = BorderStroke(width = 1.dp, colorResource(R.color.continuetext)),
                    shape = RoundedCornerShape(16.dp)) {
                    Box(modifier = Modifier.fillMaxWidth().background(colorResource(R.color.graddown))
                        .align(Alignment.CenterHorizontally)) {
                        Text(text = "Pain",
                            color = colorResource(R.color.blacktext),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(4.dp)
                        )
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Pet",
                    color = colorResource(R.color.blacktext),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Card(modifier = Modifier.width(180.dp).wrapContentHeight(),
                    border = BorderStroke(width = 1.dp, colorResource(R.color.continuetext)),
                    shape = RoundedCornerShape(16.dp)) {
                    Box(modifier = Modifier.fillMaxWidth().background(colorResource(R.color.graddown))
                        .align(Alignment.CenterHorizontally)) {
                        Text(text = "Cat",
                            color = colorResource(R.color.blacktext),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(4.dp)
                        )
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(top = 64.dp)) {
            Button(onClick = {  },
                modifier = Modifier.fillMaxWidth().padding(start = 32.dp, end = 32.dp, top = 8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.buttoncontinue)),
                shape = RoundedCornerShape(24.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 4.dp)){
                Text(text = "Parents",
                    color = colorResource(R.color.blacktext),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(onClick = {  },
                modifier = Modifier.fillMaxWidth().padding(start = 32.dp, end = 32.dp, top = 8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.buttoncontinue)),
                shape = RoundedCornerShape(24.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 4.dp)){
                Text(text = "Assessments",
                    color = colorResource(R.color.blacktext),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}