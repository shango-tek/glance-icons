package io.github.shangotek.glanceicons

import com.intellij.openapi.vfs.VirtualFile

/**
 * Recognises Angular files named after the 2025 style guide, where `ng generate` no longer adds
 * `.component`, `.directive` or `.service` to the file name, by finding the decorator or type in the source.
 */
object AngularFiles {

    /** The Angular building block a source declares, [FileIcon.TYPESCRIPT] for other code that imports Angular, or null. */
    fun classify(source: CharSequence): FileIcon? =
        KINDS.firstOrNull { it.first.containsMatchIn(source) }?.second ?: FileIcon.TYPESCRIPT.takeIf { IMPORTS_ANGULAR.containsMatchIn(source) }

    /** [classify] for a file, recomputed only after the file is saved with new content. */
    fun kindOf(file: VirtualFile): FileIcon? = cache.get(file)

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

    private val IMPORTS_ANGULAR = Regex("""\bfrom\s*['"]@angular/""")

    private val cache = ContentCache("glance.icons.angularKind", ::classify)
}
