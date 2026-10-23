package io.github.shangotek.glanceicons

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class IconCoverageTest {

    @Test
    fun `every glance file icon is reachable`() {
        // Plain TypeScript gets its icon in FileIcons rather than from a name rule.
        assertEquals(emptySet<FileIcon>(), FileIcon.entries.toSet() - IconRules.mappedFileIcons - FileIcon.TYPESCRIPT)
    }

    @Test
    fun `every icon a rule names exists`() {
        val paths = FileIcon.entries.map { it.path } + IconRules.mappedFolderIcons + COMPONENT_FOLDER +
            listOf(FileIcons.ATOM_TYPESCRIPT, FileIcons.NODE, FileIcons.EXPO, FileIcons.YAML, FileIcons.GITHUB_ACTIONS, FileIcons.KUBERNETES) +
            (AtomCatalog.files.byName + AtomCatalog.files.byPath + AtomCatalog.folders.byName + AtomCatalog.folders.byPath).map { it.icon }
        paths.distinct().forEach { assertNotNull("Missing $it", javaClass.getResource(it)) }
    }

    @Test
    fun `every bundled svg is used`() {
        val known = (FileIcon.entries.map { it.path } + IconRules.mappedFolderIcons + COMPONENT_FOLDER +
            (AtomCatalog.files.byName + AtomCatalog.files.byPath + AtomCatalog.folders.byName + AtomCatalog.folders.byPath).map { it.icon }).toSet()
        for (dir in listOf("icons/files", "icons/folders", "atom/files", "atom/folders")) {
            val svgs = File("src/main/resources/$dir").listFiles { f -> f.name.endsWith(".svg") }!!
            for (svg in svgs) {
                // name_dark.svg and name@2x.svg are the dark-theme and Retina variants of name.svg.
                val base = "/$dir/${svg.name.removeSuffix(".svg").removeSuffix("_dark").removeSuffix("@2x")}.svg"
                assertTrue("${svg.name} in $dir is not used by any rule", base in known)
            }
        }
    }

    // IntelliJ takes an icon's size from the file it draws, so Retina (@2x) and dark variants must say 16 x 16 as well.
    @Test
    fun `every svg declares 16 by 16`() {
        val wrong = listOf("icons/files", "icons/folders", "atom/files", "atom/folders")
            .flatMap { dir -> File("src/main/resources/$dir").listFiles { f -> f.name.endsWith(".svg") }!!.toList() }
            .map { it.path to declaredSize(it.readText()) }
            .filter { it.second != "16x16" }
        assertEquals(emptyList<Pair<String, String>>(), wrong)
    }

    private fun declaredSize(svg: String): String {
        val tag = Regex("<svg\\b[^>]*>").find(svg)?.value.orEmpty()
        fun attribute(name: String) = Regex("(?<![\\w-])$name=\"([^\"]*)\"").find(tag)?.groupValues?.get(1)
        return "${attribute("width")}x${attribute("height")}"
    }

    @Test
    fun `atom catalogue is fully loaded`() {
        assertTrue(AtomCatalog.files.byName.size + AtomCatalog.files.byPath.size > 1700)
        assertTrue(AtomCatalog.folders.byName.size + AtomCatalog.folders.byPath.size > 300)
    }
}
