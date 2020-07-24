package masaca.backend.domain

enum class IngredientType(val id: Int) {
    FLOUR(1),
    WET(2),
    DRY(3),
    LEVAIN(4);

    companion object {
        fun getById(id: Int): IngredientType {
            return values().find { it.id == id } ?: throw Exception("IngredientType not found")
        }
        fun getByName(name: String): IngredientType {
            return values().find { it.name == name } ?: throw Exception("IngredientType not found")
        }
    }
}
