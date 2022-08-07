package com.example.scoutingreport

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ScoutingList.route) {
        // define composables to tell navhost how different screen looks like

        // for scouting list
        composable(route = Screen.ScoutingList.route) {
            ReportScreen(navController = navController)
        }

        // for scouting edit
        composable(route = Screen.ScoutingEdit.route) {
            ScoutingEditScreen(navController = navController)
        }
    }
}

