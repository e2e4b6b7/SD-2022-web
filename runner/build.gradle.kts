plugins {
    kotlin("jvm")
    jacoco
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.register("fatJar", type = Jar::class) {
    archiveBaseName.set("Runner")
    manifest {
        attributes["Main-Class"] = "sd.web.runner.MainKt"
    }
    with(tasks["jar"] as CopySpec)
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
