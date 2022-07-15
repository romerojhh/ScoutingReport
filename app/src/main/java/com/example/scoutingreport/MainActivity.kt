package com.example.scoutingreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
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
fun DropDownMenu(
    list: List<String> = List(15) { "$it" },
    label: String,
    isEnabled: Boolean = true,
    tvValue: String = "",
    onTvChange: ((String) -> Unit)? = null
) {
    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }

    // Create a string value to store the selected TextField value
    var mSelectedText by remember { mutableStateOf("") }

    // To store the width of the TextField
    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {

        // Create an Outlined Text Field
        // with icon and not expanded
        OutlinedTextField(
            placeholder = {Text("Please choose")},
            singleLine = true,
            enabled = isEnabled,
            readOnly = true,
            value = mSelectedText,
            onValueChange = { mSelectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    mTextFieldSize = coordinates.size.toSize()
                },
            label = {Text(label)},
            trailingIcon = {
                Icon(icon,null,
                    Modifier.clickable { if (isEnabled) mExpanded = !mExpanded })
            }
        )

        // Create a drop-down menu with list of items,
        // when clicked, set the Text Field text as an item selected
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
                .fillMaxHeight(0.5f)
        ) {
            list.forEach { label ->
                DropdownMenuItem(onClick = {
                    mSelectedText = label
                    mExpanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
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

        content = { ScaffoldContent() }
    )
}

@Composable
fun ScaffoldContent() {
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        DropDownMenu(label = "Field Name")
        DropDownMenu(label = "Pest Type")
        DropDownMenu(label = "Pest Name", isEnabled = false)
        DropDownMenu(label = "Severity", isEnabled = false)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ScoutingReportTheme {
        MainPreview()
    }
}