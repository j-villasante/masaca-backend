package masaca.backend.domain

data class Recipe(
    val id: Int? = null,
    var name: String,
    var target: Int,
    var starter: Int? = null,
    var levainHydration: Int? = null,
    var ingredients: ArrayList<Ingredient>
)
