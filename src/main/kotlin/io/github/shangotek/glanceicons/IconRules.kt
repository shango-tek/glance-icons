package io.github.shangotek.glanceicons

/**
 * Glance's own icons, checked before the [AtomCatalog]: README files, the Angular family and the Java/Kotlin set.
 *
 * Files are matched by name, first rule wins:
 * 1. exact name (`angular.json`)
 * 2. test class suffix, case-sensitive (`UserTest.java`, but not `Contest.java`)
 * 3. README files (`README`, `README.md`, `README.de.md`), but not code such as `readme-parser.ts`
 * 4. Angular suffix, longest first (`user.component.ts`, `auth-guard.ts`, `app-routing-module.ts`)
 * 5. extension (`.java`, `.kt`), except Gradle scripts, which get Atom's Gradle icon
 *
 * Angular files named after the 2025 style guide (`user-card.ts`) look like plain TypeScript here;
 * [FileIcons] refines those by reading the decorator.
 */
object IconRules {

    fun fileIcon(fileName: String): FileIcon? {
        FILE_NAMES[fileName.lowercase()]?.let { return it }
        TEST_CLASS_SUFFIXES.firstOrNull { fileName.endsWith(it.first) }?.let { return it.second }
        val name = fileName.lowercase()
        if (isReadme(name)) return FileIcon.README
        if (name.endsWith(".gradle.kts")) return null
        ANGULAR_SUFFIXES.firstOrNull { name.endsWith(it.first) }?.let { return it.second }
        return EXTENSIONS[name.substringAfterLast('.', "")]
    }

    /** `readme`, optionally followed by `.`, `_` or `-` and more, in a text format or without an extension. */
    private fun isReadme(name: String): Boolean {
        val rest = name.removePrefix("readme")
        if (rest == name) return false
        return rest.isEmpty() || rest[0] in "._-" && ('.' !in rest || name.substringAfterLast('.') in README_FORMATS)
    }

    /** Angular-only folder names that Atom has no icon for. */
    fun folderIcon(folderName: String): String? = FOLDER_NAMES[folderName.lowercase()]

    /** Every icon a name rule can produce, so tests can prove that no icon is unreachable. */
    internal val mappedFileIcons: Set<FileIcon>
        get() = (FILE_NAMES.values + TEST_CLASS_SUFFIXES.map { it.second } + FileIcon.README + ANGULAR_SUFFIXES.map { it.second } + EXTENSIONS.values).toSet()

    internal val mappedFolderIcons: Set<String>
        get() = FOLDER_NAMES.values.toSet()

    private val FILE_NAMES = rules(
        FileIcon.ANGULAR to listOf("angular.json", "angular-cli.json", ".angular-cli.json"),
    ).toMap()

    private val TEST_CLASS_SUFFIXES = rules(
        FileIcon.JAVA_TEST to listOf("Test.java", "Tests.java", "IT.java", "TestCase.java"),
        FileIcon.KOTLIN_TEST to listOf("Test.kt", "Tests.kt", "IT.kt"),
    )

    private val README_FORMATS = setOf("md", "markdown", "mdx", "txt", "text", "rst", "adoc", "asciidoc", "org", "html")

    // Angular's 2025 style guide joins pipe, guard, interceptor, resolver and module suffixes with "-" instead of ".".
    private val ANGULAR_SUFFIXES = rules(
        FileIcon.COMPONENT to listOf(".component.ts"),
        FileIcon.SERVICE to listOf(".service.ts"),
        FileIcon.PIPE to listOf(".pipe.ts", "-pipe.ts"),
        FileIcon.DIRECTIVE to listOf(".directive.ts"),
        FileIcon.GUARD to listOf(".guard.ts", "-guard.ts"),
        FileIcon.INTERCEPTOR to listOf(".interceptor.ts", "-interceptor.ts"),
        FileIcon.MODULE to listOf(".module.ts", "-module.ts"),
        FileIcon.STORE to listOf(".store.ts", ".state.ts", ".reducer.ts", ".actions.ts", ".effects.ts", ".selectors.ts"),
        FileIcon.MODEL to listOf(".model.ts", ".interface.ts", ".types.ts", ".type.ts", ".enum.ts", ".dto.ts", ".entity.ts", ".data.ts"),
        FileIcon.ROUTES to listOf(
            ".routes.ts", ".routes.server.ts", ".routing.ts", "-routing.module.ts", ".routing.module.ts", "-routing-module.ts",
            ".resolver.ts", "-resolver.ts",
        ),
    ).sortedByDescending { it.first.length }

    private val EXTENSIONS = rules(
        FileIcon.JAVA to listOf("java"),
        FileIcon.KOTLIN to listOf("kt", "kts"),
    ).toMap()

    private val FOLDER_NAMES = mapOf("directives" to DIRECTIVES_FOLDER, "directive" to DIRECTIVES_FOLDER)

    private fun <T> rules(vararg groups: Pair<T, List<String>>): List<Pair<String, T>> =
        groups.flatMap { (icon, keys) -> keys.map { it to icon } }
}
