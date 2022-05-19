package sd.web.server.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.timestamp

object Submission : IntIdTable() {
    val homeworkId = integer("homework_id").references(Homework.id)
    val time = timestamp("submission_time")
    val solution = text("solution")
}
