package io.github.shangotek.glanceicons

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AtomCatalogTest {

    private fun file(name: String, path: String = "/project/$name") = AtomCatalog.fileIcon(name, path)?.substringAfterLast('/')
    private fun folder(name: String) = AtomCatalog.folderIcon(name, "/project/$name")?.substringAfterLast('/')

    @Test
    fun `official icons for web, build and cloud files`() {
        assertEquals("html.svg", file("home.html"))
        assertEquals("css.svg", file("home.css"))
        assertEquals("typeScript.svg", file("home.ts"))
        assertEquals("docker.svg", file("Dockerfile"))
        assertEquals("dockercompose.svg", file("docker-compose.yml"))
        assertEquals("maven.svg", file("pom.xml"))
        assertEquals("gradle.svg", file("build.gradle"))
        assertEquals("gradlekts.svg", file("build.gradle.kts"))
        assertEquals("kubernetes.svg", file("k8s.yaml"))
        assertEquals("helmcharts.svg", file("Chart.yaml"))
        assertEquals("azurepipelines.svg", file("azure-pipelines.yml"))
        assertEquals("bicep.svg", file("main.bicep"))
    }

    @Test
    fun `files from your screenshots`() {
        assertEquals("testts.svg", file("home.spec.ts"))
        assertEquals("claude.svg", file("CLAUDE.md"))
        assertEquals("makefile.svg", file("Makefile"))
        assertEquals("shadcn.svg", file("components.json"))
        assertEquals("postcssconfig.svg", file("postcss.config.mjs"))
        assertEquals("i18nlang.svg", file("fr.ts", "/project/src/i18n/fr.ts"))
        assertEquals("powerpoint.svg", file("deck.pptx"))
        assertEquals("word.svg", file("notes.docx"))
    }

    @Test
    fun `each image format has its own icon`() {
        assertEquals("png.svg", file("logo.png"))
        assertEquals("jpg.svg", file("photo.jpg"))
        assertEquals("gif.svg", file("spinner.gif"))
        assertEquals("webp.svg", file("hero.webp"))
        assertEquals("svg.svg", file("logo.svg"))
    }

    @Test
    fun `matching ignores case`() {
        assertEquals(file("dockerfile"), file("DOCKERFILE"))
        assertEquals(folder("node_modules"), folder("Node_Modules"))
    }

    @Test
    fun `folders from your screenshots`() {
        assertEquals("node.svg", folder("node_modules"))
        assertEquals("src.svg", folder("src"))
        assertEquals("app.svg", folder("app"))
        assertEquals("idea.svg", folder(".idea"))
        assertEquals("i18n.svg", folder("i18n"))
        assertEquals("tests.svg", folder("test"))
        assertEquals("server.svg", folder("backend"))
        assertEquals("users.svg", folder("contact"))
        assertEquals("kubernetes.svg", folder("k8s"))
        assertEquals("azure-devops.svg", folder(".azure"))
    }

    @Test
    fun `unknown names get no icon`() {
        assertNull(file("notes.unknownextension"))
        assertNull(folder("blog-posts-2026"))
    }
}
