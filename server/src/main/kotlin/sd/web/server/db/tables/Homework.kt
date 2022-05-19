package sd.web.server.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.timestamp


object Homework : IntIdTable() {
    val title = text("title")
    val publicationTime = timestamp("publication_time")
    val deadline = timestamp("deadline").nullable()
    val description = text("description").nullable()
}

