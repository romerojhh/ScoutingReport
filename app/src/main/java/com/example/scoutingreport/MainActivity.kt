package com.example.scoutingreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.scoutingreport.ui.theme.ScoutingReportTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScoutingReportTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainPreview()
                }
            }
        }
    }
}

@Composable
fun DropDownMenu(list: List<String> = listOf("Romero"), label: String) {
    OutlinedTextField(
        value = list[0],
        onValueChange = {},
        label = { Text(text = label)}
    )
}

// TODO: Make navigate icon clickable
@Composable
fun NavigateIcon(icon: ImageVector, title: String) {
    Column (modifier = Modifier.width(IntrinsicSize.Max)) {
        Icon(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(CenterHorizontally),
            imageVector = icon,
            contentDescription = null
        )
        Text(text = title, fontSize = 12.sp, style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun MainPreview() {
    Scaffold(
        topBar = { TopAppBar(
                title = { Text("Scouting Report") },
                navigationIcon = {
                    IconButton(
                        onClick = { /*TODO: Give behaviour on prev click*/ }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                backgroundColor = MaterialTheme.colors.primary
        ) },
        bottomBar = { BottomAppBar(
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                NavigateIcon(icon = Icons.Default.LocationOn, title = "Scout")
                NavigateIcon(icon = Icons.Default.List, title = "Reports")
            }
        }},

        content = {
            Column() {
                DropDownMenu(label = "Label")
                DropDownMenu(label = "Label")
                DropDownMenu(label = "Label")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ScoutingReportTheme {
        MainPreview()
    }
}