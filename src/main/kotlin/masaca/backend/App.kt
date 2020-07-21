package masaca.backend

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import masaca.backend.recipe.*

fun main() {
    val server = embeddedServer(Netty, 8080) {
        routing {
            get("/health") {
                call.respondText("OK")
            }
            recipe()
        }
    }
    server.start(wait = true)
}
