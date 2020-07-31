package masaca.backend.recipe

import masaca.backend.*
import masaca.backend.domain.*

object UseCase{
    fun createRecipe(request: CreateRecipeRequest): Recipe {
        val recipe = Recipe(
            name = request.name,
            target = request.target,
            starter = request.starter,
            levainHydration = request.levainHydration,
            ingredients = request.ingredients.map {
                Ingredient(
                    name = it.name,
                    amount = it.amount,
                    percentage = it.percentage,
                    cost = it.cost,
                    typeId = IngredientType.getByName(it.type).id
                )
            } as ArrayList<Ingredient>
        )
        return Database.doQuery {
            val repo = Repository(it)
            val recipeId = repo.createRecipe(recipe)
            it.commit()
            repo.getRecipe(recipeId)
        }
    }
    fun getRecipe(recipeId: Int): Recipe {
        return Database.doQuery {
            Repository(it).getRecipe(recipeId)
        }
    }
}
