import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.kotlin.gradle.dsl.JvmDefaultMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.intellij.platform")
}

kotlin {
    // Build with JDK 25, but emit Java 21 bytecode: every IDE since 2024.2 runs on JetBrains Runtime 21 or newer.
    jvmToolchain(25)
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
        freeCompilerArgs.add("-Xjdk-release=21")
        // Only use stdlib APIs that exist in the Kotlin bundled with the oldest supported IDE (2025.1 ships Kotlin 2.1).
        apiVersion = KotlinVersion.KOTLIN_2_1
        // Don't generate forwarding copies of interface default methods: for ProjectViewNodeDecorator that copy
        // overrides a deprecated method, which the Marketplace verifier reports as deprecated API usage.
        jvmDefault = JvmDefaultMode.NO_COMPATIBILITY
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 21
}

dependencies {
    testImplementation("junit:junit:4.13.2")

    intellijPlatform {
        // Override with -PplatformVersion=2025.1.7.2 to run the tests in the oldest supported IDE.
        intellijIdea(providers.gradleProperty("platformVersion"))
        testFramework(TestFrameworkType.Platform)
        // The unified IntelliJ IDEA distribution needs its Ultimate module declared for the headless test IDE to start.
        testBundledPlugins("com.intellij.modules.ultimate")
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "251"
            // Icon providers are stable platform API, so leave the upper bound open.
            untilBuild = provider { null }
        }
    }
    pluginVerification {
        ides {
            // -PverifyIde=/path/to/ide checks one installed IDE instead of downloading the recommended set.
            val localIde = providers.gradleProperty("verifyIde")
            if (localIde.isPresent) local(localIde.get()) else recommended()
        }
    }
}
