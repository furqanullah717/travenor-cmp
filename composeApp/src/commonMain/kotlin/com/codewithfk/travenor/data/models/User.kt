package com.codewithfk.travenor.data.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val name: String, val email: String, val id: Long)
