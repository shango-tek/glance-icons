package io.github.shangotek.glanceicons

import com.intellij.ide.IconProvider
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import javax.swing.Icon

/**
 * Icons for the project tree, which asks each PSI file and directory for its icon.
 */
class GlanceIconProvider : IconProvider(), DumbAware {

    override fun getIcon(element: PsiElement, flags: Int): Icon? = when (element) {
        is PsiDirectory -> folderIcon(element)
        is PsiFile -> fileIcon(element)
        else -> null
    }

    private fun fileIcon(file: PsiFile): Icon? {
        val virtualFile = file.virtualFile
        return (if (virtualFile != null) FileIcons.of(virtualFile) else FileIcons.byName(file.name))?.let(Icons::load)
    }

    private fun folderIcon(directory: PsiDirectory): Icon? {
        val folder = directory.virtualFile
        val icon = IconRules.folderIcon(folder.name) ?: AtomCatalog.folderIcon(folder.name, folder.path) ?: return null
        // Content roots and source roots keep the IDE's icon, because their look carries meaning.
        val index = ProjectFileIndex.getInstance(directory.project)
        if (index.getContentRootForFile(folder) == folder || index.getSourceRootForFile(folder) == folder) return null
        return Icons.load(icon)
    }
}
