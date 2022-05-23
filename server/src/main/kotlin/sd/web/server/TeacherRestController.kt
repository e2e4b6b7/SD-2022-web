package sd.web.server

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.getKoin
import sd.web.server.data.*

class TeacherRestController : Controller {
    private val teacherService: TeacherService = getKoin().get()

    override fun Routing.config() {
        route("/teacher/api") {
            get("/homework/") {
                call.respond(teacherService.getHomeworks())
            }

            post("/homework/") {
                val homeworkWithCheckerScript = call.receive<HomeworkWithCheckerScript>()
                val homeworkId = teacherService.addHomework(Homework(
                    homeworkWithCheckerScript.title,
                    homeworkWithCheckerScript.publicationTime,
                    homeworkWithCheckerScript.deadline,
                    homeworkWithCheckerScript.description
                ))
                val checkerId = teacherService.addChecker(homeworkId, Checker(homeworkWithCheckerScript.checkScript!!))
                call.respond(Pair(homeworkId, checkerId))
            }

            get("/homework/{homeworkId}") {
                val homeworkId = call.parameters["homeworkId"]
                call.respond(teacherService.getHomework(homeworkId!!.toInt())!!)
            }

            get("/submission/") {
                call.respond(teacherService.getSubmissionsWithChecks())
            }

            get("/submission/{submissionId}") {
                val submissionId = call.parameters["submissionId"]
                call.respond(teacherService.getSubmissionWithChecks(submissionId!!.toInt())!!)
            }
        }
    }
}
