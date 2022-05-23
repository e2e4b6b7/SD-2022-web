package sd.web.server

import io.mockk.mockkClass
import org.h2.jdbcx.JdbcDataSource
import org.jetbrains.exposed.sql.Database.Companion.connectPool
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import sd.web.server.db.tables.*
import javax.sql.ConnectionPoolDataSource

internal open class BaseTest : KoinTest {
    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            module { single { h2DataSource } },
            services,
            controllers
        )
    }
}

internal open class BaseTestWithMockk : BaseTest() {
    @JvmField
    @RegisterExtension
    val mockProvider = MockProviderExtension.create { mockkClass(it) }
}


internal val h2DataSource: ConnectionPoolDataSource
    get() = JdbcDataSource().apply {
        setURL("jdbc:h2:~/test")
        user = "sa"
        password = "sa"
        val conn = connectPool(this)
        transaction(conn) {
            SchemaUtils.create(Checker)
            SchemaUtils.create(Homework)
            SchemaUtils.create(HomeworkCheckers)
            SchemaUtils.create(Submission)
            SchemaUtils.create(SubmissionCheck)
        }
    }
