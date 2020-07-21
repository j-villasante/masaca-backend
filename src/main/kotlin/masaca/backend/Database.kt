package masaca.backend

import com.zaxxer.hikari.*


object Database {
    private var dataSource: HikariDataSource

    init {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:postgresql://localhost/masaca"
        config.username = "masaca_adm"
        config.password = "masaca"
        this.dataSource = HikariDataSource(config)
    }

    fun executeQuery(query: String): Int {
        val connection = this.dataSource.connection
        connection.autoCommit = false
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(query)
        resultSet.next()
        val test = resultSet.getInt("test")
        connection.close()
        return test
    }
}
