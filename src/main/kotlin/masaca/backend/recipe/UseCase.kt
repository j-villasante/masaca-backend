package masaca.backend.recipe

import masaca.backend.*
import masaca.backend.domain.recipe.*

object UseCase {
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
                    type = IngredientType.valueOf(it.type)
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

    fun listRecipes(): ArrayList<Recipe> {
        return Database.doQuery {
            Repository(it).listRecipe()
        }
    }
}
