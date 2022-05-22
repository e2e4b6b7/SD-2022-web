package sd.web.server

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.date.*
import org.koin.java.KoinJavaComponent.getKoin
import sd.web.server.data.Submission

class StudentRestController : Controller {
    private val studentService: StudentService = getKoin().get()
    override fun Routing.config() {
        route("/student") {
            get("/homework/") {
                call.respond(studentService.getHomeworks())
            }

            get("/homework/{homeworkId}") {
                val homeworkId = call.parameters["homeworkId"]
                if (homeworkId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Homework ID isn't set")
                } else if (homeworkId.toIntOrNull() == null) {
                    call.respond(HttpStatusCode.BadRequest, "Homework ID isn't integer")
                } else {
                    call.respond(studentService.getHomeworkById(homeworkId.toInt()) ?: "Unknown homework")
                }
            }

            get("/submission/") {
                call.respond(studentService.getSubmissions())
            }

            get("/submission/{submissionId}") {
                val submissionId = call.parameters["submissionId"]
                if (submissionId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Submission ID isn't set")
                } else if (submissionId.toIntOrNull() == null) {
                    call.respond(HttpStatusCode.BadRequest, "Submission ID isn't integer")
                } else {
                    call.respond(studentService.getHomeworkById(submissionId.toInt()) ?: "Unknown submission")
                }
            }

            post("/submission/") {
                val solution = call.parameters["solution"]
                val homeworkId = call.parameters["homeworkId"]
                val submissionDateTime = call.parameters["submissionTime"]
                if (submissionDateTime == null) {
                    call.respond(HttpStatusCode.BadRequest, "Submission time isn't set")
                } else if (solution == null) {
                    call.respond(HttpStatusCode.BadRequest, "Solution isn't set")
                } else if (homeworkId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Homework ID isn't set")
                } else if (homeworkId.toIntOrNull() == null) {
                    call.respond(HttpStatusCode.BadRequest, "Homework ID isn't integer")
                } else {
                    call.respond(
                        HttpStatusCode.OK,
                        studentService.submit(
                            Submission(
                                homeworkId.toInt(),
                                submissionDateTime.fromHttpToGmtDate().toJvmDate().toInstant(),
                                solution
                            )
                        )
                    )
                }
            }
        }
    }
}
