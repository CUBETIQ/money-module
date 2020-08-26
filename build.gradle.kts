plugins {
    kotlin("jvm") version "1.4.0"
}

group = "com.cubetiqs.libra"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.vintage:junit-vintage-engine:5.6.2")
}
