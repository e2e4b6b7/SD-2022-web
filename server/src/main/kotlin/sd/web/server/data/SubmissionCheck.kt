package sd.web.server.data

data class SubmissionCheck(val submissionId: Int, val checkerId: Int, val mark: Boolean, val output: String)

data class SubmissionCheckWithId(
    val id: Int,
    val submissionId: Int,
    val checkerId: Int,
    val mark: Boolean,
    val output: String
)
