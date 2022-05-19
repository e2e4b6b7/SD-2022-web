package sd.web.server.db.tables

import org.jetbrains.exposed.sql.Table

object HomeworkCheckers : Table() {
    val homeworkId = integer("homework_id").references(Homework.id)
    val checkerId = integer("checker_id").references(Checker.id)

    override val primaryKey = PrimaryKey(arrayOf(homeworkId, checkerId))
}
