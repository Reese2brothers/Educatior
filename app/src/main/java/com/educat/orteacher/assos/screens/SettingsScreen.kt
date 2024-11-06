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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.educat.orteacher.assos.R

@Composable
fun SettingsScreen(navController: NavController){
    Column(modifier = Modifier.fillMaxSize().systemBarsPadding().background(brush = Brush.verticalGradient(listOf(
        colorResource(R.color.gradup), colorResource(R.color.graddown)
    ))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(R.drawable.solarbackmain), contentDescription = "gotomain",
                modifier = Modifier.size(60.dp).padding(start = 12.dp).clickable { navController.navigate("MainScreen") })
        }
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Card(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 4.dp)
                .fillMaxWidth().height(150.dp).background(Color.Transparent),
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.fillMaxSize().background(colorResource(R.color.buttoncontinue)),
                        verticalArrangement = Arrangement.SpaceEvenly) {
                        Text(text = "Rate us!", color = colorResource(R.color.blacktext),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.interv)),
                            modifier = Modifier.padding(start = 32.dp)
                        )
                        Text(text = "Your feedback helps\n" +
                                "us to improve", color = colorResource(R.color.continuetext),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.interv)),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        Text(text = "Give a rate", color = colorResource(R.color.graddown),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.interv)),
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    }
            }
            Card(modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth().height(50.dp).background(Color.Transparent),
                shape = RoundedCornerShape(24.dp),
                elevation = 8.dp) {
                Row(modifier = Modifier.fillMaxSize().background(colorResource(R.color.buttoncontinue)),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Developer’s website", color = colorResource(R.color.continuetext),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.interv)),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Image(painter = painterResource(R.drawable.vector), contentDescription = "continue_image",
                        modifier = Modifier.size(25.dp).padding(end = 16.dp))
                }
            }
            Card(modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth().height(50.dp).background(Color.Transparent),
                shape = RoundedCornerShape(24.dp),
                elevation = 8.dp) {
                Row(modifier = Modifier.fillMaxSize().background(colorResource(R.color.buttoncontinue)),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Privacy Policy", color = colorResource(R.color.continuetext),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.interv)),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Image(painter = painterResource(R.drawable.vector), contentDescription = "continue_image",
                        modifier = Modifier.size(25.dp).padding(end = 16.dp))
                }
            }
            Card(modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth().height(50.dp).background(Color.Transparent),
                shape = RoundedCornerShape(24.dp),
                elevation = 8.dp) {
                Row(modifier = Modifier.fillMaxSize().background(colorResource(R.color.buttoncontinue)),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Support Page", color = colorResource(R.color.continuetext),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.interv)),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Image(painter = painterResource(R.drawable.vector), contentDescription = "continue_image",
                        modifier = Modifier.size(25.dp).padding(end = 16.dp))
                }
            }
        }
    }
}