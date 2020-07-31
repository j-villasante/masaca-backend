package masaca.backend

import com.zaxxer.hikari.*
import java.sql.*


object Database {
    private var dataSource: HikariDataSource

    init {
        val config = HikariConfig()
        config.jdbcUrl = Config.databaseConfig.url
        config.username = Config.databaseConfig.username
        config.password = Config.databaseConfig.password
        this.dataSource = HikariDataSource(config)
    }

    fun <T> doQuery(operation: (connection: Connection) -> T): T {
        val connection = this.dataSource.connection
        connection.autoCommit = false
        val result = operation(connection)
        connection.close()
        return result
    }
}
