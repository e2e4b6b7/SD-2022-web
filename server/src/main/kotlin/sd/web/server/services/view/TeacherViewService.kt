package sd.web.server.services.view

import kotlinx.html.HTML
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import sd.web.server.data.addId
import sd.web.server.services.rest.TeacherService
import java.time.Instant

class TeacherViewService : KoinComponent {
    private val teacherService: TeacherService by inject()

    fun getHomeworkPage(homeworkId: Int): HTML.() -> Unit = {
        homeworkPage(teacherService.getHomework(homeworkId)?.addId(homeworkId), Role.TEACHER)
    }

    fun getHomeworksPage(): HTML.() -> Unit = {
        homeworksListPage(teacherService.getHomeworks().filter { it.publicationTime >= Instant.now() }
            .sortedBy { it.publicationTime }, Role.TEACHER
        )
    }

    fun getSubmissionPage(submissionId: Int): HTML.() -> Unit = {
        submissionPage(teacherService.getSubmissionWithChecks(submissionId), Role.TEACHER)
    }

    fun getSubmissionsPage(): HTML.() -> Unit = {
        submissionsListPage(
            teacherService.getSubmissionsWithChecks().sortedBy { it.submission.time }.reversed(), Role.TEACHER
        )
    }
}
