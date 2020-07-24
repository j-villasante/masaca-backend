package masaca.backend.recipe

import masaca.backend.domain.*
import java.sql.*

class Repository(private val connection: Connection) {
    fun createRecipe(recipe: Recipe): Int {
        val recipeId = connection.prepareStatement(
            "INSERT INTO masaca.recipe (name) VALUES (?) RETURNING id"
        ).apply {
            setString(1, recipe.name)
        }.executeQuery().let {
            it.next()
            it.getInt(1)
        }

        for (ingredient in recipe.ingredients) {
            connection.prepareStatement(
                "INSERT INTO masaca.ingredient (name, amount, percentage, cost, type_id, recipe_id) VALUES (?, ?, ?, ?, ?, ?)"
            ).apply {
                setString(1, ingredient.name)
                setBigDecimal(2, ingredient.amount)
                setBigDecimal(3, ingredient.percentage)
                setBigDecimal(4, ingredient.cost)
                setInt(5, ingredient.typeId)
                setInt(6, recipeId)
            }.executeUpdate()
        }
        return recipeId
    }

    fun getRecipe(recipeId: Int): Recipe {
        val recipe = connection.prepareStatement(
            "SELECT r.name FROM masaca.recipe r WHERE r.id = ?"
        ).apply {
            setInt(1, recipeId)
        }.executeQuery().let {
            if (it.next())
                Recipe(recipeId, it.getString(1), ArrayList())
            else
                null
        }
            ?: throw Exception("Recipe does not exist")

        connection.prepareStatement(
            "SELECT i.id, i.name, i.amount, i.percentage, i.cost, i.type_id FROM masaca.ingredient i WHERE i.recipe_id = ?"
        ).apply {
            setInt(1, recipeId)
        }.executeQuery().let {
            while (it.next()) {
                recipe.ingredients.add(
                    Ingredient(
                        id = it.getInt("id"),
                        name = it.getString("name"),
                        amount = it.getBigDecimal("amount"),
                        percentage = it.getBigDecimal("percentage"),
                        cost = it.getBigDecimal("cost"),
                        typeId = it.getInt("type_id"),
                        recipeId = recipeId
                    )
                )
            }
        }
        return recipe
    }
}
