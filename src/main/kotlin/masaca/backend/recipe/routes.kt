package masaca.backend.recipe

import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.response.respondText

fun Routing.recipe() {
    route("/recipe") {
        get("/") {
            call.respondText("hello")
        }
    }
}
