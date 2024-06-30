import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
    maven {
        url = uri("https://jitpack.io")
    }

}

tasks.withType<KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_19)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}

description = "com.theovier client"
group = "com.theovier"
version = "0.0.1-SNAPSHOT"

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "com.theovier.athena.client.LauncherKt"
        )
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    isZip64 = true
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.0")
    implementation("org.apache.logging.log4j:log4j-api:2.17.1")
    implementation("org.apache.logging.log4j:log4j-core:2.17.1")
    implementation("io.github.libktx:ktx-app:1.11.0-rc4")
    implementation("io.github.libktx:ktx-graphics:1.11.0-rc4")
    implementation("io.github.libktx:ktx-assets-async:1.11.0-rc4")
    implementation("io.github.libktx:ktx-math:1.11.0-rc4")
    implementation("io.github.libktx:ktx-ashley:1.11.0-rc4")
    implementation("io.github.libktx:ktx-inject:1.11.0-rc4")
    implementation("io.github.libktx:ktx-tiled:1.11.0-rc4")
    implementation("io.github.libktx:ktx-box2d:1.11.0-rc4")
    implementation("io.github.libktx:ktx-scene2d:1.11.0-rc4")
    implementation("io.github.libktx:ktx-freetype:1.11.0-rc4")
    implementation("io.github.libktx:ktx-freetype-async:1.11.0-rc4")
    implementation("com.badlogicgames.gdx:gdx:1.11.0")
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:1.11.0")
    implementation("com.badlogicgames.gdx:gdx-platform:1.11.0:natives-desktop")
    implementation("com.badlogicgames.gdx:gdx-freetype:1.11.0")
    implementation("com.badlogicgames.gdx:gdx-freetype-platform:1.11.0:natives-desktop")
    implementation("com.badlogicgames.gdx:gdx-box2d:1.11.0")
    implementation("com.badlogicgames.gdx:gdx-box2d-platform:1.11.0:natives-desktop")
    implementation("com.badlogicgames.gdx-controllers:gdx-controllers-desktop:2.2.2")
    implementation("com.badlogicgames.gdx-controllers:gdx-controllers-core:2.2.2")
    implementation("com.badlogicgames.ashley:ashley:1.7.4")
    implementation("com.esotericsoftware.spine:spine-libgdx:4.0.18.1")
    implementation("io.insert-koin:koin-core-jvm:3.1.5")
    implementation("io.insert-koin:koin-test-jvm:3.1.5")
    implementation("io.insert-koin:koin-test-junit5:3.1.5")
    implementation("com.talosvfx:talos-libgdx:1.4.0")
    implementation("com.tinder.statemachine:statemachine:0.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("io.mockk:mockk:1.12.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.0")
}
