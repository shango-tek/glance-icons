package io.github.shangotek.glanceicons

import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import java.io.IOException

/**
 * Recognises Angular files named after the 2025 style guide, where `ng generate` no longer adds
 * `.component`, `.directive` or `.service` to the file name, by finding the decorator or type in the source.
 */
object AngularFiles {

    fun classify(source: CharSequence): FileIcon? = KINDS.firstOrNull { it.first.containsMatchIn(source) }?.second

    /** What a plain TypeScript file declares, cached on the file and recomputed only after it is saved with new content. */
    fun kindOf(file: VirtualFile): FileIcon? {
        val stamp = file.modificationStamp
        file.getUserData(KIND)?.let { if (it.stamp == stamp) return it.icon }
        val icon = if (file.length > MAX_SIZE) null else classify(readText(file))
        file.putUserData(KIND, CachedKind(stamp, icon))
        return icon
    }

    private fun readText(file: VirtualFile): String =
        try {
            String(file.contentsToByteArray(), Charsets.UTF_8)
        } catch (e: IOException) {
            ""
        }

    private class CachedKind(val stamp: Long, val icon: FileIcon?)

    private val KIND = Key.create<CachedKind>("glance.icons.angularKind")

    // Angular building blocks are small; skipping huge files keeps the project tree fast.
    private const val MAX_SIZE = 256 * 1024

    // First match wins, so a component that also declares an @Injectable helper stays a component.
    private val KINDS = listOf(
        Regex("""@Component\s*\(""") to FileIcon.COMPONENT,
        Regex("""@Directive\s*\(""") to FileIcon.DIRECTIVE,
        Regex("""@Pipe\s*\(""") to FileIcon.PIPE,
        Regex("""@NgModule\s*\(""") to FileIcon.MODULE,
        Regex("""\b(CanActivate|CanActivateChild|CanDeactivate|CanMatch)Fn\b|\bimplements\b[^{]*\bCan(Activate|ActivateChild|Deactivate|Match|Load)\b""") to FileIcon.GUARD,
        Regex("""\bHttpInterceptorFn\b|\bimplements\b[^{]*\bHttpInterceptor\b""") to FileIcon.INTERCEPTOR,
        Regex("""\bResolveFn\b|\bimplements\b[^{]*\bResolve\s*<""") to FileIcon.ROUTES,
        Regex("""\b(signalStore|createReducer|createEffect|createFeature)\s*\(""") to FileIcon.STORE,
        // @Service() is Angular 22's shorthand for @Injectable({ providedIn: 'root' }).
        Regex("""@(Service|Injectable)\s*\(""") to FileIcon.SERVICE,
    )
}
