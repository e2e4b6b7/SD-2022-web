package sd.web.runner

import com.google.gson.GsonBuilder
import com.rabbitmq.client.*
import sd.web.server.MessageBrokerConfig
import sd.web.server.data.*

class RabbitMQConsumer(config: MessageBrokerConfig): AutoCloseable {

    private val runner = Runner()
    private val queueName = config.queueName

    private val connectionFactory = ConnectionFactory().apply {
        host = config.host.hostname
        port = config.host.port
    }

    private val connection = connectionFactory.newConnection()

    private val channel = connection.createChannel()

    override fun close() {
        channel.close()
        connection.close()
    }

    fun run() {
        channel.queueDeclare(queueName, false, false, false, null)
        channel.basicConsume(
            queueName,
            true,
            {
                _, message ->
                val task = gson.fromJson(String(message.body), SubmissionWithCheckers::class.java)
                runner.checkSubmission(task)
            }
        ) { _ -> }
    }

    companion object {
        private val gson = GsonBuilder().create()
    }

}
