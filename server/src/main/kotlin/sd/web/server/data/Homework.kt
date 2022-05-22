package sd.web.server.data

import java.time.Instant

data class Homework(val title: String, val publicationTime: Instant, val deadline: Instant?, val description: String?)

data class HomeworkWithId(
    val id: Int,
    val title: String,
    val publicationTime: Instant,
    val deadline: Instant?,
    val description: String?
)

fun Homework.addId(id: Int): HomeworkWithId = HomeworkWithId(id, title, publicationTime, deadline, description)
