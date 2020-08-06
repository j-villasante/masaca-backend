package masaca.backend.domain.procedure

import com.fasterxml.jackson.annotation.*
import java.time.*

@JsonIgnoreProperties("id", "recipeId")
data class Procedure (
    val id: Int? = null,
    val recipeId: Int,
    val createdAt: OffsetDateTime? = null,
    val stepLogs: ArrayList<ProcedureStepLog> = ArrayList(),
    val temperatureLogs: ArrayList<ProcedureTemperatureLog> = ArrayList()
)
