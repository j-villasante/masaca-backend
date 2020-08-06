package masaca.backend.domain.procedure

import com.fasterxml.jackson.annotation.*
import java.time.*

@JsonIgnoreProperties("id", "procedureId")
data class ProcedureTemperatureLog(
    val id: Int? = null,
    val procedureId: Int,
    val temperature: Int,
    val createdAt: OffsetDateTime? = null
)
