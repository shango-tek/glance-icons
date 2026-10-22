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
        return (if (virtualFile != null) FileIcons.of(virtualFile) else IconRules.fileIcon(file.name))?.icon
    }

    private fun folderIcon(directory: PsiDirectory): Icon? {
        val icon = IconRules.folderIcon(directory.name) ?: return null
        // Content roots, source roots and excluded folders keep the IDE's icon, because their look carries meaning.
        val folder = directory.virtualFile
        val index = ProjectFileIndex.getInstance(directory.project)
        if (index.isExcluded(folder) || index.getContentRootForFile(folder) == folder || index.getSourceRootForFile(folder) == folder) {
            return null
        }
        return icon.icon
    }
}
