plugins {
    kotlin("jvm")
    jacoco
}

val koinVersion = "3.1.6"

dependencies {
    implementation(project(":server"))

    implementation("io.insert-koin", "koin-core", koinVersion)
    testImplementation("io.insert-koin", "koin-test-junit5", koinVersion)

    implementation("com.rabbitmq", "amqp-client", "5.14.2")

    implementation("com.google.code.gson", "gson", "2.9.0")

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
