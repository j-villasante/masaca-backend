package masaca.backend.domain.recipe

import com.fasterxml.jackson.annotation.*
import java.math.*

@JsonIgnoreProperties("id", "typeId", "recipeId")
data class Ingredient (
    val id: Int? = null,
    var name: String,
    var amount: Int,
    var percentage: BigDecimal,
    var cost: BigDecimal,
    var type: IngredientType,
    val recipeId: Int? = null
)
