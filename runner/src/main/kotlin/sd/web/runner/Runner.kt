package sd.web.runner

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import sd.web.server.data.SubmissionCheck
import sd.web.server.data.SubmissionWithCheckers
import sd.web.server.db.DBService
import java.nio.file.Path
import java.util.concurrent.TimeUnit
import kotlin.io.path.createDirectories

class Runner : KoinComponent {
    private val dbService: DBService by inject()

    init {
        ROOT_PATH.createDirectories()
    }

    private fun cloneRepository(githubUrl: String): Boolean {
        return ProcessBuilder("git", "clone", githubUrl)
            .directory(ROOT_PATH.toFile())
            .start()
            .waitFor(10, TimeUnit.MINUTES)
    }

    private fun runChecker(checkerCommand: String, repositoryName: String): Process {
        return ProcessBuilder(checkerCommand)
            .directory(ROOT_PATH.resolve(repositoryName).toFile())
            .start()
    }

    private fun Process.collectCheckerResult(submissionId: Int, checkerId: Int): SubmissionCheck {
        val message: String
        val mark: Boolean
        if (!waitFor(10, TimeUnit.MINUTES)) {
            mark = false
            message = "Failed to execute"
        } else {
            mark = exitValue() == 0
            message = String(inputStream.readAllBytes())
        }
        return SubmissionCheck(submissionId, checkerId, mark, message)
    }

    fun checkSubmission(submissionWithCheckers: SubmissionWithCheckers) {
        val submission = submissionWithCheckers.submission
        val githubUrl = submissionWithCheckers.submission.solution
        val repositoryName = githubUrl.split("/").last()
        if (!cloneRepository(githubUrl)) {
            submissionWithCheckers.checkers.forEach {
                dbService.addSubmissionCheck(
                    SubmissionCheck(submission.id, it.id, false, "Can not download homeworks")
                )
            }
        }
        submissionWithCheckers.checkers.forEach {
            val process = runChecker(it.command, repositoryName)
            val submissionCheck = process.collectCheckerResult(submission.id, it.id)
            dbService.addSubmissionCheck(submissionCheck)
        }
    }

    companion object {
        private val ROOT_PATH = Path.of("./checks/")
    }
}
