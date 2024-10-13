package com.example.routes

import com.example.model.UserRequest
import com.example.services.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
// localhost:8080/users/login
fun Route.userRoutes(userService: UserService) {
    route("/users") {
        post("/register") {
          val request =  call.receive<UserRequest>()
            val response = userService.createUser(request)
            call.respond(HttpStatusCode.OK, response)
        }
        post("/login") {

        }
    }
}