package sd.web.server

import io.mockk.every
import org.junit.jupiter.api.Test
import org.koin.core.component.get
import org.koin.test.mock.declareMock
import sd.web.server.data.HomeworkWithId
import sd.web.server.db.DBService
import sd.web.server.services.rest.StudentService
import java.time.Instant
import kotlin.test.assertEquals

internal class ExampleMockTest : BaseTestWithMockk() {
    private fun mockDB() {
        declareMock<DBService> {
            every { homeworks() } returns
                listOf(HomeworkWithId(1, "title", Instant.now(), null, null))
        }
    }

    @Test
    fun `test controller without db`() {
        mockDB()

        val service = get<StudentService>()
        assertEquals(1, service.getHomeworks().size)
        assertEquals("title", service.getHomeworks().first().title)
    }
}
