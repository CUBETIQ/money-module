plugins {
    kotlin("jvm") version "1.4.0"
    `java-library`
    id("de.marcphilipp.nexus-publish") version "0.4.0"
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

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

nexusPublishing {
    repositories {
        create("mavenJava") {
            nexusUrl.set(uri("https://nexus.osa.cubetiqs.com/repository/local-maven-dev/"))
            snapshotRepositoryUrl.set(uri("https://nexus.osa.cubetiqs.com/repository/local-maven-dev/"))
            username.set("cubetiq")
            password.set("cube17tiq")
        }
    }
}