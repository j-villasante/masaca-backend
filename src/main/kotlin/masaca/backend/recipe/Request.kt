package masaca.backend.recipe

import java.math.*

data class CreateRecipeIngredientRequest(
    val name: String,
    val amount: Int,
    val percentage: BigDecimal,
    val cost: BigDecimal,
    val type: String
)

data class CreateRecipeRequest(
    val name: String,
    val target: Int,
    val starter: Int?,
    val levainHydration: Int?,
    val ingredients: ArrayList<CreateRecipeIngredientRequest>
)
