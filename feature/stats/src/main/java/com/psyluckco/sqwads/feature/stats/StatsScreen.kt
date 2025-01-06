/**
 * Created by developer on 01-01-2025.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.stats

import android.content.res.Configuration
import android.graphics.Color.toArgb
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.chart.copy
import com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import com.patrykandpatrick.vico.core.extension.getFieldValue
import com.psyluckco.sqwads.core.design.R
import com.psyluckco.sqwads.core.design.component.AppWrapper
import com.psyluckco.sqwads.core.design.component.HeaderWrapper
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme
import kotlin.random.Random

@Composable
internal fun StatsRoute(
    popup: () -> Unit,
    viewModel: StatsViewModel = hiltViewModel()
) {

}

@Composable
fun StatsScreen(
    uiState: StatsUiState,
    onEvent: (StatsEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    AppWrapper(modifier = modifier.background(color = MaterialTheme.colorScheme.background)) {
        StatsHeader()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            UserSentimentGraph()

        }

    }

}

@Composable
fun UserSentimentGraph(
    scores: List<Double> = listOf(0.25, -0.45, 0.32, 0.75),
    modifier: Modifier = Modifier
) {
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

        val threshold = lineComponent()


        Chart(
            chart = lineChart(
                lines = lineSpecs,
                decorations = listOf(
                    ThresholdLine(
                    0f
                    )
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

fun getRandomEntries() = List(4) { entryOf(it, Random.nextFloat() * 16f) }

@Composable
fun StatsHeader(modifier: Modifier = Modifier) {
    HeaderWrapper(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = modifier
                .fillMaxWidth()
                .height(90.dp)
        ) {
            Text(
                text = stringResource(R.string.stats_header),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun StatsScreenPreview() {
    SqwadsTheme {
        StatsScreen(uiState = StatsUiState(), onEvent = { })
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun StatsScreenDarkPreview() {
    SqwadsTheme {
        StatsScreen(uiState = StatsUiState(), onEvent = { })
    }
}