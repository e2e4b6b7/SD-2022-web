package sd.web.server

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.getKoin
import sd.web.server.data.Submission

class StudentRestController : Controller {
    private val studentService: StudentService = getKoin().get()
    override fun Routing.config() {
        application.install(StatusPages) {
            exception<NumberFormatException> { call, e ->
                call.respond(HttpStatusCode.BadRequest, e.message ?: "")
            }
            exception<Exception> { call, e ->
                call.respond(HttpStatusCode.InternalServerError, e.message ?: "")
            }
        }
        application.install(ContentNegotiation) {
            json()
        }

        route("/student") {
            get("/homework/") {
                call.respond(studentService.getHomeworks())
            }

            get("/homework/{homeworkId}") {
                val homeworkId = call.parameters["homeworkId"]
                call.respond(studentService.getHomeworkById(homeworkId!!.toInt())!!)
            }

            get("/submission/") {
                call.respond(studentService.getSubmissions())
            }

            get("/submission/{submissionId}") {
                val submissionId = call.parameters["submissionId"]
                call.respond(studentService.getHomeworkById(submissionId!!.toInt())!!)
            }

            post("/submission/") {
                val submission = call.receive<Submission>()
                call.respond(studentService.submit(submission))
            }
        }
    }
}
