package com.educat.orteacher.assos

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.educat.orteacher.assos.screens.ContinueScreen
import com.educat.orteacher.assos.screens.MainNavigation
import com.educat.orteacher.assos.screens.MainScreen
import com.educat.orteacher.assos.screens.NewStudentsDetailsScreen
import com.educat.orteacher.assos.screens.OcenkiOtchetiScreen
import com.educat.orteacher.assos.screens.PlanLessonsScreen
import com.educat.orteacher.assos.screens.SettingsScreen
import com.educat.orteacher.assos.screens.StartScreen
import com.educat.orteacher.assos.screens.StudentsDetailsScreen
import com.educat.orteacher.assos.screens.StudentsScreen
import com.educat.orteacher.assos.screens.SubMainScreen
import com.educat.orteacher.assos.screens.TimerScreen

@Composable
fun NavGraphNavigate(context : Context, navController: NavHostController) {
    NavHost(navController = navController, startDestination = "ContinueScreen") {
        composable("ContinueScreen") {
            ContinueScreen(navController = navController)
        }
        composable("StartScreen") {
            StartScreen(navController = navController)
        }
        composable("MainScreen") {
            MainScreen(navController)
        }
        composable("SettingsScreen") {
            SettingsScreen(navController = navController)
        }
        composable("TimerScreen") {
            TimerScreen(navController = navController)
        }
        composable("NewStudentsDetailsScreen") {
            NewStudentsDetailsScreen(navController = navController)
        }
        composable("StudentsDetailsScreen") {
            StudentsDetailsScreen(navController = navController)
        }
    }
}

@Composable
fun NavigationGraph(subNavController: NavHostController, navController: NavController) {
    NavHost(navController = subNavController, startDestination = "SubMainScreen") {

        composable("SubMainScreen") {
            SubMainScreen(navController)
        }
        composable("StudentsScreen") {
            StudentsScreen(subNavController)
        }
        composable("PlanLessonsScreen") {
            PlanLessonsScreen(subNavController)
        }
        composable("OcenkiOtchetiScreen") {
            OcenkiOtchetiScreen(subNavController)
        }
        composable("MainNavigation") {
            MainNavigation(subNavController)
        }
    }
}