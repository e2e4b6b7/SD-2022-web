package sd.web.server.data

import java.time.Instant

data class Submission(val homeworkId: Int, val time: Instant, val solution: String)

data class SubmissionWithId(val id: Int, val homeworkId: Int, val time: Instant, val solution: String)
