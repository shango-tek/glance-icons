package io.github.shangotek.glanceicons

import com.intellij.openapi.vfs.VirtualFile

/**
 * Resource path of the icon for a real file: Glance's own rules first, then the Atom catalogue, then the cases
 * that need the file's location or contents.
 */
object FileIcons {

    fun of(file: VirtualFile): String? {
        val name = file.name
        if (isGitHubWorkflow(file)) return GITHUB_ACTIONS
        IconRules.fileIcon(name)?.let { return it.path }
        val atom = AtomCatalog.fileIcon(name, file.path)
        val angular = if (mayDeclareAngular(name)) AngularFiles.kindOf(file) else null
        // A building block found in the code beats any guess from the name, such as Node.js for app.ts or Next.js for layout.ts.
        if (angular != null && angular != FileIcon.TYPESCRIPT) return angular.path
        // Plain TypeScript gets the pixel-sharp TS icon, and so does Angular code the catalogue takes for another framework.
        if (atom == ATOM_TYPESCRIPT || angular == FileIcon.TYPESCRIPT && isOtherFramework(atom)) return FileIcon.TYPESCRIPT.path
        // Plain YAML may be a Kubernetes manifest.
        if (atom == YAML || atom == null && isYaml(name)) kubernetes.get(file)?.let { return it }
        if (atom != null) return atom
        // Atom covers `.env` and `.env.local`, not longer chains such as `.env.local.prod`.
        if (name.startsWith(".env.", ignoreCase = true)) return AtomCatalog.fileIcon(".env")
        return null
    }

    /** For PSI files without a backing file, where only the name is known. */
    fun byName(name: String): String? =
        IconRules.fileIcon(name)?.path ?: AtomCatalog.fileIcon(name)?.let { if (it == ATOM_TYPESCRIPT) FileIcon.TYPESCRIPT.path else it }

    /** TypeScript code, except type declarations and the tests, stories and mocks that keep the catalogue's icon for their role. */
    private fun mayDeclareAngular(name: String): Boolean {
        val lower = name.lowercase()
        return lower.endsWith(".ts") && !lower.endsWith(".d.ts") && ROLE_SUFFIXES.none { lower.endsWith(it) }
    }

    // What the catalogue guesses from a bare name: Node.js for app.ts, main.ts and server.ts, Expo for app.config.ts,
    // Next.js for layout.ts, page.ts, error.ts, not-found.ts and route.ts. All of them are common Angular file names.
    private fun isOtherFramework(icon: String?) = icon == NODE || icon == EXPO || icon?.startsWith("/atom/files/next-") == true

    private val ROLE_SUFFIXES = listOf(".spec.ts", ".test.ts", ".e2e-spec.ts", ".cy.ts", ".stories.ts", ".mock.ts")

    private fun isGitHubWorkflow(file: VirtualFile): Boolean {
        val parent = file.parent ?: return false
        return isYaml(file.name) && parent.name == "workflows" && parent.parent?.name == ".github"
    }

    private fun isYaml(name: String) = name.endsWith(".yml", ignoreCase = true) || name.endsWith(".yaml", ignoreCase = true)

    internal const val ATOM_TYPESCRIPT = "/atom/files/typeScript.svg"
    internal const val NODE = "/atom/files/nodejs.svg"
    internal const val EXPO = "/atom/files/expo.svg"
    internal const val YAML = "/atom/files/yaml.svg"
    internal const val GITHUB_ACTIONS = "/atom/files/githubActions.svg"
    internal const val KUBERNETES = "/atom/files/kubernetes.svg"

    // Every Kubernetes manifest starts its documents with apiVersion and kind, whatever the file is called.
    private val API_VERSION = Regex("""(?m)^apiVersion:\s*\S""")
    private val KIND = Regex("""(?m)^kind:\s*\S""")
    private val kubernetes = ContentCache("glance.icons.kubernetes") { text ->
        if (API_VERSION.containsMatchIn(text) && KIND.containsMatchIn(text)) KUBERNETES else null
    }
}
