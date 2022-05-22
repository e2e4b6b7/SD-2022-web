package sd.web.server

import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module
import org.postgresql.ds.PGConnectionPoolDataSource
import sd.web.server.db.DBService
import javax.sql.ConnectionPoolDataSource

fun dbConnection(config: DBConfig): ConnectionPoolDataSource =
    PGConnectionPoolDataSource().apply {
        user = config.user
        password = config.password
        databaseName = "sd-web"
        serverNames = config.hosts.map { it.hostname }.toTypedArray()
        portNumbers = config.hosts.map { it.port }.toIntArray()
    }

fun main() {
    val config = getConfig()

    val common = module {
        single { dbConnection(config.db) }
    }

    val services = module {
        single { DBService() }
        single { StudentService() }
        single { StudentViewService() }
    }

    val controllers = module {
        single { StudentViewController() } bind Controller::class
        single { StudentRestController() } bind Controller::class
    }

    startKoin {
        modules(common, services, controllers)
    }

    HttpServer().start(2347)
}
