package sd.web.server.services.view

import kotlinx.html.HTML
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import sd.web.server.data.addId
import sd.web.server.services.rest.StudentService
import java.time.Instant

class StudentViewService : KoinComponent {
    private val studentService: StudentService by inject()

    fun getHomeworkPage(homeworkId: Int): HTML.() -> Unit = {
        homeworkPage(studentService.getHomework(homeworkId)?.addId(homeworkId), Role.STUDENT)
    }

    fun getHomeworksPage(): HTML.() -> Unit = {
        homeworksListPage(studentService.getHomeworks().filter { it.publicationTime <= Instant.now() }
            .sortedBy { it.publicationTime }, Role.STUDENT
        )
    }

    fun getSubmissionPage(submissionId: Int): HTML.() -> Unit = {
        submissionPage(studentService.getSubmissionWithChecks(submissionId), Role.STUDENT)
    }

    fun getSubmissionsPage(): HTML.() -> Unit = {
        submissionsListPage(
            studentService.getSubmissionsWithChecks().sortedBy { it.submission.time }.reversed(), Role.STUDENT
        )
    }

    fun getHomeworkSubmissions(homeworkId: Int): HTML.() -> Unit {
        TODO("Maybe will be done but it isn't required in the task")
    }
}
