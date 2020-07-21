package masaca.backend.recipe

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import masaca.backend.*


fun Routing.recipe() {
    route("/recipe") {
        get("/") {
            call.respond(Database.executeQuery("select 2 as test").toString())
        }
    }
}
