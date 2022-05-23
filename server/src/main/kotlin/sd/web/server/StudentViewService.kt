package sd.web.server

import kotlinx.html.HTML
import org.koin.core.component.KoinComponent
import sd.web.server.data.addId
import java.time.Instant

class StudentViewService : KoinComponent {
    private val studentService: StudentService = getKoin().get()

    fun getHomeworkPage(homeworkId: Int): HTML.() -> Unit {
        return { homeworkPage(studentService.getHomework(homeworkId)?.addId(homeworkId), Role.STUDENT) }
    }

    fun getHomeworksPage(): HTML.() -> Unit {
        return {
            homeworksListPage(studentService.getHomeworks().filter { it.publicationTime >= Instant.now() }
                .sortedBy { it.publicationTime }, Role.STUDENT
            )
        }
    }

    fun getSubmissionPage(submissionId: Int): HTML.() -> Unit {
        return { submissionPage(studentService.getSubmissionWithChecks(submissionId), Role.STUDENT) }
    }

    fun getSubmissionsPage(): HTML.() -> Unit {
        return {
            submissionsListPage(
                studentService.getSubmissionsWithChecks().sortedBy { it.submission.time }.reversed(), Role.STUDENT
            )
        }
    }

    fun getHomeworkSubmissions(homeworkId: Int): HTML.() -> Unit {
        TODO("Maybe will be done but it isn't required in the task")
    }
}
