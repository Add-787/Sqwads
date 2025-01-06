/**
 * Created by developer on 01-01-2025.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.stats

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf
import com.psyluckco.sqwads.core.design.R
import com.psyluckco.sqwads.core.design.component.AppWrapper
import com.psyluckco.sqwads.core.design.component.HeaderWrapper
import com.psyluckco.sqwads.core.design.component.SqwadsProgressLoadingDialog
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme
import com.psyluckco.sqwads.core.model.LoadingState
import com.psyluckco.sqwads.core.model.Room
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@Composable
internal fun StatsRoute(
    popUp: () -> Unit,
    viewModel: StatsViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.initialize()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StatsScreen(
        uiState = uiState,
        onEvent = {},
        popUp = popUp
    )

    if(uiState.loadingState is LoadingState.Loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            SqwadsProgressLoadingDialog(id = R.string.stats_screen_loading_dialog)
        }
    }

}

@Composable
fun StatsScreen(
    uiState: StatsUiState,
    onEvent: (StatsEvent) -> Unit,
    popUp: () -> Unit,
    modifier: Modifier = Modifier,
) {

    AppWrapper(modifier = modifier.background(color = MaterialTheme.colorScheme.background)) {
        StatsHeader(
            goBack = popUp
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            if(uiState.sentimentScores.isNotEmpty()) {
                UserSentimentGraph(
                    scores = uiState.sentimentScores
                )
            }



            RecommendedRoomsSection(rooms = uiState.recommendedRooms)

        }

    }

}

@Composable
fun UserSentimentGraph(
    scores: List<Double> = listOf(0.25, -0.45, 0.32, 0.75),
    modifier: Modifier = Modifier
) {
    Column {

        Text(
            text = stringResource(id = R.string.current_mood_graph_header),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = modifier
            .wrapContentWidth()
            .height(300.dp)
            .padding(all = 8.dp)
        ) {

            val floatEntries = scores.mapIndexed { index, d ->
                entryOf(index,d)
            }

            val lineSpecs = scores.map { s ->
                LineChart.LineSpec(
                    lineColor = if (s < 0.0) MaterialTheme.colorScheme.error.toArgb() else MaterialTheme.colorScheme.primary.toArgb(),
                    lineBackgroundShader = DynamicShaders.fromBrush(
                        verticalGradient(
                            listOf(
                                if (s < 0.0) MaterialTheme.colorScheme.error.copy(0.5f) else MaterialTheme.colorScheme.primary.copy(
                                    0.5f
                                ),
                                if (s < 0.0) MaterialTheme.colorScheme.error.copy(0f) else MaterialTheme.colorScheme.primary.copy(
                                    0f
                                )
                            )
                        )
                    )
                )
            }

            val thresholdLine = lineComponent(
                color = MaterialTheme.colorScheme.onBackground
            )


            Chart(
                chart = lineChart(
                    lines = lineSpecs,
                    decorations = listOf(
                        ThresholdLine(
                            0f,
                            lineComponent = thresholdLine
                        )
                    ),
                    axisValuesOverrider = AxisValuesOverrider.fixed(
                        minY = (-1.0).toFloat(),
                        maxY = (1.0).toFloat()
                    )
                ),
                chartModelProducer = ChartEntryModelProducer(floatEntries),
                startAxis = rememberStartAxis(
                    guideline = null,
                ),
                bottomAxis = rememberBottomAxis(
                    label = null,
                    guideline = null
                ),
                horizontalLayout = HorizontalLayout.FullWidth()
            )

        }

    }

    
}


@Composable
fun StatsHeader(
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    HeaderWrapper(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = goBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(60.dp),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(11.dp))

            Text(
                text = "Statistics",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

        }
    }

}

@Composable
fun RecommendedRoomsSection(
    rooms: List<Room>,
    modifier: Modifier = Modifier
) {
    Column {

        Text(
            text = stringResource(id = R.string.recommended_rooms_section_header),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn (
            modifier = Modifier.fillMaxWidth(),
        ){
            items(rooms){
                RecommendedRoomCard(room = it)
            }
        }

    }
}

@Composable
fun RecommendedRoomCard(
    room: Room,
    modifier: Modifier = Modifier,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxSize()

        ) {
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .clip(RoundedCornerShape(size = 10.dp))
                    .background(MaterialTheme.colorScheme.onSurfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp),
                    tint = MaterialTheme.colorScheme.background,
                    )
            }

            Spacer(modifier = modifier.width(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(text = room.name, fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(0.75f)),
                    contentAlignment = Alignment.Center
                ) {
                    val formattedScore = String.format("%.2f", room.score.times(100))
                    Text(text = formattedScore, style = MaterialTheme.typography.labelSmall)

                }

            }
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.surfaceContainerLow
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StatsScreenPreview() {
    val fakeRooms = listOf(
        Room(
            id = "1",
            name = "test_room_1",
            members = listOf("user1","user2"),
            createdAt = LocalDateTime.now(),
            createdBy = "user1",
            score = 0.98
        ),
        Room(
            id = "2",
            name = "test_room_2",
            members = listOf("user3","user4","user5"),
            createdAt = LocalDateTime.now(),
            createdBy = "user3",
            score = 0.96
        ),
        Room(
            id = "3",
            name = "test_room_3",
            members = listOf("user6","user7"),
            createdAt = LocalDateTime.now(),
            createdBy = "user6",
            score = 0.91
        )
    )
    
    SqwadsTheme {
        StatsScreen(uiState = StatsUiState(
            sentimentScores = listOf(0.23, 0.56,-0.24, -0.45, 0.10,0.89),
            recommendedRooms = fakeRooms
        ), onEvent = { }, popUp = { } )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun StatsScreenDarkPreview() {

    val fakeRooms = listOf(
        Room(
            id = "1",
            name = "test_room_1",
            members = listOf("user1","user2"),
            createdAt = LocalDateTime.now(),
            createdBy = "user1",
            score = 0.98
        ),
        Room(
            id = "2",
            name = "test_room_2",
            members = listOf("user3","user4","user5"),
            createdAt = LocalDateTime.now(),
            createdBy = "user3",
            score = 0.96
        ),
        Room(
            id = "3",
            name = "test_room_3",
            members = listOf("user6","user7"),
            createdAt = LocalDateTime.now(),
            createdBy = "user6",
            score = 0.91
        )
    )

    SqwadsTheme {
        StatsScreen(uiState = StatsUiState(
            sentimentScores = listOf(0.23, 0.56,-0.24, -0.45, 0.10,0.89),
            recommendedRooms = fakeRooms
        ), onEvent = { }, popUp = { } )
    }
}