package com.educat.orteacher.assos.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.educat.orteacher.assos.R

@Composable
fun TimerScreen(navController: NavController){

    BackHandler {
        navController.navigate("MainScreen")
    }

    Column(modifier = Modifier.fillMaxSize().systemBarsPadding().background(brush = Brush.verticalGradient(listOf(
        colorResource(R.color.gradup), colorResource(R.color.graddown)))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

    }
}