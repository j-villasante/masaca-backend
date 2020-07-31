package masaca.backend

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import masaca.backend.recipe.*

fun Application.main() {
    Config.setup(environment.config)

    install(ContentNegotiation) {
        jackson {}
    }
    routing {
        get("/health") {
            call.respondText("OK")
        }
        recipe()
    }
}
