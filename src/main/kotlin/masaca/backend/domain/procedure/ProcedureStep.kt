package masaca.backend.domain.procedure

enum class ProcedureStep(val id: Int) {
    LEVAIN(1),
    AUTOLYSIS(2),
    BULK_FERMENTATION(3),
    PRE_SHAPE(4),
    SHAPE(5),
    COUNTER_PROOF(6),
    COLD_PROOF(7),
    STEAM_BAKE(8),
    DRY_BAKE(9),
    OTHER(10);
}
