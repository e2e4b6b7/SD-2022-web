package sd.web.server.controllers

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import sd.web.server.services.view.TeacherViewService

class TeacherViewController : Controller, KoinComponent {
    private val teacherViewService: TeacherViewService by inject()

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
