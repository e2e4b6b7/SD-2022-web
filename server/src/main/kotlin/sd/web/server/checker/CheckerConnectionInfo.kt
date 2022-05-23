package sd.web.server.checker

import com.rabbitmq.client.ConnectionFactory
import sd.web.server.MessageBrokerConfig

class CheckerConnectionInfo(config: MessageBrokerConfig) {
    val queueName = config.queueName
    val connectionFactory = ConnectionFactory().apply {
        host = config.host.hostname
        port = config.host.port
    }
}
