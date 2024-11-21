/**
 * Created by developer on 05-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.design.component

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.psyluckco.sqwads.core.design.R
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme

@Composable
fun SqwadsProgressIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .zIndex(2f)
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimaryContainer)
    }
}

@Composable
fun SqwadsProgressLoadingDialog(
    modifier: Modifier = Modifier,
    @StringRes id: Int
) {
    Card(
        modifier = modifier
            .width(600.dp)
            .height(300.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 19.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {

        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(70.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(stringResource(id = id), style = MaterialTheme.typography.displaySmall)

        }

    }
    
    
}

//@Composable
//fun LottieAnimationPlaceholder(
//    @RawRes rawRes: Int,
//    modifier: Modifier = Modifier,
//    backgroundColor: Color = MaterialTheme.colorScheme.background
//) {
//    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(rawRes))
//    val progress by animateLottieCompositionAsState(
//        composition,
//        iterations = LottieConstants.IterateForever,
//        reverseOnRepeat = true
//    )
//    Box(
//        modifier = modifier
//            .fillMaxSize()
//            .background(backgroundColor),
//        contentAlignment = Alignment.Center
//    ) {
//        LottieAnimation(
//            composition = composition,
//            progress = { progress },
//            modifier = Modifier.fillMaxSize(0.50f)
//        )
//    }
//}


@Preview
@Composable
private fun SqwadsProgressIndicatorPreview() {
    SqwadsTheme {
        SqwadsProgressIndicator()
    }

}

@Preview
@Composable
private fun SqwadsProgressLoadingDialogReview() {

    SqwadsTheme {
        SqwadsProgressLoadingDialog(id = R.string.placeholder)
    }


}