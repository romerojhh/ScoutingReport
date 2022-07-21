package com.example.scoutingreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scoutingreport.ui.theme.ScoutingReportTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

class ReportScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScoutingReportTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainReportScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainReportScreen() {

    // 2 page for Shared tab and Saved tab
    val pagerState = rememberPagerState(pageCount = 2)

    Scaffold(
        topBar =
        {
            TopAppBar(
                title = { Text("Reports") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },

        bottomBar =
        {
            BottomAppBar(
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    NavigateIcon(icon = Icons.Default.LocationOn, title = "Scout")
                    NavigateIcon(icon = Icons.Default.List, title = "Reports")
                }
            }
        },

        content = {
            ScaffoldContent(
                modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
                pagerState = pagerState
            )
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ScaffoldContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState
) {
    Tabs(pagerState = pagerState)
}

// on below line we are
// creating a function for tabs
@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf("Shared", "Saved")

    // on below line we are creating
    // a variable for the scope.
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.primary,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                color = Color.White
            )
        }
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = list[index],
                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun ReportScreenPreview() {
    ScoutingReportTheme {
        MainReportScreen()
    }
}
