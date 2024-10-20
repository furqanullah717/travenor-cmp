package com.codewithfk.travenor.data.models.response

import com.codewithfk.travenor.data.models.User
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(val user: User, val token: String)
