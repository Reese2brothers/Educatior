package com.educat.orteacher.assos.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
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
import androidx.navigation.compose.rememberNavController
import com.educat.orteacher.assos.MainActivity
import com.educat.orteacher.assos.NavigationGraph
import com.educat.orteacher.assos.R

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
    Column(modifier = Modifier.fillMaxSize().systemBarsPadding().background(
            brush = Brush.verticalGradient(listOf(colorResource(R.color.gradup), colorResource(R.color.graddown)))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween) {
        Column(modifier = Modifier.fillMaxWidth().weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally) {
//            NavigationGraph(
//                subNavController = subNavController,
//                navController = navController,
//                selectedTab = "StudentsScreen"
//            )
            when (selectedTab.value) {
                "Students" -> NavigationGraph(subNavController, navController, selectedTab,"StudentsScreen")
                // другие экраны вкладок здесь
                else -> NavigationGraph(subNavController, navController, selectedTab,"SubMainScreen")
            }
        }
        Row { MainNavigation(subNavController, selectedTab) }
    }
}

//@Composable
//fun MainNavigation(subNavController: NavController, selectedTab: MutableState<String?>) {
//    Row(
//        horizontalArrangement = Arrangement.SpaceAround,
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.fillMaxWidth().wrapContentHeight().background(colorResource(R.color.continuetext))
//    ) {
//        val selectedIcon = remember { mutableStateOf("SubMainScreen") }
//        val icons = listOf(
//            "SubMainScreen" to R.drawable.mainscreen,
//            "StudentsScreen" to R.drawable.parents,
//            "PlanLessonsScreen" to R.drawable.planlessons,
//            "OcenkiOtchetiScreen" to R.drawable.ocenkiotcheti
//        )
//        icons.forEach { (route, iconRes) ->
//            Image(
//                painter = painterResource(iconRes),
//                contentDescription = route,
//                modifier = Modifier.size(50.dp).padding(top = 4.dp, bottom = 4.dp).clickable {
//                        selectedIcon.value = route
//                        subNavController.navigate(route)
//                    subNavController.navigate(route) {
//                        popUpTo(subNavController.graph.startDestinationId) {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                    },
//                colorFilter = ColorFilter.tint(
//                    if (selectedIcon.value == route) colorResource(R.color.black) else colorResource(
//                        R.color.graddown
//                    )
//                )
//            )
//        }
//    }
//}
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
    Column(modifier = Modifier.background(brush = Brush.verticalGradient(listOf(
        colorResource(R.color.gradup), colorResource(R.color.graddown)))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(R.drawable.solarsettings), contentDescription = "gotosettings",
                modifier = Modifier
                    .size(60.dp)
                    .padding(top = 16.dp, end = 16.dp)
                    .clickable { navController.navigate("SettingsScreen") })
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "2-A", color = colorResource(R.color.blacktext),
                fontSize = 80.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.interv)),
            )
            Text(text = "20 students", color = colorResource(R.color.blacktext),
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.interv)),
            )
            Button(onClick = { navController.navigate("TimerScreen") },
                modifier = Modifier.width(200.dp).padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.continuetext)),
                shape = RoundedCornerShape(24.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 4.dp)){
                Text(text = "Call timer", modifier = Modifier.padding(4.dp),
                    color = colorResource(R.color.graddown),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Column(horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().weight(1f)) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = "Planned events",
                    modifier = Modifier.padding(top = 16.dp, start = 24.dp),
                    color = colorResource(R.color.blacktext),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.interv)),
                    textAlign = TextAlign.Start
                )
                LazyRow(modifier = Modifier.height(100.dp).padding(top = 16.dp, start = 24.dp)) {  }
                Text(text = "Schedule of lessons",
                    modifier = Modifier.padding(top = 16.dp, start = 24.dp),
                    color = colorResource(R.color.blacktext),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.interv)),
                    textAlign = TextAlign.Start
                )
                LazyRow(modifier = Modifier.height(60.dp).padding(top = 16.dp, start = 24.dp)) {  }
            }
        }
    }
}