package sd.web.server

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import kotlin.io.path.Path
import kotlin.io.path.bufferedReader
import kotlin.properties.Delegates

val configPath = Path("config.yml")

class Config {
    lateinit var db: DBConfig
}

class DBConfig {
    lateinit var user: String
    lateinit var password: String
    lateinit var hosts: List<Host>
}

class Host {
    lateinit var hostname: String
    var port: Int by Delegates.notNull()
}

fun getConfig(): Config {
    return Yaml(Constructor(Config::class.java)).load(configPath.bufferedReader())
}
