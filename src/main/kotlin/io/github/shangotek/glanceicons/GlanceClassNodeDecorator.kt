package io.github.shangotek.glanceicons

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.ide.projectView.ProjectViewNodeDecorator
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiNamedElement

/**
 * A Java or Kotlin file holding one class shows up in the project tree as that class rather than as a file,
 * so the file icon never reaches the tree. This puts the language icon back on that class node.
 */
class GlanceClassNodeDecorator : ProjectViewNodeDecorator {

    override fun decorate(node: ProjectViewNode<*>, data: PresentationData) {
        val declaration = node.value as? PsiNamedElement ?: return
        if (declaration is PsiFile || declaration.parent !is PsiFile) return
        val file = node.virtualFile ?: return
        if (declaration.name != file.nameWithoutExtension) return
        val icon = IconRules.fileIcon(file.name)?.takeIf { it in CLASS_FILE_ICONS } ?: return
        data.setIcon(icon.icon)
    }

    private companion object {
        val CLASS_FILE_ICONS = setOf(FileIcon.JAVA, FileIcon.JAVA_TEST, FileIcon.KOTLIN, FileIcon.KOTLIN_TEST)
    }
}
