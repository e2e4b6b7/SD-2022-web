package sd.web.server.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import sd.web.server.data.*
import sd.web.server.services.rest.TeacherService

class TeacherRestController : Controller, KoinComponent {
    private val teacherService: TeacherService by inject()

    override fun Routing.config() {
        route("/teacher/api") {
            get("/homework/") {
                call.respond(teacherService.getHomeworks())
            }

            post("/homework/") {
                val homeworkWithCheckerScript = call.receive<HomeworkWithCheckerScript>()
                val homeworkId = teacherService.addHomework(
                    Homework(
                        homeworkWithCheckerScript.title,
                        homeworkWithCheckerScript.publicationTime,
                        homeworkWithCheckerScript.deadline,
                        homeworkWithCheckerScript.description
                    )
                )
                val checkerId = teacherService.addChecker(homeworkId, Checker(homeworkWithCheckerScript.checkScript!!))
                call.respond(Pair(homeworkId, checkerId))
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
