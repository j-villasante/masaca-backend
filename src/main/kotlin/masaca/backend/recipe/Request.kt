package masaca.backend.recipe

import java.math.*

data class CreateRecipeIngredientRequest(
    val name: String,
    val amount: BigDecimal,
    val percentage: BigDecimal,
    val cost: BigDecimal,
    val type: String
)

data class CreateRecipeRequest(
    val name: String,
    val ingredients: ArrayList<CreateRecipeIngredientRequest>
)
