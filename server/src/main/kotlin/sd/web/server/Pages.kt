package sd.web.server

import kotlinx.html.*
import sd.web.server.data.*

enum class Role(val root: String) {
    STUDENT("/student"), TEACHER("teacher")
}

fun HTML.homeworksListPage(homeworks: List<HomeworkWithId>, role: Role) {
    head {
        title {
            +"Homeworks"
        }
    }
    body {
        if (role == Role.TEACHER) {
            text("New homework")
            form(action = "${role.root}/homework", method = FormMethod.post) {
                name = "New homework"
                input(type = InputType.text, name = "title") {
                    required = true
                    placeholder = "Title"
                }

                input(type = InputType.text, name = "description") {
                    placeholder = "Description"
                }
                text("Publication date and time:")
                input(type = InputType.dateTimeLocal, name = "publicationDate") {
                    required = true
                }
                text("Deadline:")
                input(type = InputType.dateTimeLocal, name = "deadline")
                input(type = InputType.submit)
            }
        }
        table {
            tr {
                th { +"ID" }
                th { +"Title" }
                th { +"Publication time" }
                th { +"Deadline" }
            }
            homeworks.forEach {
                tr {
                    td {
                        a(href = "${role.root}/homework/${it.id}") {
                            +it.id.toString()
                        }
                    }
                    td { +it.title }
                    td { +it.publicationTime.toString() }
                    td { +it.deadline.toString() }
                }
            }
        }
    }
}

fun HTML.homeworkPage(homework: HomeworkWithId, role: Role) {
    head {
        title {
            +"Homework: ${homework.title}"
        }
    }
    body {
        table {
            tr {
                th { +"Homework ID" }
                td { +homework.id.toString() }
            }
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
            form(action = "${role.root}/submission", method = FormMethod.post) {
                input(type = InputType.url, name = "solution") {
                    required = true
                    placeholder = "URL to github with solution"
                }
                input(type = InputType.submit)
            }
        }
    }
}

fun HTML.submissionsListPage(submissions: List<SubmissionWithId>, role: Role) {
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
            submissions.forEach {
                tr {
                    td { +it.time.toString() }
                    td {
                        a(href = "${role.root}/submission/${it.id}") { +it.id.toString() }
                    }
                    td {
                        a(href = "${role.root}/homework/${it.homeworkId}") { +it.homeworkId.toString() }
                    }
                    td {
                        +"TODO"
                    }
                }
            }
        }
    }
}

fun HTML.submissionPage(submission: SubmissionWithId, role: Role) {
    head {
        title {
            +"Submission for homework ${submission.homeworkId}"
        }
    }
    body {
        table {
            tr {
                th { +"Submission ID" }
                td { submission.id.toString() }
            }
            tr {
                th { +"Submission time" }
                td { +submission.time.toString() }
            }
            tr {
                th { +"Homework" }
                td {
                    a(href = "${role.root}/homework/${submission.homeworkId}") {
                        +submission.homeworkId.toString()
                    }
                }
            }
            tr {
                th { +"Solution" }
                td {
                    a(href = submission.solution) { +submission.solution }
                }
            }
            tr {
                th { +"Checking result" }
                td { +"TODO" }
            }
        }
    }
}

