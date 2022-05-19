package sd.web.server.data

import java.time.Instant

data class Homework(val title: String, val publicationTime: Instant, val deadline: Instant?, val description: String?)
