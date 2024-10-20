package com.codewithfk.travenor

import androidx.compose.ui.window.ComposeUIViewController
import com.codewithfk.travenor.di.initKoin
import com.codewithfk.travenor.ui.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }