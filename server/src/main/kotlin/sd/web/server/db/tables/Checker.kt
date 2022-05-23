package sd.web.server.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Checker : IntIdTable() {
    val command = text("command")
}
