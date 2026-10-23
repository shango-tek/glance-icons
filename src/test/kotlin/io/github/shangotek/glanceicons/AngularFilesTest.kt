package io.github.shangotek.glanceicons

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AngularFilesTest {

    @Test
    fun `component is recognised by its decorator`() {
        val source = """
            import { Component, input } from '@angular/core';

            @Component({
              selector: 'app-user-card',
              templateUrl: './user-card.html',
            })
            export class UserCard {
              readonly name = input.required<string>();
            }
        """.trimIndent()

        assertEquals(FileIcon.COMPONENT, AngularFiles.classify(source))
    }

    @Test
    fun `service is recognised by its decorator`() {
        assertEquals(FileIcon.SERVICE, AngularFiles.classify("@Injectable({ providedIn: 'root' })\nexport class User {}"))
    }

    @Test
    fun `angular 22 service decorator is a service`() {
        assertEquals(FileIcon.SERVICE, AngularFiles.classify("@Service()\nexport class Cart {\n  private http = inject(HttpClient);\n}"))
    }

    @Test
    fun `ng module is recognised by its decorator`() {
        assertEquals(FileIcon.MODULE, AngularFiles.classify("@NgModule({ declarations: [] })\nexport class LegacyModule {}"))
    }

    @Test
    fun `class based guards, interceptors and resolvers are recognised by what they implement`() {
        assertEquals(FileIcon.GUARD, AngularFiles.classify("@Injectable()\nexport class AuthGuard implements CanActivate, CanMatch {}"))
        assertEquals(FileIcon.INTERCEPTOR, AngularFiles.classify("@Injectable()\nexport class LogInterceptor implements HttpInterceptor {}"))
        assertEquals(FileIcon.ROUTES, AngularFiles.classify("@Injectable()\nexport class UserResolver implements Resolve<User> {}"))
    }

    @Test
    fun `directive and pipe are recognised by their decorator`() {
        assertEquals(FileIcon.DIRECTIVE, AngularFiles.classify("@Directive({ selector: '[appHighlight]' })\nexport class Highlight {}"))
        assertEquals(FileIcon.PIPE, AngularFiles.classify("@Pipe({ name: 'dateDe' })\nexport class DateDePipe {}"))
    }

    @Test
    fun `functional guards, interceptors and signal stores are recognised by their type`() {
        assertEquals(FileIcon.GUARD, AngularFiles.classify("export const authGuard: CanActivateFn = () => true;"))
        assertEquals(FileIcon.INTERCEPTOR, AngularFiles.classify("export const authInterceptor: HttpInterceptorFn = (req, next) => next(req);"))
        assertEquals(FileIcon.STORE, AngularFiles.classify("export const UsersStore = signalStore(withState({ users: [] }));"))
    }

    @Test
    fun `component wins over a helper service in the same file`() {
        assertEquals(FileIcon.COMPONENT, AngularFiles.classify("@Injectable()\nclass Helper {}\n@Component({ selector: 'x' })\nclass X {}"))
    }

    @Test
    fun `importing angular alone is no building block`() {
        assertEquals(FileIcon.TYPESCRIPT, AngularFiles.classify("export interface User { name: string }\nimport { Component } from '@angular/core';"))
        assertNull(AngularFiles.classify("export const sum = (a: number, b: number) => a + b;"))
    }
}
