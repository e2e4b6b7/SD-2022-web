package sd.web.server.checker

import org.koin.core.component.KoinComponent
import org.koin.java.KoinJavaComponent
import sd.web.server.data.Submission
import sd.web.server.db.DBService
import sd.web.server.getConfig

class CheckerService: AutoCloseable, KoinComponent {

    private val checkerInfo: CheckerConnectionInfo = KoinJavaComponent.getKoin().get()

    private val connection = checkerInfo.connectionFactory.newConnection()
    private val channel = connection.createChannel()

    private val dbService: DBService = KoinJavaComponent.getKoin().get()

    fun checkSubmission(submission: Submission) {

    }

    override fun close() {
        channel.close()
        connection.close()
    }
}
