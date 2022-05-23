package sd.web.server

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import sd.web.server.checker.CheckerService
import sd.web.server.data.*
import sd.web.server.db.DBService

class StudentService : KoinComponent {
    private val dbService: DBService by inject()
    private val checkerService: CheckerService by inject()

    fun submit(submission: Submission): Int {
        val submissionId = dbService.addSubmission(submission)
        checkerService.checkSubmission(submission.addId(submissionId))
        return submissionId
    }

    fun getHomework(homeworkId: Int) = dbService.homework(homeworkId)
    fun getHomeworks() = dbService.homeworks()

    fun getHomeworkSubmissions(homeworkId: Int) = dbService.homeworkSubmissions(homeworkId)

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
