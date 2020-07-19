package masaca.backend

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import masaca.backend.recipe.recipe
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table

fun main(args: Array<String>) {
    Database.connect("jdbc:postgresql://localhost/masaca", driver = "org.postgresql.Driver", user = "masaca", password = "masaca")
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
