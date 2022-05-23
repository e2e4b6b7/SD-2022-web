package sd.web.server

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.getKoin

class TeacherViewController : Controller {
    private val teacherViewService: TeacherViewService = getKoin().get()
    override fun Routing.config() {
        route("/teacher") {
            get("/homework/") {
                call.respondHtml { teacherViewService.getHomeworksPage()() }
            }

            get("/homework/{homeworkId}") {
                val homeworkId = call.parameters["homeworkId"]
                call.respondHtml { teacherViewService.getHomeworkPage(homeworkId!!.toInt())() }
            }

            get("/submission/") {
                call.respondHtml { teacherViewService.getSubmissionsPage()() }
            }

            get("/submission/{submissionId}") {
                val submissionId = call.parameters["submissionId"]
                call.respondHtml {
                    teacherViewService.getSubmissionPage(submissionId!!.toInt())()
                }
            }
        }
    }
}
