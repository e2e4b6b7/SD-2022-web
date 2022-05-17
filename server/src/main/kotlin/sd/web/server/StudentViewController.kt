package sd.web.server

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.body

class StudentViewController : Controller {
    override fun Routing.config() {
        route("/student") {
            get("/homeworks") {
                call.respondHtml {
                    body {
                        text("homeworks")
                    }
                }
            }
            get("/homework/{id}") {
                call.respondHtml {
                    body {
                        text("homework ${call.parameters["id"]}")
                    }
                }
            }
            get("homework/{homeworkId}/submission/{submissionId}") {
                val submissionId = call.parameters["submissionId"]
                val homeworkId = call.parameters["homeworkId"]
                call.respondHtml {
                    body {
                        text("submission $submissionId of homework $homeworkId")
                    }
                }
            }
        }
    }
}
