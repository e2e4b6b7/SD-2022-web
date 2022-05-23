package sd.web.server

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.getKoin
import sd.web.server.data.Submission

class StudentRestController : Controller {
    private val studentService: StudentService = getKoin().get()
    override fun Routing.config() {
        route("/student/api") {
            get("/homework/") {
                call.respond(studentService.getHomeworks())
            }

            get("/homework/{homeworkId}") {
                val homeworkId = call.parameters["homeworkId"]
                call.respond(studentService.getHomework(homeworkId!!.toInt())!!)
            }

            get("/submission/") {
                call.respond(studentService.getSubmissionsWithChecks())
            }

            get("/submission/{submissionId}") {
                val submissionId = call.parameters["submissionId"]
                call.respond(studentService.getSubmissionWithChecks(submissionId!!.toInt())!!)
            }

            post("/submission/") {
                val submission = call.receive<Submission>()
                call.respond(studentService.submit(submission))
            }
        }
    }
}
