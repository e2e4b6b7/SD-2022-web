package sd.web.server.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import sd.web.server.data.Submission
import sd.web.server.services.rest.StudentService

class StudentRestController : Controller, KoinComponent {
    private val studentService: StudentService by inject()

    override fun Routing.config() {
        route("/student/api") {
            get("/homework/") {
                call.respond(studentService.getHomeworks())
            }

            get("/homework/{homeworkId}") {
                val homeworkId = call.parameters["homeworkId"]!!.toInt()
                call.respond(studentService.getHomework(homeworkId)!!)
            }

            get("/submission/") {
                call.respond(studentService.getSubmissionsWithChecks())
            }

            get("/submission/{submissionId}") {
                val submissionId = call.parameters["submissionId"]!!.toInt()
                call.respond(studentService.getSubmissionWithChecks(submissionId)!!)
            }

            post("/submission/") {
                val submission = call.receive<Submission>()
                call.respond(studentService.submit(submission))
            }
        }
    }
}
