plugins {
    kotlin("jvm") version "1.6.10"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
    implementation("org.apache.commons:commons-csv:1.5")

    implementation("org.postgresql:postgresql:42.3.3")

    implementation(project(":shared"))
}