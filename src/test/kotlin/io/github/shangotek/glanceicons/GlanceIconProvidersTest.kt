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

        assertSame(Icons.load(FileIcon.COMPONENT.path), GlanceFileIconProvider().getIcon(psiFile.virtualFile, 0, project))
        assertSame(Icons.load(FileIcon.COMPONENT.path), GlanceIconProvider().getIcon(psiFile, 0))
    }

    fun `test atom catalogue covers everything else`() {
        val html = myFixture.addFileToProject("web/home.html", "").virtualFile
        val docker = myFixture.addFileToProject("Dockerfile", "").virtualFile

        assertSame(Icons.load("/atom/files/html.svg"), GlanceFileIconProvider().getIcon(html, 0, project))
        assertSame(Icons.load("/atom/files/docker.svg"), GlanceFileIconProvider().getIcon(docker, 0, project))
    }

    fun `test angular 2025 files are recognised by their contents`() {
        val component = myFixture.addFileToProject("app/user-card.ts", "@Component({ selector: 'app-user-card' })\nexport class UserCard {}")
        val service = myFixture.addFileToProject("app/user.ts", "@Service()\nexport class User {}")
        val model = myFixture.addFileToProject("app/user-model.ts", "export interface UserModel { name: string }")

        assertSame(Icons.load(FileIcon.COMPONENT.path), GlanceFileIconProvider().getIcon(component.virtualFile, 0, project))
        assertSame(Icons.load(FileIcon.COMPONENT.path), GlanceIconProvider().getIcon(component, 0))
        assertSame(Icons.load(FileIcon.SERVICE.path), GlanceFileIconProvider().getIcon(service.virtualFile, 0, project))
        assertSame(Icons.load(FileIcon.TYPESCRIPT.path), GlanceFileIconProvider().getIcon(model.virtualFile, 0, project))
    }

    fun `test angular code beats the catalogue's guesses from the name`() {
        val root = myFixture.addFileToProject("app/app.ts", "@Component({ selector: 'app-root' })\nexport class App {}").virtualFile
        val layout = myFixture.addFileToProject("app/layout/layout.ts", "@Component({ selector: 'app-layout' })\nexport class Layout {}").virtualFile
        val config = myFixture.addFileToProject("app/app.config.ts", "import { ApplicationConfig } from '@angular/core';\nexport const appConfig: ApplicationConfig = { providers: [] };").virtualFile
        val nodeServer = myFixture.addFileToProject("api/server.ts", "import express from 'express';\nexpress().listen(3000);").virtualFile
        val spec = myFixture.addFileToProject("app/app.spec.ts", "@Component({ template: '' })\nclass TestHost {}").virtualFile

        assertSame(Icons.load(FileIcon.COMPONENT.path), GlanceFileIconProvider().getIcon(root, 0, project))
        assertSame(Icons.load(FileIcon.COMPONENT.path), GlanceFileIconProvider().getIcon(layout, 0, project))
        assertSame(Icons.load(FileIcon.TYPESCRIPT.path), GlanceFileIconProvider().getIcon(config, 0, project))
        assertSame(Icons.load(FileIcons.NODE), GlanceFileIconProvider().getIcon(nodeServer, 0, project))
        assertSame(Icons.load("/atom/files/testts.svg"), GlanceFileIconProvider().getIcon(spec, 0, project))
    }

    fun `test kubernetes manifests are recognised by their contents`() {
        val manifest = myFixture.addFileToProject("deploy/web.yaml", "apiVersion: apps/v1\nkind: Deployment\nmetadata:\n  name: web\n")
        val plain = myFixture.addFileToProject("deploy/values-notes.yaml", "replicas: 3\n")

        assertSame(Icons.load(FileIcons.KUBERNETES), GlanceFileIconProvider().getIcon(manifest.virtualFile, 0, project))
        assertSame(Icons.load(FileIcons.YAML), GlanceFileIconProvider().getIcon(plain.virtualFile, 0, project))
    }

    fun `test github workflows are told apart from other yaml`() {
        val workflow = myFixture.addFileToProject(".github/workflows/release.yml", "on: push").virtualFile
        val other = myFixture.addFileToProject("deploy/settings-notes.yml", "a: 1").virtualFile

        assertSame(Icons.load(FileIcons.GITHUB_ACTIONS), GlanceFileIconProvider().getIcon(workflow, 0, project))
        assertSame(Icons.load(FileIcons.YAML), GlanceFileIconProvider().getIcon(other, 0, project))
    }

    fun `test icon follows the file contents after saving`() {
        val file = myFixture.addFileToProject("app/highlight.ts", "export const x = 1;").virtualFile
        assertSame(Icons.load(FileIcon.TYPESCRIPT.path), GlanceFileIconProvider().getIcon(file, 0, project))

        WriteAction.runAndWait<Throwable> { VfsUtil.saveText(file, "@Directive({ selector: '[appHighlight]' })\nexport class Highlight {}") }

        assertSame(Icons.load(FileIcon.DIRECTIVE.path), GlanceFileIconProvider().getIcon(file, 0, project))
    }

    fun `test named folders get their icon`() {
        val components = myFixture.tempDirFixture.findOrCreateDir("components")
        val directives = myFixture.tempDirFixture.findOrCreateDir("directives")

        assertSame(Icons.load("/atom/folders/components.svg"), GlanceIconProvider().getIcon(psiDirectory(components), 0))
        assertSame(Icons.load(DIRECTIVES_FOLDER), GlanceIconProvider().getIcon(psiDirectory(directives), 0))
        assertNull(GlanceFileIconProvider().getIcon(components, 0, project))
    }

    fun `test folders of angular components get the component folder icon`() {
        myFixture.addFileToProject("app/accordion/accordion.ts", "@Component({ selector: 'app-accordion' })\nexport class Accordion {}")
        myFixture.addFileToProject("app/old-card/old-card.component.ts", "")
        myFixture.addFileToProject("app/step-list/step-list.ts", "export const steps = ['cart', 'payment'];")
        myFixture.addFileToProject("app/app.ts", "@Component({ selector: 'app-root' })\nexport class App {}")

        val icon = { path: String -> GlanceIconProvider().getIcon(psiDirectory(myFixture.findFileInTempDir(path)), 0) }
        assertSame(Icons.load(COMPONENT_FOLDER), icon("app/accordion"))
        assertSame(Icons.load(COMPONENT_FOLDER), icon("app/old-card"))
        assertNull(icon("app/step-list"))
        // A name with its own icon wins: app/ holds the root component but keeps the app folder icon.
        assertSame(Icons.load("/atom/folders/app.svg"), icon("app"))
    }

    fun `test source root keeps the IDE icon`() {
        // The light test project's only source root is called "src", a name the rules would otherwise match.
        val sourceRoot = ModuleRootManager.getInstance(module).sourceRoots.single()
        assertEquals("src", sourceRoot.name)

        assertNull(GlanceIconProvider().getIcon(psiDirectory(sourceRoot), 0))
    }

    private fun psiDirectory(folder: VirtualFile): PsiDirectory = PsiManager.getInstance(project).findDirectory(folder)!!
}
