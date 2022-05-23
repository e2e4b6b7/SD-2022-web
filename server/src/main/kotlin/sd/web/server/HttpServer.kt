package sd.web.server

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.component.KoinComponent

class HttpServer : KoinComponent {
    fun start(port: Int) {
        embeddedServer(Netty, port = port) {
            install(StatusPages) {
                exception<IllegalArgumentException> { call, e ->
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "")
                }
                exception<Exception> { call, e ->
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "")
                }
            }
            install(ContentNegotiation) {
                json()
            }
            routing {
                getKoin().getAll<Controller>().asSequence().distinct().forEach {
                    with(it) { this@routing.config() }
                }
            }
        }.start(wait = true)
    }
}
