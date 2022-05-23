package sd.web.runner

import sd.web.server.data.*
import sd.web.server.db.DBService
import java.nio.file.Path
import kotlin.io.path.createDirectories
import org.koin.java.KoinJavaComponent.getKoin

class Runner {
    private val dbService: DBService = getKoin().get()

    init {
        ROOT_PATH.createDirectories()
    }

    fun checkSubmission(submissionWithCheckers: SubmissionWithCheckers) {
        val submission = submissionWithCheckers.submission
        val githubUrl = submissionWithCheckers.submission.solution
        TODO("Add check")
        submissionWithCheckers.checkers.forEach {
            dbService.addSubmissionCheck(SubmissionCheck(submission.id, it.id, true, "test"))
        }
    }

    companion object {
        private val ROOT_PATH = Path.of("./checks/")
    }
}
