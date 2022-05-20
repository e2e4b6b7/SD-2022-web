package sd.web.server

import kotlinx.html.*
import sd.web.server.data.Homework
import sd.web.server.data.Submission

enum class Role {
    STUDENT, TEACHER
}

fun HTML.homeworksListPage(homeworks: List<Homework>, role: Role) {
    head {
        title {
            +"Homeworks"
        }
    }
    body {
        if (role == Role.TEACHER) {
            form {
                // TODO form for creating homework and call rout like 'post teacher/new_homework'
            }
        }
        table {
            // TODO table of homeworks with possibility for click and opening homework page
        }
    }
}

fun HTML.homeworkPage(homework: Homework, role: Role) {
    head {
        title {
            +"Homework: ${homework.title}"
        }
    }
    body {
        // TODO display info about task
        if (role == Role.STUDENT) {
            form {
                // TODO form for submit and call rout like 'post student/new_submit'
            }
        }
    }
}

fun HTML.submissionsListPage(submissions: List<Submission>) {
    head {
        title {
            +"Submissions"
        }
    }
    body {
        table {
            // TODO table of submissions
        }
    }
}

fun HTML.submissionPage(submission: Submission) {
    head {
        title {
            +"Submission for homework ${submission.homeworkId}"
        }
    }
    body {
        // TODO display info about submission
    }
}

