package masaca.backend.procedure

import masaca.backend.*
import masaca.backend.domain.procedure.*
import masaca.backend.error.*

object UseCase {
    fun createProcedure(request: CreateProcedureRequest): Procedure {
        return Database.doQuery {
            val repository = Repository(it)
            val procedureId = repository.createProcedure(
                Procedure(
                    recipeId = request.recipeId
                )
            )
            it.commit()
            repository.getProcedure(procedureId)
        }
    }

    fun getProcedure(procedureId: Int): Procedure {
        return Database.doQuery {
            Repository(it).getProcedure(procedureId)
        }
    }

    fun createStepLog(request: CreateStepLogRequest): Procedure {
        return Database.doQuery {
            val r = Repository(it)
            val step = ProcedureStep.valueOf(request.step)

            if (step != ProcedureStep.OTHER) {
                val procedure = r.getProcedure(request.procedureId)
                if (procedure.stepLogs.any { sl -> sl.step == step })
                    throw MasacaValidationError("Step already exists")
            }

            r.createStepLog(
                ProcedureStepLog(
                    procedureId = request.procedureId,
                    step = step,
                    stepOther =
                    if (step == ProcedureStep.OTHER) request.stepOther
                    else null
                )
            )
            it.commit()
            r.getProcedure(request.procedureId)
        }
    }

    fun createTemperatureLog(request: CreateTemperatureLog): Procedure {
        return Database.doQuery {
            val r = Repository(it)
            r.createTemperatureLog(
                ProcedureTemperatureLog(
                    procedureId = request.procedureId,
                    temperature = request.temperature
                )
            )
            it.commit()
            r.getProcedure(request.procedureId)
        }
    }
}
