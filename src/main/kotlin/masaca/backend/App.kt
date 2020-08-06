package masaca.backend

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import masaca.backend.error.*
import masaca.backend.procedure.*
import masaca.backend.recipe.*

fun Application.main() {
    Config.setup(environment.config)

    install(ContentNegotiation) {
        jackson {
            registerModule(JavaTimeModule())
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        }
    }

    install(StatusPages) {
        exception<MasacaError> {
            call.respond(it.httpStatusCode, it.response)
        }
    }

    routing {
        get("/health") {
            call.respondText("OK")
        }
        recipe()
        procedure()
    }
}
