package com.codewithfk.travenor.data.models.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val email: String, val password: String)
