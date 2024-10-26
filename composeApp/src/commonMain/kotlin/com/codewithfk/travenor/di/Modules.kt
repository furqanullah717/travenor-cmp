package com.codewithfk.travenor.di

import com.codewithfk.travenor.cache.TravenorSession
import com.codewithfk.travenor.data.NetworkService
import com.codewithfk.travenor.ui.feature.login.LoginViewModel
import com.codewithfk.travenor.ui.feature.register.RegisterViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {

    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println("BackEndHandler: $message")
                    }
                }
            }
        }
    }

    single {
        NetworkService(get())
    }
    viewModel {
        RegisterViewModel(get(),get())
    }
    viewModel {
        LoginViewModel(get(),get())
    }
    single<TravenorSession> {
        TravenorSession(get())
    }
}