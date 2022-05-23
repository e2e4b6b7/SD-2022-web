package sd.web.server

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.getKoin

class StudentViewController : Controller {
    private val studentViewService: StudentViewService = getKoin().get()

    override fun Routing.config() {
        application.install(StatusPages) {
            exception<NumberFormatException> { call, e ->
                call.respond(HttpStatusCode.BadRequest, e.message ?: "")
            }
            exception<Exception> { call, e ->
                call.respond(HttpStatusCode.InternalServerError, e.message ?: "")
            }
        }

        route("/student") {
            get("/homework/") {
                call.respondHtml {
                    studentViewService.getHomeworksPage()
                }
            }

            get("/homework/{homeworkId}") {
                val homeworkId = call.parameters["homeworkId"]
                call.respondHtml {
                    studentViewService.getHomeworkPage(homeworkId!!.toInt())
                }

            }

            get("/submission/") {
                call.respondHtml {
                    studentViewService.getSubmissionsPage()
                }
            }

            get("/submission/{submissionId}") {
                val submissionId = call.parameters["submissionId"]
                call.respondHtml {
                    studentViewService.getSubmissionPage(submissionId!!.toInt())
                }
            }
        }
    }
}
