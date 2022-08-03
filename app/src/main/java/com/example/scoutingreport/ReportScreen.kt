package com.example.scoutingreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scoutingreport.ui.theme.ScoutingReportTheme
import com.google.accompanist.pager.*
import kotlinx.coroutines.CoroutineScope
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
    val pagerState = rememberPagerState()
    val pages = remember { listOf("Shared", "Saved") }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),

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
        }

    // Scaffold content
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ScaffoldContent(
                pagerState = pagerState,
                pages = pages,
                coroutineScope = coroutineScope
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ScaffoldContent(
    pagerState: PagerState,
    pages: List<String>,
    coroutineScope: CoroutineScope
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        pages.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                modifier = Modifier.padding(vertical = 12.dp),
                onClick = {
                    // Animate to the selected page when clicked
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            ) {
                Text(
                    text = title,
                    color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                )
            }
        }
    }

    // Content
    HorizontalPager(
        count = pages.size,
        state = pagerState,
        // Add 16.dp padding to 'center' the pages
        contentPadding = PaddingValues(16.dp),
        itemSpacing = 15.dp,
        modifier = Modifier
            .fillMaxWidth()
    ) { page ->
        // Our content for each page
        Card {
            Box(Modifier.fillMaxSize()) {
                Text(
                    text = "Page: ${pages[page]}",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun SharedTabContent() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

    }
}

@Preview
@Composable
fun ReportScreenPreview() {
    ScoutingReportTheme {
        MainReportScreen()
    }
}
