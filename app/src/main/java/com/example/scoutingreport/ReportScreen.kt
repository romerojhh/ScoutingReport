package com.example.scoutingreport

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.scoutingreport.data.Report
import com.example.scoutingreport.ui.report_list.ReportItem
import com.example.scoutingreport.ui.theme.ScoutingReportTheme
import com.google.accompanist.pager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ReportScreen(navController: NavController) {
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

        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Navigate to ScoutingEdit screen
                navController.navigate(Screen.ScoutingEdit.route)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
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

    // MOCK DATA FOR REPORT LIST
    val mockReport = Report("Field Name", "Pest Type", "Pest", 1, "notes", "recommendations")
    val report: MutableList<Report> = mutableListOf()
    for (i in 1 .. 10) {
        report.add(mockReport)
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
        if (pages[page] == "Shared") {
            SharedTabContent(report)
        } else {
            MatrixText()
        }
    }
}

@Composable
fun SharedTabContent(
    reports: List<Report>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        this.items(reports) { report ->
            ReportItem(report = report)
        }
    }
}

@Preview
@Composable
fun ReportScreenPreview() {
    val navController = rememberNavController()
    ScoutingReportTheme {
        ReportScreen(navController = navController)
    }
}
