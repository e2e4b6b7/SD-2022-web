package sd.web.server.controllers

import io.ktor.server.routing.*

interface Controller {
    fun Routing.config()
}
