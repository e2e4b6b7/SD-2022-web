package sd.web.server

import org.koin.core.component.KoinComponent
import sd.web.server.checker.CheckerService
import sd.web.server.data.*
import sd.web.server.db.DBService

class StudentService : KoinComponent {
    private val dbService: DBService = getKoin().get()
    private val checkerService: CheckerService = getKoin().get()
    fun submit(submission: Submission): Int {
        val submissionId = dbService.addSubmission(submission)
        TODO("Check of submission and return submission ID")
    }

    fun getHomeworkById(homeworkId: Int) = dbService.homework(homeworkId)
    fun getHomeworkSubmissions(homeworkId: Int) = dbService.homeworkSubmissions(homeworkId)
    fun getHomeworks() = dbService.homeworks()
    fun getSubmissionWithChecks(submissionId: Int): SubmissionWithChecks? {
        val submission = dbService.submission(submissionId)?.addId(submissionId) ?: return null
        val checks = dbService.submissionChecks(submissionId)
        return SubmissionWithChecks(submission, checks)
    }
    fun getSubmissionsWithChecks(): List<SubmissionWithChecks> {
        return dbService.submissions().map { SubmissionWithChecks(it, dbService.submissionChecks(it.id) ) }
    }
    fun getSubmission(submissionId: Int) = dbService.submission(submissionId)
    fun getSubmissions() = dbService.submissions()
}
