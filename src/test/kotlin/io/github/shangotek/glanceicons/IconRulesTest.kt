package io.github.shangotek.glanceicons

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class IconRulesTest {

    @Test
    fun `angular role suffix wins over the ts extension`() {
        assertEquals(FileIcon.COMPONENT, IconRules.fileIcon("user-card.component.ts"))
        assertEquals(FileIcon.SERVICE, IconRules.fileIcon("user.service.ts"))
        assertEquals(FileIcon.STORE, IconRules.fileIcon("users.store.ts"))
        assertEquals(FileIcon.MODULE, IconRules.fileIcon("shared.module.ts"))
        assertEquals(FileIcon.MODEL, IconRules.fileIcon("role.enum.ts"))
        assertEquals(FileIcon.MODEL, IconRules.fileIcon("blog.data.ts"))
    }

    @Test
    fun `angular 2025 style guide joins suffixes with a hyphen`() {
        assertEquals(FileIcon.GUARD, IconRules.fileIcon("auth-guard.ts"))
        assertEquals(FileIcon.PIPE, IconRules.fileIcon("date-de-pipe.ts"))
        assertEquals(FileIcon.INTERCEPTOR, IconRules.fileIcon("auth-interceptor.ts"))
        assertEquals(FileIcon.ROUTES, IconRules.fileIcon("user-resolver.ts"))
        assertEquals(FileIcon.MODULE, IconRules.fileIcon("shared-module.ts"))
        // Components, directives and services have no suffix any more; AngularFiles reads their decorator.
        assertNull(IconRules.fileIcon("user-card.ts"))
    }

    @Test
    fun `longest suffix wins`() {
        assertEquals(FileIcon.ROUTES, IconRules.fileIcon("app-routing.module.ts"))
        assertEquals(FileIcon.ROUTES, IconRules.fileIcon("app-routing-module.ts"))
        assertEquals(FileIcon.ROUTES, IconRules.fileIcon("app.routes.server.ts"))
    }

    @Test
    fun `java and kotlin test classes are told apart by case`() {
        assertEquals(FileIcon.JAVA_TEST, IconRules.fileIcon("UserServiceTest.java"))
        assertEquals(FileIcon.JAVA_TEST, IconRules.fileIcon("CheckoutIT.java"))
        assertEquals(FileIcon.JAVA, IconRules.fileIcon("Contest.java"))
        assertEquals(FileIcon.KOTLIN_TEST, IconRules.fileIcon("AppTests.kt"))
        assertEquals(FileIcon.KOTLIN, IconRules.fileIcon("Latest.kt"))
    }

    @Test
    fun `everything else is left to the atom catalogue`() {
        assertNull(IconRules.fileIcon("index.html"))
        assertNull(IconRules.fileIcon("home.spec.ts"))
        assertNull(IconRules.fileIcon("build.gradle.kts"))
        assertNull(IconRules.folderIcon("components"))
    }

    @Test
    fun `angular json gets the new angular logo`() {
        assertEquals(FileIcon.ANGULAR, IconRules.fileIcon("angular.json"))
        assertEquals(FileIcon.ANGULAR, IconRules.fileIcon(".angular-cli.json"))
    }

    @Test
    fun `readme files get the info icon`() {
        for (name in listOf("README", "README.md", "readme.txt", "README.de.md", "README_EN.adoc", "readme-dev.md")) {
            assertEquals(name, FileIcon.README, IconRules.fileIcon(name))
        }
        assertNull(IconRules.fileIcon("readme-parser.ts"))
        assertNull(IconRules.fileIcon("readmes.md"))
    }

    @Test
    fun `angular directives folder has its own icon`() {
        assertEquals(DIRECTIVES_FOLDER, IconRules.folderIcon("Directives"))
    }
}
