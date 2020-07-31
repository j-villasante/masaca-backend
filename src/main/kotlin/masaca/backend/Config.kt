package masaca.backend

import io.ktor.config.*

data class DatabaseConfig(
    val url: String,
    val username: String,
    val password: String
)

object Config {
    lateinit var databaseConfig: DatabaseConfig

    fun setup(config: ApplicationConfig) {
        databaseConfig = DatabaseConfig(
            config.property("database.url").getString(),
            config.property("database.username").getString(),
            config.property("database.password").getString()
        )
    }
}
