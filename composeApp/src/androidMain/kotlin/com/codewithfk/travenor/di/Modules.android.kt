package com.codewithfk.travenor.di

import com.codewithfk.travenor.cache.dataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        dataStore(get())
    }
}