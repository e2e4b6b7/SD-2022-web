package sd.web.server

import io.ktor.server.routing.*

interface Controller {
    fun Routing.config()
}
