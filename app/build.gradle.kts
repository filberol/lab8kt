plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    id("org.jetbrains.dokka") version "1.6.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")

    implementation(project(":shared"))
}

application {
    mainClass.set("lab5.AppKt")
}
