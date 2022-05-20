package sd.web.server.db

import org.jetbrains.exposed.sql.ResultRow
import sd.web.server.data.*
import sd.web.server.db.tables.Checker
import sd.web.server.db.tables.Homework
import sd.web.server.db.tables.Submission

val ResultRow.asHomework: sd.web.server.data.Homework
    get() = Homework(
        this[Homework.title],
        this[Homework.publicationTime],
        this[Homework.deadline],
        this[Homework.description]
    )

val ResultRow.asHomeworkWithId: HomeworkWithId
    get() = HomeworkWithId(
        this[Homework.id].value,
        this[Homework.title],
        this[Homework.publicationTime],
        this[Homework.deadline],
        this[Homework.description]
    )

val ResultRow.asSubmission: sd.web.server.data.Submission
    get() = Submission(
        this[Submission.homeworkId],
        this[Submission.time],
        this[Submission.solution],
    )

val ResultRow.asSubmissionWithId: SubmissionWithId
    get() = SubmissionWithId(
        this[Submission.id].value,
        this[Submission.homeworkId],
        this[Submission.time],
        this[Submission.solution],
    )

val ResultRow.asChecker: sd.web.server.data.Checker
    get() = Checker(
        this[Checker.command]
    )

val ResultRow.asCheckerWithId: CheckerWithId
    get() = CheckerWithId(
        this[Checker.id].value,
        this[Checker.command]
    )
