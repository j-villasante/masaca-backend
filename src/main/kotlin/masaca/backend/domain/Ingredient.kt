package masaca.backend.domain

import com.fasterxml.jackson.annotation.*
import java.math.*

@JsonIgnoreProperties("id", "typeId", "recipeId")
data class Ingredient (
    val id: Int? = null,
    var name: String,
    var amount: Int,
    var percentage: BigDecimal,
    var cost: BigDecimal,
    var typeId: Int,
    val recipeId: Int? = null
) {
    var type: IngredientType
        get() = IngredientType.getById(this.typeId)
        set(value) {
            this.typeId = value.id
        }
}
