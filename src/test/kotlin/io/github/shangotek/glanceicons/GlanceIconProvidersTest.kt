package io.github.shangotek.glanceicons

import com.intellij.ide.FileIconProvider
import com.intellij.ide.IconProvider
import com.intellij.ide.projectView.ProjectViewNodeDecorator
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class GlanceIconProvidersTest : BasePlatformTestCase() {

    fun `test providers are registered`() {
        assertTrue(IconProvider.EXTENSION_POINT_NAME.extensionList.any { it is GlanceIconProvider })
        assertTrue(FileIconProvider.EP_NAME.extensionList.any { it is GlanceFileIconProvider })
        // Looked up by ID: the static EP field only exists before 2026.2.
        val decorators = project.extensionArea.getExtensionPoint<ProjectViewNodeDecorator>("com.intellij.projectViewNodeDecorator")
        assertTrue(decorators.extensionList.any { it is GlanceClassNodeDecorator })
    }

    fun `test file icon in tabs and project tree`() {
        val psiFile = myFixture.addFileToProject("app/user-card.component.ts", "")

        assertSame(FileIcon.COMPONENT.icon, GlanceFileIconProvider().getIcon(psiFile.virtualFile, 0, project))
        assertSame(FileIcon.COMPONENT.icon, GlanceIconProvider().getIcon(psiFile, 0))
    }

    fun `test angular 2025 files are recognised by their contents`() {
        val component = myFixture.addFileToProject("app/user-card.ts", "@Component({ selector: 'app-user-card' })\nexport class UserCard {}")
        val service = myFixture.addFileToProject("app/user.ts", "@Injectable({ providedIn: 'root' })\nexport class User {}")
        val model = myFixture.addFileToProject("app/user-model.ts", "export interface UserModel { name: string }")

        assertSame(FileIcon.COMPONENT.icon, GlanceFileIconProvider().getIcon(component.virtualFile, 0, project))
        assertSame(FileIcon.COMPONENT.icon, GlanceIconProvider().getIcon(component, 0))
        assertSame(FileIcon.SERVICE.icon, GlanceFileIconProvider().getIcon(service.virtualFile, 0, project))
        assertSame(FileIcon.TYPESCRIPT.icon, GlanceFileIconProvider().getIcon(model.virtualFile, 0, project))
    }

    fun `test github workflows are told apart from other yaml`() {
        val workflow = myFixture.addFileToProject(".github/workflows/ci.yml", "on: push").virtualFile
        val other = myFixture.addFileToProject("config/ci.yml", "a: 1").virtualFile

        assertSame(FileIcon.GITHUB_ACTIONS.icon, GlanceFileIconProvider().getIcon(workflow, 0, project))
        assertSame(FileIcon.YAML.icon, GlanceFileIconProvider().getIcon(other, 0, project))
    }

    fun `test icon follows the file contents after saving`() {
        val file = myFixture.addFileToProject("app/highlight.ts", "export const x = 1;").virtualFile
        assertSame(FileIcon.TYPESCRIPT.icon, GlanceFileIconProvider().getIcon(file, 0, project))

        WriteAction.runAndWait<Throwable> { VfsUtil.saveText(file, "@Directive({ selector: '[appHighlight]' })\nexport class Highlight {}") }

        assertSame(FileIcon.DIRECTIVE.icon, GlanceFileIconProvider().getIcon(file, 0, project))
    }

    fun `test named folder gets its icon`() {
        val folder = myFixture.tempDirFixture.findOrCreateDir("components")

        assertSame(FolderIcon.COMPONENTS.icon, GlanceIconProvider().getIcon(psiDirectory(folder), 0))
        assertNull(GlanceFileIconProvider().getIcon(folder, 0, project))
    }

    fun `test source root keeps the IDE icon`() {
        // The light test project's only source root is called "src", a name the rules would otherwise match.
        val sourceRoot = ModuleRootManager.getInstance(module).sourceRoots.single()
        assertEquals("src", sourceRoot.name)

        assertNull(GlanceIconProvider().getIcon(psiDirectory(sourceRoot), 0))
    }

    private fun psiDirectory(folder: VirtualFile): PsiDirectory = PsiManager.getInstance(project).findDirectory(folder)!!
}
