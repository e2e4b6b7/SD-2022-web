package sd.web.server

import io.ktor.server.http.*
import kotlinx.html.*
import sd.web.server.data.*
import java.time.Instant

enum class Role(val root: String) {
    STUDENT("/student"), TEACHER("/teacher")
}

fun HTML.homeworksListPage(homeworks: List<HomeworkWithId>, role: Role) {
    body {
        text("kek")
    }
//    head {
//        title {
//            +"Homeworks"
//        }
//    }
//    body {
//        if (role == Role.TEACHER) {
//            text("New homework")
//            form(action = "${role.root}/api/homework/", method = FormMethod.post) {
//                name = "New homework"
//                input(type = InputType.text, name = "title") {
//                    required = true
//                    placeholder = "Title"
//                }
//                br
//
//                label { +"Publication date and time:" }
//                br
//                input(type = InputType.dateTimeLocal, name = "publicationDate") {
//                    required = true
//                }
//                br
//
//                label { +"Deadline:" }
//                br
//                input(type = InputType.dateTimeLocal, name = "deadline")
//                br
//
//                textArea(cols = "120", rows = "10") {
//                    name = "description"
//                    placeholder = "Description"
//                }
//                br
//
//                textArea(cols = "120", rows = "10") {
//                    name = "checkScript"
//                    required = true
//                    placeholder = "Checking bash script"
//                }
//                br
//
//                input(type = InputType.submit)
//            }
//        }
//        table(classes = "border") {
//            tr {
//                th { +"ID" }
//                th { +"Title" }
//                th { +"Publication time" }
//                th { +"Deadline" }
//            }
//            homeworks.forEach {
//                tr {
//                    td {
//                        a(href = "${role.root}/homework/${it.id}") {
//                            +it.id.toString()
//                        }
//                    }
//                    td { +it.title }
//                    td { +it.publicationTime.toString() }
//                    td { +it.deadline.toString() }
//                }
//            }
//        }
//    }
}

fun HTML.homeworkPage(homework: HomeworkWithId?, role: Role) {
    if (homework == null) {
        head {
            title {
                +"Null homework"
            }
        }
        body {
            text("Null homework")
        }
    } else {
        head {
            title {
                +"Homework: ${homework.title}"
            }
        }
        body {
            table {
                tr {
                    th { +"Title" }
                    td { +homework.title }
                }
                tr {
                    th { +"Description" }
                    td { +(homework.description ?: "No description") }
                }
                tr {
                    th { +"Deadline" }
                    td { +(homework.deadline?.toString() ?: "No deadline") }
                }
                tr {
                    th { +"Publication time" }
                    td { +homework.publicationTime.toString() }
                }
            }
            if (role == Role.STUDENT) {
                text("New submission")
                form(action = "${role.root}/api/submission/", method = FormMethod.post) {
                    input(type = InputType.url, name = "solution") {
                        required = true
                        placeholder = "URL to github with solution"
                    }
                    input(type = InputType.hidden, name = "homeworkId") {
                        value = homework.id.toString()
                    }
                    input(type = InputType.hidden, name = "time") {
                        value = Instant.now().toHttpDateString()
                    }
                    input(type = InputType.submit)
                }
            }
        }
    }
}

fun HTML.submissionsListPage(submissionsWithCheck: List<SubmissionWithChecks>, role: Role) {
    head {
        title {
            +"Submissions"
        }
    }
    body {
        table {
            tr {
                th { +"Submission time" }
                th { +"Submission ID" }
                th { +"Homework ID" }
                th { +"Checking result" }
            }
            submissionsWithCheck.forEach {
                tr {
                    td { +it.submission.time.toString() }
                    td {
                        a(href = "${role.root}/submission/${it.submission.id}") { +it.submission.id.toString() }
                    }
                    td {
                        a(href = "${role.root}/homework/${it.submission.homeworkId}") { +it.submission.homeworkId.toString() }
                    }
                    td {
                        +if (it.checks.all { it.mark }) "OK" else "FAILED"
                    }
                }
            }
        }
    }
}

fun HTML.submissionPage(submissionWithChecks: SubmissionWithChecks?, role: Role) {
    if (submissionWithChecks == null) {
        head {
            title {
                +"Null submission"
            }
        }
        body {
            text("Null submission")
        }
    } else {
        head {
            title {
                +"Submission for homework ${submissionWithChecks.submission.homeworkId}"
            }
        }
        body {
            table {
                tr {
                    th { +"Submission time" }
                    td { +submissionWithChecks.submission.time.toString() }
                }
                tr {
                    th { +"Homework" }
                    td {
                        a(href = "${role.root}/homework/${submissionWithChecks.submission.homeworkId}") {
                            +submissionWithChecks.submission.homeworkId.toString()
                        }
                    }
                }
                tr {
                    th { +"Solution" }
                    td {
                        a(href = submissionWithChecks.submission.solution) { +submissionWithChecks.submission.solution }
                    }
                }

                submissionWithChecks.checks.forEachIndexed { index, submissionCheck ->
                    tr {
                        th { +"Check $index" }
                        td {
                            +(
                                "Mark: " + (if (submissionCheck.mark) "OK" else "FAILED") + "\n" +
                                    "Output: ${submissionCheck.output}"
                                )
                        }
                    }
                }
            }
        }
    }
}

