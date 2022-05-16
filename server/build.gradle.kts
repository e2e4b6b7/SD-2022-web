plugins {
    kotlin("jvm")
    jacoco
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.register("fatJar", type = Jar::class) {
    archiveBaseName.set("Server")
    manifest {
        attributes["Main-Class"] = "sd.web.server.MainKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
