package com.codewithfk.travenor.cache

import android.content.Context

fun dataStore(context: Context) = createDataStore {
    context.dataDir.resolve(dataStoreFileName).absolutePath
}