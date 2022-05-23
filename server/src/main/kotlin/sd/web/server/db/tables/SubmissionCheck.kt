package sd.web.server.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object SubmissionCheck : IntIdTable() {
    val submissionId = integer("submission_id").references(Submission.id)
    val checkerId = integer("checker_id").references(Checker.id)
    val mark = bool("mark")
    val output = text("checker_output")
}
