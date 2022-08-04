package com.example.scoutingreport.ui.report_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scoutingreport.data.Report
import com.example.scoutingreport.ui.theme.ScoutingReportTheme

@Composable
fun ReportItem(
    report: Report
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            Row (
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .height(IntrinsicSize.Min)
                    .wrapContentSize(Alignment.Center)
            ) {
                Icon(
                    imageVector = Icons.Default.Agriculture,
                    contentDescription = null,
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .scale(2f)
                )

                // Center Description
                CenterDescription(Modifier.weight(2f), report)

                Text(
                    text = "Date/Time",
                    fontSize = 12.sp,
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                        .wrapContentHeight(Alignment.CenterVertically)
                )
            }

            Divider(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun CenterDescription(
    modifier : Modifier = Modifier,
    report: Report
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = report.fieldName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Text(
            text = "${report.pestType} " +
                (0x2022.toChar()).toString() +
                " ${report.pestName}",
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Location: Lat/Long",
            fontSize = 14.sp,
        )
    }
}

@Preview
@Composable
fun MainPreview() {
    val mockReport = Report("Field Name", "Pest Type", "Pest", 1, "notes", "recommendations")
    ScoutingReportTheme {
        ReportItem(mockReport)
    }
}