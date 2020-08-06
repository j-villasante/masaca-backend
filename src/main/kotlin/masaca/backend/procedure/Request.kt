package masaca.backend.procedure

data class CreateProcedureRequest(
    val recipeId: Int
)

data class CreateStepLogRequest(
    var procedureId: Int = -1,
    val step: String,
    val stepOther: String?
)

data class CreateTemperatureLog(
    var procedureId: Int = -1,
    val temperature: Int
)
