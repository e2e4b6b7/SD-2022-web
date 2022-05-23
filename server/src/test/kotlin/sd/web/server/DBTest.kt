package sd.web.server

import org.junit.jupiter.api.Test
import org.koin.core.component.get
import sd.web.server.data.*
import sd.web.server.db.DBService
import java.time.Instant
import kotlin.test.assertEquals

internal class DBTest : BaseTest() {
    @Test
    fun `simple test of db service`() {
        val db = get<DBService>()

        val hw = Homework("title", Instant.now(), null, null)
        val homeworkId = db.addHomework(hw)

        val checker = Checker("run")
        val checkerId = db.addChecker(checker)

        val homeworks = db.homeworks()
        assertEquals(1, homeworks.size)
        assertEquals(hw.title, homeworks.first().title)

        val hwById = db.homework(homeworkId)
        assertEquals(hw, hwById)

        db.addCheckerToHomework(homeworkId, checkerId)

        val checkers = db.homeworkCheckers(homeworkId)
        assertEquals(1, checkers.size)
        assertEquals(CheckerWithId(checkerId, checker.command), checkers.first())
    }
}

