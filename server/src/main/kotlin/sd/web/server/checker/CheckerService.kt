package sd.web.server.checker

import com.google.gson.GsonBuilder
import org.koin.core.component.KoinComponent
import org.koin.java.KoinJavaComponent
import sd.web.server.data.*
import sd.web.server.db.DBService

class CheckerService: AutoCloseable, KoinComponent {

    private val checkerInfo: CheckerConnectionInfo = KoinJavaComponent.getKoin().get()

    private val connection = checkerInfo.connectionFactory.newConnection()
    private val channel = connection.createChannel()

    private val dbService: DBService = KoinJavaComponent.getKoin().get()

    fun checkSubmission(submission: SubmissionWithId) {
        channel.queueDeclare(checkerInfo.queueName, false, false, false, null)
        val checkers = dbService.homeworkCheckers(submission.homeworkId)
        channel.basicPublish(
            "",
            checkerInfo.queueName,
            null,
            gson.toJson(SubmissionWithCheckers(submission, checkers)).toByteArray()
        )
    }

    override fun close() {
        channel.close()
        connection.close()
    }

    companion object {
        private val gson = GsonBuilder().create()
    }
}
