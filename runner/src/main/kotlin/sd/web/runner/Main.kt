package sd.web.runner

import org.koin.core.context.startKoin
import org.koin.dsl.module
import sd.web.server.*
import kotlin.concurrent.thread

private fun startConsumer(config: MessageBrokerConfig) = thread {
    RabbitMQConsumer(config).run()
}

fun main() {
    val config = getConfig()

    val common = module {
        single { dbConnection(config.db) }
        single { checkerConnectionInfo(config.messageBroker) }
    }

    startKoin {
        modules(common, services, controllers)
    }

    startConsumer(config.messageBroker)
    startConsumer(config.messageBroker)
}
