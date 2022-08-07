package com.example.scoutingreport

sealed class Screen(val route: String) {
    object ScoutingList: Screen("scoutingList")
    object ScoutingEdit: Screen("scoutingEdit")
}
