package com.defaultcompany.testemulator.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material.MaterialTheme
import com.defaultcompany.testemulator.R
import com.defaultcompany.testemulator.presentation.theme.TestEmulatorTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.input.rotary.onRotaryScrollEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearApp()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WearApp() {
    var imageIndex by remember { mutableStateOf(0) }
    val images = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5,
        R.drawable.image6,
        R.drawable.image7,
        R.drawable.image8
    )
    val focusRequester = FocusRequester()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    TestEmulatorTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .focusRequester(focusRequester)
                .onFocusEvent {
                    if (it.isFocused) {
                        Log.d("WearApp", "Component is focused")
                    }
                }
                .onRotaryScrollEvent {
                    if (it.verticalScrollPixels > 0) {
                        imageIndex = (imageIndex + 1) % images.size
                        Log.d("WearApp", "Rotated Up: $imageIndex")
                    } else {
                        imageIndex = (imageIndex - 1 + images.size) % images.size
                        Log.d("WearApp", "Rotated Down: $imageIndex")
                    }
                    true
                }
                .focusable(),
            contentAlignment = Alignment.Center
        ) {
            val currentImage: Painter = painterResource(id = images[imageIndex])
            Image(painter = currentImage, contentDescription = null, modifier = Modifier.fillMaxSize())
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp()
}