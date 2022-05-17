package sd.web.server

import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

val module = module {
    single { StudentViewController() } bind Controller::class
}

fun main() {
    startKoin {
        modules(module)
    }

    HttpServer().start(2347)
}
