package io.github.shangotek.glanceicons

import com.intellij.openapi.vfs.VirtualFile

/**
 * The icon for a real file: [IconRules] by name, plus the cases a name alone can't decide.
 */
object FileIcons {

    fun of(file: VirtualFile): FileIcon? {
        if (isGitHubWorkflow(file)) return FileIcon.GITHUB_ACTIONS
        val byName = IconRules.fileIcon(file.name)
        if (byName != FileIcon.TYPESCRIPT || file.name.endsWith(".d.ts")) return byName
        return AngularFiles.kindOf(file) ?: byName
    }

    private fun isGitHubWorkflow(file: VirtualFile): Boolean {
        val name = file.name
        val parent = file.parent ?: return false
        return (name.endsWith(".yml") || name.endsWith(".yaml")) && parent.name == "workflows" && parent.parent?.name == ".github"
    }
}
