package com.darkrai.gargi.presentation.plantDescription.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Carousel(imageList:List<String>) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = {
            imageList.size
        }
    )

    HorizontalPager(state = pagerState) {index->
        val offset = (pagerState.currentPage - index) +  pagerState.currentPageOffsetFraction
        val imgSize by animateFloatAsState(
            targetValue = if(offset != 0.0f) 0.85f else 1f,
            animationSpec = tween(durationMillis = 200),
            label = ""
        )
        val matrix = remember {
            ColorMatrix()
        }
        LaunchedEffect(key1 = imgSize){
            if(offset != 0.0f){
                matrix.setToSaturation(0f)
            }else{
                matrix.setToSaturation(1f)
            }
        }

        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .graphicsLayer {
                    scaleX = imgSize
                    scaleY = imgSize
                },

            model = ImageRequest.Builder(LocalContext.current).data(imageList[index]).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.colorMatrix(matrix)
        )
    }
}