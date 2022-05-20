plugins {
    kotlin("jvm")
    jacoco
}

val ktorVersion = "2.0.1"
val slf4jVersion = "1.7.36"
val exposedVersion = "0.38.1"

dependencies {
    implementation("io.ktor", "ktor-server-netty-jvm", ktorVersion)
    implementation("io.ktor", "ktor-server-status-pages", ktorVersion)
    implementation("io.ktor", "ktor-server-html-builder", ktorVersion)

    implementation("org.slf4j", "slf4j-api", slf4jVersion)
    implementation("org.slf4j", "slf4j-simple", slf4jVersion)

    implementation("io.insert-koin", "koin-core", "3.1.6")

    implementation("org.jetbrains.exposed", "exposed-core", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-dao", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-jdbc", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-java-time", exposedVersion)

    implementation("org.postgresql", "postgresql", "42.3.5")

    implementation("org.yaml", "snakeyaml", "1.30")

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
