package masaca.backend.domain

data class Recipe(
    val id: Int? = null,
    var name: String,
    var target: Int,
    var starter: Int?,
    var levainHydration: Int?,
    var ingredients: ArrayList<Ingredient>
)
