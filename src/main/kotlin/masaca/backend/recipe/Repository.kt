package masaca.backend.recipe

import masaca.backend.domain.recipe.*
import masaca.backend.error.*
import java.sql.*
import java.time.*

class Repository(private val connection: Connection) {
    fun createRecipe(recipe: Recipe): Int {
        val recipeId = connection.prepareStatement(
            "INSERT INTO masaca.recipe (name, target, starter, levain_hydration) VALUES (?, ?, ?, ?) RETURNING id"
        ).apply {
            setString(1, recipe.name)
            setInt(2, recipe.target)
            setObject(3, recipe.starter, Types.INTEGER)
            setObject(4, recipe.levainHydration, Types.INTEGER)
            recipe.levainHydration?.let { setInt(4, it) }
        }.executeQuery().let {
            it.next()
            it.getInt(1)
        }

        for (ingredient in recipe.ingredients) {
            connection.prepareStatement(
                "INSERT INTO masaca.ingredient (name, amount, percentage, cost, type_id, recipe_id) VALUES (?, ?, ?, ?, ?, ?)"
            ).apply {
                setString(1, ingredient.name)
                setInt(2, ingredient.amount)
                setBigDecimal(3, ingredient.percentage)
                setBigDecimal(4, ingredient.cost)
                setInt(5, ingredient.type.id)
                setInt(6, recipeId)
            }.executeUpdate()
        }
        return recipeId
    }

    fun getRecipe(recipeId: Int): Recipe {
        val recipe = connection.prepareStatement(
            "SELECT r.name, r.target, r.starter, r.levain_hydration, r.created_at FROM masaca.recipe r WHERE r.id = ?"
        ).apply {
            setInt(1, recipeId)
        }.executeQuery().let {
            if (it.next())
                Recipe(
                    recipeId,
                    it.getString(1),
                    it.getInt(2),
                    it.getInt(3),
                    it.getInt(4),
                    it.getObject(5, OffsetDateTime::class.java),
                    ArrayList()
                )
            else
                null
        }
            ?: throw MasacaNotFoundError("Recipe does not exist")

        connection.prepareStatement(
            "SELECT i.id, i.name, i.amount, i.percentage, i.cost, it.name FROM masaca.ingredient i INNER JOIN masaca.ingredient_type it ON i.type_id = it.id WHERE i.recipe_id = ?"
        ).apply {
            setInt(1, recipeId)
        }.executeQuery().let {
            while (it.next()) {
                recipe.ingredients.add(
                    Ingredient(
                        id = it.getInt(1),
                        name = it.getString(2),
                        amount = it.getInt(3),
                        percentage = it.getBigDecimal(4),
                        cost = it.getBigDecimal(5),
                        type = IngredientType.valueOf(it.getString(6)),
                        recipeId = recipeId
                    )
                )
            }
        }
        return recipe
    }

    fun listRecipe(): ArrayList<Recipe> {
        val recipes = ArrayList<Recipe>()
        connection.createStatement().executeQuery(
            "SELECT id, name, target, created_at  FROM masaca.recipe"
        ).let {
            while (it.next()) {
                recipes.add(
                    Recipe(
                        it.getInt(1),
                        it.getString(2),
                        it.getInt(3),
                        created_at = it.getObject(4, OffsetDateTime::class.java),
                        ingredients = ArrayList()
                    )
                )
            }
        }
        return recipes
    }
}
