package masaca.backend.domain.procedure

import com.fasterxml.jackson.annotation.*
import java.time.*

@JsonIgnoreProperties("id", "procedureId")
data class ProcedureStepLog (
    val id: Int? = null,
    val procedureId: Int,
    val step: ProcedureStep,
    val stepOther: String?,
    val createdAt: OffsetDateTime? = null
)
