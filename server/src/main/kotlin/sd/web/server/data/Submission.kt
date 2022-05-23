package sd.web.server.data

import java.time.Instant

data class Submission(val homeworkId: Int, val time: Instant, val solution: String)

data class SubmissionWithId(val id: Int, val homeworkId: Int, val time: Instant, val solution: String)

data class SubmissionWithChecks(val submission: SubmissionWithId, val checks: List<SubmissionCheck>)

data class SubmissionWithCheckers(val submission: SubmissionWithId, val checkers: List<CheckerWithId>)

fun Submission.addId(id: Int): SubmissionWithId = SubmissionWithId(id, homeworkId, time, solution)
