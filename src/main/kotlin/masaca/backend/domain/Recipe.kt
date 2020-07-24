package masaca.backend.domain

data class Recipe(
    val id: Int? = null,
    var name: String,
    var ingredients: ArrayList<Ingredient>
)
