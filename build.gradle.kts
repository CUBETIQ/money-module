plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.vintage:junit-vintage-engine:5.6.2")
}