package sd.web.runner

import sd.web.server.*
import javax.sql.ConnectionPoolDataSource
import kotlin.concurrent.thread

private fun startConsumer(config: MessageBrokerConfig) = thread(start = true, isDaemon = false) {
    RabbitMQConsumer(config).run()
}


fun main() {
    val config = getConfig()
    startConsumer(config.messageBroker)
    startConsumer(config.messageBroker)
}
