package sd.web.server

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.getKoin

class StudentViewController : Controller {
    private val studentViewService: StudentViewService = getKoin().get()

    override fun Routing.config() {
        route("/student") {
            get("/homework/") {
                call.respondHtml {
                    studentViewService.getHomeworksPage()()
                }
            }

            get("/homework/{homeworkId}") {
                val homeworkId = call.parameters["homeworkId"]
                call.respondHtml {
                    studentViewService.getHomeworkPage(homeworkId!!.toInt())()
                }
            }

            get("/submission/") {
                call.respondHtml {
                    studentViewService.getSubmissionsPage()()
                }
            }

            get("/submission/{submissionId}") {
                val submissionId = call.parameters["submissionId"]
                call.respondHtml {
                    studentViewService.getSubmissionPage(submissionId!!.toInt())()
                }
            }
        }
    }
}
