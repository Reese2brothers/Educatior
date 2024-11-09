package com.educat.orteacher.assos

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.educat.orteacher.assos.screens.AssessmentsScreen
import com.educat.orteacher.assos.screens.ContinueScreen
import com.educat.orteacher.assos.screens.MainNavigation
import com.educat.orteacher.assos.screens.MainScreen
import com.educat.orteacher.assos.screens.MeetingHistoryScreen
import com.educat.orteacher.assos.screens.NewStudentsDetailsScreen
import com.educat.orteacher.assos.screens.OcenkiOtchetiScreen
import com.educat.orteacher.assos.screens.ParentDad
import com.educat.orteacher.assos.screens.ParentMum
import com.educat.orteacher.assos.screens.ParentsScreen
import com.educat.orteacher.assos.screens.PlanLessonsScreen
import com.educat.orteacher.assos.screens.SettingsScreen
import com.educat.orteacher.assos.screens.StartScreen
import com.educat.orteacher.assos.screens.StudentsDetailsScreen
import com.educat.orteacher.assos.screens.StudentsScreen
import com.educat.orteacher.assos.screens.SubMainScreen
import com.educat.orteacher.assos.screens.TimerScreen

@Composable
fun NavGraphNavigate(context : Context, navController: NavHostController, subNavController: NavHostController, parNavController: NavHostController) {
    NavHost(navController = navController, startDestination = "ContinueScreen") {
        composable("ContinueScreen") {
            ContinueScreen(navController = navController)
        }
        composable("StartScreen") {
            StartScreen(navController = navController)
        }
        composable("MainScreen/{tab}") { backStackEntry ->
            val tab = backStackEntry.arguments?.getString("tab")
            MainScreen(navController, tab)
        }
        composable("SettingsScreen") {
            SettingsScreen(navController = navController)
        }
        composable("TimerScreen") {
            TimerScreen(navController = navController)
        }
        composable("NewStudentsDetailsScreen") {
            NewStudentsDetailsScreen(
                navController = navController,
                subNavController = subNavController
            )
        }
        composable(
            "StudentsDetailsScreen/{name}/{data}/{office}/{performance}/{behaviour}/{hobbies}/{pet}/{image}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("data") { type = NavType.StringType },
                navArgument("office") { type = NavType.StringType },
                navArgument("performance") { type = NavType.StringType },
                navArgument("behaviour") { type = NavType.StringType },
                navArgument("hobbies") { type = NavType.StringType },
                navArgument("pet") { type = NavType.StringType },
                navArgument("image") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val data = backStackEntry.arguments?.getString("data") ?: ""
            val office = backStackEntry.arguments?.getString("office") ?: ""
            val performance = backStackEntry.arguments?.getString("performance") ?: ""
            val behaviour = backStackEntry.arguments?.getString("behaviour") ?: ""
            val hobbies = backStackEntry.arguments?.getString("hobbies") ?: ""
            val pet = backStackEntry.arguments?.getString("pet") ?: ""
            val image = backStackEntry.arguments?.getString("image") ?: ""
            StudentsDetailsScreen(navController, name, data, office, performance, behaviour, hobbies, pet, image)
        }
        composable("AssessmentsScreen") {
            AssessmentsScreen(navController)
        }
        composable(
            "ParentsScreen/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            ParentsScreen(navController = navController, name = name)
        }
        composable("MeetingHistoryScreen") {
            MeetingHistoryScreen()
        }
    }
}


@Composable
fun NavigationGraph(subNavController: NavHostController, navController: NavController, selectedTab: MutableState<String?>,
                    startDestination: String) {
    NavHost(navController = subNavController, startDestination = startDestination) {

        composable("SubMainScreen") {
            SubMainScreen(navController, selectedTab)
        }
        composable("StudentsScreen") {
            StudentsScreen(subNavController, navController)
        }
        composable("PlanLessonsScreen") {
            PlanLessonsScreen()
        }
        composable("OcenkiOtchetiScreen") {
            OcenkiOtchetiScreen(subNavController)
        }
        composable("MainNavigation") {
            MainNavigation(subNavController, selectedTab)
        }
    }
}

@Composable
fun ParentsGraph(parNavController: NavHostController, name: String) {
    NavHost(navController = parNavController, startDestination = "ParentMum") {

        composable("ParentMum") {
            ParentMum(name)
        }
        composable("ParentDad") {
            ParentDad(name)
        }
    }
}