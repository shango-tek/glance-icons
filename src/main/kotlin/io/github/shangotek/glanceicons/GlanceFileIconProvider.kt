package io.github.shangotek.glanceicons

import com.intellij.ide.FileIconProvider
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon

/**
 * Icons for places that only know the virtual file, such as editor tabs, Recent Files and Search Everywhere.
 * Folders are left to [GlanceIconProvider], which can see the project roots.
 */
class GlanceFileIconProvider : FileIconProvider, DumbAware {

    override fun getIcon(file: VirtualFile, flags: Int, project: Project?): Icon? =
        if (file.isDirectory) null else FileIcons.of(file)?.icon
}
