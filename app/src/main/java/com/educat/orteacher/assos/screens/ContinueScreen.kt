package com.educat.orteacher.assos.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
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
import com.educat.orteacher.assos.MainActivity
import com.educat.orteacher.assos.R


@Composable
fun ContinueScreen(navController: NavController){
    val activity = LocalContext.current as MainActivity

    BackHandler {
        activity.finishAffinity()
    }

    Column(modifier = Modifier.fillMaxSize().systemBarsPadding().background(brush = Brush.verticalGradient(listOf(
        colorResource(R.color.gradup), colorResource(R.color.graddown)))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Image(painter = painterResource(R.drawable.continueimage), contentDescription = "continue_image",
            modifier = Modifier.padding(top = 80.dp).fillMaxWidth().height(400.dp))
        Text(text = "Here you will be able to organise your lessons, plan your timetable, track student " +
                "progress, check homework and communicate with parents. ",
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
            color = colorResource(R.color.continuetext),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.interv)),
            textAlign = TextAlign.Center)
        Button(onClick = { navController.navigate("StartScreen") },
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 32.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.buttoncontinue)),
            shape = RoundedCornerShape(24.dp),
            elevation = ButtonDefaults.elevation(defaultElevation = 4.dp)){
            Text(text = "Continue",
                color = colorResource(R.color.blacktext),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}