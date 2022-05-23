package sd.web.server.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import sd.web.server.data.*
import sd.web.server.services.rest.TeacherService
import java.time.*

class TeacherRestController : Controller, KoinComponent {
    private val teacherService: TeacherService by inject()

    override fun Routing.config() {
        route("/teacher/api") {
            get("/homework/") {
                call.respond(teacherService.getHomeworks())
            }

            post("/homework/") {
                val formParams = call.receiveParameters()
                val homeworkId = teacherService.addHomework(Homework(
                    formParams["title"]!!,
                    formParams["publicationTime"]!!.toInstant(),
                    formParams["deadline"]!!.toInstant(),
                    formParams["description"]!!
                ))
                val checkerId = teacherService.addChecker(homeworkId, Checker(formParams["checkScript"]!!))
                call.respondRedirect("/teacher/homework/")
            }

            get("/homework/{homeworkId}") {
                val homeworkId = call.parameters["homeworkId"]!!.toInt()
                call.respond(teacherService.getHomework(homeworkId)!!)
            }

            get("/submission/") {
                call.respond(teacherService.getSubmissionsWithChecks())
            }

            get("/submission/{submissionId}") {
                val submissionId = call.parameters["submissionId"]!!.toInt()
                call.respond(teacherService.getSubmissionWithChecks(submissionId)!!)
            }
        }
    }
}
