package io.github.shangotek.glanceicons

import com.intellij.openapi.util.IconLoader
import java.util.concurrent.ConcurrentHashMap
import javax.swing.Icon

/**
 * Glance's own file icons, loaded from `/icons/files/<name>.svg`, where `<name>` is the lowercase entry name.
 * Everything else comes from the [AtomCatalog].
 */
enum class FileIcon {
    // Angular: a C with the Angular logo for components, a drawing of their own for the other building blocks
    COMPONENT, SERVICE, DIRECTIVE, PIPE, GUARD, INTERCEPTOR, ROUTES, MODULE, STORE, MODEL,

    // The new Angular logo, for angular.json
    ANGULAR,

    // Pixel-sharp TypeScript, used for every plain .ts file instead of the catalogue's
    TYPESCRIPT,

    // The blue info sign, for README files
    README,

    // Java and Kotlin, with a test badge for test classes
    JAVA, JAVA_TEST, KOTLIN, KOTLIN_TEST;

    val path: String = "/icons/files/${name.lowercase()}.svg"
}

/** Angular's directives folder, drawn in Atom's folder style because Atom has none. */
const val DIRECTIVES_FOLDER = "/icons/folders/directives.svg"

/** A folder that holds one Angular component, such as `accordion/` with `accordion.ts`; see [AngularFiles.isComponentFolder]. */
const val COMPONENT_FOLDER = "/icons/folders/component.svg"

/** Loads each icon once. IntelliJ uses `<name>_dark.svg` in dark themes and `<name>@2x.svg` on Retina screens when they exist. */
object Icons {

    private val cache = ConcurrentHashMap<String, Icon>()

    fun load(path: String): Icon = cache.computeIfAbsent(path) { IconLoader.getIcon(it, Icons::class.java) }
}
