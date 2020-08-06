package masaca.backend.procedure

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import masaca.backend.error.*

fun Routing.procedure() {
    route("/procedure") {
        post("/") {
            val procedure = UseCase.createProcedure(call.receive())
            call.respond(procedure)
        }
        get("/{procedure_id}") {
            val procedureId = call.parameters["procedure_id"]
                ?: throw MasacaInvalidParameterError("No valid procedure id was given")
            call.respond(UseCase.getProcedure(procedureId.toInt()))
        }
        post("/{procedure_id}/log/step") {
            val procedureId = call.parameters["procedure_id"]
                ?: throw MasacaInvalidParameterError("No valid procedure id was given")
            val request = call.receive<CreateStepLogRequest>()
            request.procedureId = procedureId.toInt()
            call.respond(UseCase.createStepLog(request))
        }
        post("/{procedure_id}/log/temperature") {
            val procedureId = call.parameters["procedure_id"]
                ?: throw MasacaInvalidParameterError("No valid procedure id was given")
            val request = call.receive<CreateTemperatureLog>()
            request.procedureId = procedureId.toInt()
            call.respond(UseCase.createTemperatureLog(request))
        }
    }
}
