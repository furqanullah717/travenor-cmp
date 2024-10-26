package com.example.plugins

import com.example.repository.UserRepository
import com.example.routes.userRoutes
import com.example.services.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(StatusPages) {
        exception<IllegalArgumentException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to (cause.message ?: "Invalid request"))
            )
        }

        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to (cause.message ?: "Invalid request"))
            )
        }
    }
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        authenticate("auth-jwt") {
            get("/authenticated") {
                call.respondText("Authenticated")
            }
        }
        userRoutes(UserService(UserRepository()))
    }
}
