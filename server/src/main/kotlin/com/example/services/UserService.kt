package com.example.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.model.UserRequest
import com.example.model.UserResponse
import com.example.plugins.jwtAudience
import com.example.plugins.jwtDomain
import com.example.plugins.jwtSecret
import com.example.repository.UserRepository
import java.security.MessageDigest
import java.util.Date

class UserService(private val userRepository: UserRepository) {

    suspend fun createUser(userRequest: UserRequest): UserResponse {
        val existingUser = userRepository.findUserByEmail(userRequest.email)
        if (existingUser != null) {
            throw IllegalArgumentException("User with email ${userRequest.email} already exists")
        }
        val user =
            userRepository.createUser(userRequest.copy(password = hashPassword(userRequest.password)))
        return UserResponse(user, createJwtToken(user.email))
    }

    fun createJwtToken(email: String): String {
        return JWT.create()
            .withAudience(jwtAudience)
            .withIssuer(jwtDomain)
            .withClaim("email", email)
            .withExpiresAt(Date(System.currentTimeMillis() + (24 * 60 * 60 * 60 * 1000L)))  // Token expiration time
            .sign(Algorithm.HMAC256(jwtSecret))
    }


    fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        return md.digest(password.toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }
    }
}