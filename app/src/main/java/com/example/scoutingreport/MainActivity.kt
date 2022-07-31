package com.example.scoutingreport

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.util.lerp
import com.example.scoutingreport.ui.theme.ScoutingReportTheme
import com.google.accompanist.pager.*
import kotlin.math.absoluteValue

// TODO: Use extended icons so that we don't have to import camera icon using
// implementation "androidx.compose.material:material-icons-extended:$compose_version"

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
    onTvChange: (String) -> Unit
) {
    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }

    // To store the width of the TextField
    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Box {

        // Create an Outlined Text Field
        // with icon and not expanded
        OutlinedTextField(
            placeholder = { Text("Please choose") },
            singleLine = true,
            enabled = isEnabled,
            readOnly = true,
            value = tvValue,
            onValueChange = onTvChange,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    mTextFieldSize = coordinates.size.toSize()
                },
            label = { Text(label) },
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
                    onTvChange.invoke(label)
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
    val context = LocalContext.current

    Column (
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .clickable {
                if (title == "Reports") {
                    context.startActivity(Intent(context, ReportScreen::class.java))
                    //startActivity(context, Intent(context, ReportScreen::class.java))
                }

                Toast
                    .makeText(context, "$title pressed", Toast.LENGTH_SHORT)
                    .show()
            }
    ) {
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
    val modifier = Modifier.padding(horizontal = 30.dp)
    var allEnabled by remember { mutableStateOf(false) }

    Scaffold(

        topBar =
        {
            TopAppBar(
                title = { Text("Scouting Report") },
                navigationIcon = {
                    IconButton(
                        onClick = {/*TODO: Give behaviour on prev click*/ }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
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

        content =
        { pad ->
            ScaffoldContent(
                bottomPadding = pad.calculateBottomPadding(),
                allEnabled = allEnabled
            ) {
                allEnabled = it
            }
        },

        floatingActionButtonPosition = FabPosition.Center,

        floatingActionButton = {
            if (allEnabled) {
                Button(
                    modifier = modifier
                        .fillMaxWidth(),
                    onClick = { /*TODO*/ },
                    enabled = allEnabled
                ) {
                    Text(text = "Save Report")
                }
            }
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PestImages() {
    // Get images from the list that is storing the pest images from user
    // display the images only if the list is not empty

    val images = listOf(R.drawable.pest1, R.drawable.pest2, R.drawable.pest3)
    val list by remember { mutableStateOf(images) }
    val contentPadding = PaddingValues(32.dp)
    val pagerState = rememberPagerState()

    // Display 10 items
    HorizontalPager(
        state = pagerState,
        count = list.size,
        itemSpacing = 10.dp,
        contentPadding = contentPadding,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) { page ->
        // Our page content
        Image(
            painter = painterResource(id = list[page]),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(5.dp))
                .graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                    // We animate the scaleX + scaleY, between 85% and 100%
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        )
    }

    HorizontalPagerIndicator(
        pagerState = pagerState,
        modifier = Modifier
            .wrapContentWidth(CenterHorizontally)
            .padding(16.dp),
    )
}

@Composable
fun ScaffoldContent(
    bottomPadding: Dp,
    allEnabled: Boolean,
    onAllEnabled: (Boolean) -> Unit
) {
    // to enable vertical scrolling
    val scrollState = rememberScrollState()

    var fieldName by remember { mutableStateOf("") }
    var pestType by remember { mutableStateOf("") }
    var pestName by remember { mutableStateOf("") }
    var severity by remember { mutableStateOf("") }
    var enable by remember { mutableStateOf(false) }
    var noteContent by remember { mutableStateOf("") }
    var recommendationContent by remember { mutableStateOf("") }

    // pest name and Severity dropdown menu will only be activated when
    // user chosen option from field name and pest type
    enable = fieldName.isNotEmpty() && pestType.isNotEmpty()

    // User can save report when at least the first 4 dropdown menu is filled
    if (enable && pestName.isNotEmpty() && severity.isNotEmpty() && !allEnabled) {
        onAllEnabled.invoke(true)
    }

    Column (
        modifier = Modifier
            .padding(top = 16.dp, bottom = bottomPadding)
            .padding(horizontal = 20.dp)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        DropDownMenu(label = "Field Name", tvValue = fieldName) { fieldName = it }
        DropDownMenu(label = "Pest Type", tvValue = pestType) { pestType = it }
        DropDownMenu(label = "Pest Name", tvValue = pestName, isEnabled = enable) { pestName = it }
        DropDownMenu(label = "Severity", tvValue = severity, isEnabled = enable) { severity = it }
        Divider(modifier = Modifier.fillMaxWidth())

        // Add photo button
        Button (
            modifier = Modifier.height(40.dp),
            onClick = { /*TODO*/ },
        ) {
            Icon(
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = null
            )
            Spacer(Modifier.width(5.dp))
            Text(text = "ADD PHOTO")
        }

        PestImages()

        Divider(modifier = Modifier.fillMaxWidth())

        NoteSpace(label = "Notes", noteContent) { noteContent = it }
        NoteSpace(label = "Recommendation", recommendationContent) { recommendationContent = it }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun NoteSpace(
    // TODO: Do we need state hoisting here???
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text("Add your notes here...") },
        maxLines = 6,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Preview(showBackground = true, heightDp = 1500)
@Composable
fun DefaultPreview() {
    ScoutingReportTheme {
        MainPreview()
    }
}