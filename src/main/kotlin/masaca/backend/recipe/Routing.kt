package masaca.backend.recipe

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import masaca.backend.error.*


fun Routing.recipe() {
    route("/recipe") {
        post("/") {
            val recipe = UseCase.createRecipe(call.receive())
            call.respond(recipe)
        }
        get("/{recipe_id}") {
            val recipeId = call.parameters["recipe_id"] ?: throw MasacaInvalidParameterError("invalid recipe id")
            call.respond(UseCase.getRecipe(recipeId.toInt()))
        }
        get("/") {
            call.respond(UseCase.listRecipes())
        }
    }
}
