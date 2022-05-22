package sd.web.server

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.HTML
import kotlinx.html.body
import org.koin.java.KoinJavaComponent.getKoin

class StudentViewController : Controller {
    private val studentViewService: StudentViewService = getKoin().get()

    override fun Routing.config() {
        route("/student") {
            get("/homework/") {
                call.respondHtml {
                    studentViewService.getHomeworksPage()
                }
            }

            get("/homework/{homeworkId}") {
                val homeworkId = call.parameters["homeworkId"]
                if (homeworkId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Homework ID isn't set")
                } else if (homeworkId.toIntOrNull() == null) {
                    call.respond(HttpStatusCode.BadRequest, "Homework ID isn't integer")
                } else {
                    call.respondHtml {
                        studentViewService.getHomeworkPage(homeworkId.toInt())
                    }
                }
            }

            get("/submission/") {
                call.respondHtml {
                    studentViewService.getSubmissionsPage()
                }
            }

            get("/submission/{submissionId}") {
                val submissionId = call.parameters["submissionId"]
                if (submissionId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Submission ID isn't set")
                } else if (submissionId.toIntOrNull() == null) {
                    call.respond(HttpStatusCode.BadRequest, "Submission ID isn't integer")
                } else {
                    call.respondHtml {
                        studentViewService.getSubmissionPage(submissionId.toInt())
                    }
                }
            }
        }
    }
}
