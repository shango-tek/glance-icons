package io.github.shangotek.glanceicons

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class IconCoverageTest {

    @Test
    fun `every file icon is reachable`() {
        // GitHub Actions is matched by its folder in FileIcons rather than by name.
        val reachable = IconRules.mappedFileIcons + FileIcon.GITHUB_ACTIONS
        assertEquals(emptySet<FileIcon>(), FileIcon.entries.toSet() - reachable)
    }

    @Test
    fun `every folder icon is reachable`() {
        assertEquals(emptySet<FolderIcon>(), FolderIcon.entries.toSet() - IconRules.mappedFolderIcons)
    }

    @Test
    fun `every svg belongs to an icon`() {
        val known = FileIcon.entries.map { it.path } + FolderIcon.entries.map { it.path }
        for (kind in listOf("files", "folders")) {
            val svgs = File("src/main/resources/icons/$kind").listFiles { f -> f.name.endsWith(".svg") }!!
            for (svg in svgs) {
                // name_dark.svg is the dark-theme variant of name.svg.
                val base = "/icons/$kind/${svg.name.removeSuffix(".svg").removeSuffix("_dark")}.svg"
                assertTrue("${svg.name} is not used by any icon", base in known)
            }
        }
    }
}
