package sd.web.server

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StudentViewController : Controller, KoinComponent {
    private val studentViewService: StudentViewService by inject()

    override fun Routing.config() {
        route("/student") {
            get("/homework/") {
                call.respondHtml {
                    studentViewService.getHomeworksPage()()
                }
            }

            get("/homework/{homeworkId}") {
                val homeworkId = call.parameters["homeworkId"]!!.toInt()
                call.respondHtml {
                    studentViewService.getHomeworkPage(homeworkId)()
                }
            }

            get("/submission/") {
                call.respondHtml {
                    studentViewService.getSubmissionsPage()()
                }
            }

            get("/submission/{submissionId}") {
                val submissionId = call.parameters["submissionId"]!!.toInt()
                call.respondHtml {
                    studentViewService.getSubmissionPage(submissionId)()
                }
            }
        }
    }
}
