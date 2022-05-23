package sd.web.server

import org.koin.core.component.KoinComponent
import sd.web.server.data.*
import sd.web.server.db.DBService

class TeacherService : KoinComponent {
    private val dbService: DBService = getKoin().get()

    fun addHomework(homework: Homework): Int {
        return dbService.addHomework(homework)
    }

    fun addChecker(homeworkId: Int, checker: Checker): Int {
        val checkerId = dbService.addChecker(checker)
        dbService.addCheckerToHomework(homeworkId, checkerId)
        return checkerId
    }

    fun getHomework(homeworkId: Int) = dbService.homework(homeworkId)
    fun getHomeworkSubmissions(homeworkId: Int) = dbService.homeworkSubmissions(homeworkId)
    fun getHomeworks() = dbService.homeworks()
    fun getSubmissionWithChecks(submissionId: Int): SubmissionWithChecks? {
        val submission = dbService.submission(submissionId)?.addId(submissionId) ?: return null
        val checks = dbService.submissionChecks(submissionId)
        return SubmissionWithChecks(submission, checks)
    }

    fun getSubmissionsWithChecks(): List<SubmissionWithChecks> {
        return dbService.submissions().map { SubmissionWithChecks(it, dbService.submissionChecks(it.id)) }
    }

    fun getSubmission(submissionId: Int) = dbService.submission(submissionId)
    fun getSubmissions() = dbService.submissions()
}
