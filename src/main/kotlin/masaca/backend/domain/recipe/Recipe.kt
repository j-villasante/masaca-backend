package masaca.backend.domain.recipe

import java.time.*

data class Recipe(
    val id: Int? = null,
    var name: String,
    var target: Int,
    var starter: Int? = null,
    var levainHydration: Int? = null,
    val created_at: OffsetDateTime? = null,
    var ingredients: ArrayList<Ingredient>
)
