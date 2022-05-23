package sd.web.server.controllers

import java.time.*

fun String.toInstant(): Instant {
    return LocalDateTime.parse(this).toInstant(ZoneOffset.ofHours(0))
}
