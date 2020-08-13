package masaca.backend.domain.procedure

enum class ProcedureStep(val id: Int) {
    LEVAIN(1),
    AUTOLYSIS(2),
    BULK_FERMENTATION(3),
    STRETCH_AND_FOLD(4),
    PRE_SHAPE(5),
    SHAPE(6),
    COUNTER_PROOF(7),
    COLD_PROOF(8),
    STEAM_BAKE(9),
    DRY_BAKE(10),
    OTHER(11);
}
