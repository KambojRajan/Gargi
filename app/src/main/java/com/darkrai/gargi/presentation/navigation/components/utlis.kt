package com.darkrai.gargi.presentation.navigation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Composable
fun DonatePlant(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "potted_plant",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(13.5f, 33.958f)
                horizontalLineToRelative(13f)
                lineToRelative(1.958f, -7.666f)
                horizontalLineTo(11.583f)
                lineToRelative(1.917f, 7.666f)
                close()
                moveToRelative(0f, 2.625f)
                quadToRelative(-0.875f, 0f, -1.583f, -0.541f)
                quadToRelative(-0.709f, -0.542f, -0.959f, -1.417f)
                lineToRelative(-2.083f, -8.333f)
                horizontalLineToRelative(22.25f)
                lineToRelative(-2.083f, 8.333f)
                quadToRelative(-0.25f, 0.875f, -0.959f, 1.417f)
                quadToRelative(-0.708f, 0.541f, -1.583f, 0.541f)
                close()
                moveTo(7.708f, 23.667f)
                horizontalLineToRelative(24.584f)
                verticalLineToRelative(-4.042f)
                horizontalLineTo(7.708f)
                verticalLineToRelative(4.042f)
                close()
                moveTo(11.125f, 3.5f)
                quadToRelative(3.458f, 0.542f, 6.083f, 3.375f)
                reflectiveQuadTo(20f, 13.208f)
                quadToRelative(0.208f, -3.416f, 2.833f, -6.27f)
                quadToRelative(2.625f, -2.855f, 6.042f, -3.438f)
                quadToRelative(0.417f, -0.083f, 0.687f, 0.208f)
                quadToRelative(0.271f, 0.292f, 0.188f, 0.667f)
                quadToRelative(-0.458f, 3.125f, -2.917f, 5.604f)
                quadToRelative(-2.458f, 2.479f, -5.5f, 3.063f)
                verticalLineTo(17f)
                horizontalLineToRelative(12.292f)
                quadToRelative(0.542f, 0f, 0.937f, 0.396f)
                quadToRelative(0.396f, 0.396f, 0.396f, 0.937f)
                verticalLineToRelative(5.334f)
                quadToRelative(0f, 1.125f, -0.791f, 1.875f)
                quadToRelative(-0.792f, 0.75f, -1.875f, 0.75f)
                horizontalLineTo(7.708f)
                quadToRelative(-1.083f, 0f, -1.854f, -0.75f)
                quadToRelative(-0.771f, -0.75f, -0.771f, -1.875f)
                verticalLineToRelative(-5.334f)
                quadToRelative(0f, -0.541f, 0.375f, -0.937f)
                reflectiveQuadTo(6.375f, 17f)
                horizontalLineToRelative(12.333f)
                verticalLineToRelative(-3.958f)
                quadToRelative(-3.083f, -0.584f, -5.52f, -3.063f)
                quadTo(10.75f, 7.5f, 10.25f, 4.375f)
                quadToRelative(-0.083f, -0.375f, 0.188f, -0.667f)
                quadToRelative(0.27f, -0.291f, 0.687f, -0.208f)
                close()
            }
        }.build()
    }
}

@Composable
fun DashboardIcon(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "space_dashboard",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(7.875f, 34.75f)
                quadToRelative(-1.042f, 0f, -1.833f, -0.792f)
                quadToRelative(-0.792f, -0.791f, -0.792f, -1.833f)
                verticalLineTo(7.875f)
                quadToRelative(0f, -1.042f, 0.792f, -1.833f)
                quadToRelative(0.791f, -0.792f, 1.833f, -0.792f)
                horizontalLineToRelative(24.25f)
                quadToRelative(1.042f, 0f, 1.833f, 0.792f)
                quadToRelative(0.792f, 0.791f, 0.792f, 1.833f)
                verticalLineToRelative(24.25f)
                quadToRelative(0f, 1.042f, -0.792f, 1.833f)
                quadToRelative(-0.791f, 0.792f, -1.833f, 0.792f)
                close()
                moveToRelative(0f, -2.625f)
                horizontalLineToRelative(10.833f)
                verticalLineTo(7.875f)
                horizontalLineTo(7.875f)
                verticalLineToRelative(24.25f)
                close()
                moveToRelative(13.458f, 0f)
                horizontalLineToRelative(10.792f)
                verticalLineTo(19.958f)
                horizontalLineTo(21.333f)
                verticalLineToRelative(12.167f)
                close()
                moveToRelative(0f, -14.792f)
                horizontalLineToRelative(10.792f)
                verticalLineTo(7.875f)
                horizontalLineTo(21.333f)
                verticalLineToRelative(9.458f)
                close()
            }
        }.build()
    }
}