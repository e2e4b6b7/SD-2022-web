package sd.web.server

import org.koin.core.component.KoinComponent
import sd.web.server.data.Submission
import sd.web.server.db.DBService

class StudentService : KoinComponent {
    private val dbService: DBService = getKoin().get()
    fun submit(submission: Submission): Int {
        val submissionId = dbService.addSubmission(submission)
        TODO("Check of submission and return submission ID")
    }

    fun getHomeworkById(homeworkId: Int) = dbService.homework(homeworkId)
    fun getHomeworkSubmissions(homeworkId: Int) = dbService.homeworkSubmissions(homeworkId)
    fun getHomeworks() = dbService.homeworks()
    fun getSubmission(submissionId: Int) = dbService.submission(submissionId)
    fun getSubmissions() = dbService.submissions()
}
