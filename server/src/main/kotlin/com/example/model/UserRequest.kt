package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(val email: String, val password: String, val name: String)