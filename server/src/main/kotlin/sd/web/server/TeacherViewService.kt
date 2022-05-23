package sd.web.server

import kotlinx.html.HTML
import org.koin.core.component.KoinComponent
import sd.web.server.data.addId
import java.time.Instant

class TeacherViewService : KoinComponent {
    private val teacherService: TeacherService = getKoin().get()

    fun getHomeworkPage(homeworkId: Int): HTML.() -> Unit {
        return { homeworkPage(teacherService.getHomework(homeworkId)?.addId(homeworkId), Role.TEACHER) }
    }

    fun getHomeworksPage(): HTML.() -> Unit {
        return {
            homeworksListPage(teacherService.getHomeworks().filter { it.publicationTime >= Instant.now() }
                .sortedBy { it.publicationTime }, Role.TEACHER
            )
        }
    }

    fun getSubmissionPage(submissionId: Int): HTML.() -> Unit {
        return { submissionPage(teacherService.getSubmissionWithChecks(submissionId), Role.TEACHER) }
    }

    fun getSubmissionsPage(): HTML.() -> Unit {
        return {
            submissionsListPage(
                teacherService.getSubmissionsWithChecks().sortedBy { it.submission.time }.reversed(), Role.TEACHER
            )
        }
    }
}
