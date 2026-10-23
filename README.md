# Glance Icons

<img src="src/main/resources/META-INF/pluginIcon.svg" width="80" align="right" alt="Glance Icons logo">

Colourful, pixel-sharp file and folder icons for IntelliJ IDEA, WebStorm and every other JetBrains IDE, with icons that understand Angular, up to Angular 22's `@Service()` and the 2025 style guide where `ng generate` no longer puts `.component` or `.service` in file names.

- **1,700+ file types and 340+ folders**, so `Makefile`, `CLAUDE.md`, Office files, `contact`, `profile` and `infra` folders get icons too
- **Official icons** for HTML, CSS, TypeScript, Docker, Maven, Gradle, Kubernetes, Helm, Azure Pipelines, Bicep, React, Next.js and many more
- **Pixel-sharp folders**: every folder outline is redrawn on the 16 px grid, so its edges land on whole pixels instead of blurring between them
- **One icon per Angular type**, found by file name *or* by the decorator inside the file
- **Kubernetes manifests by content**: any YAML file with `apiVersion` and `kind` gets the Kubernetes icon, whatever it's called
- Java and Kotlin test classes get a test badge, in the project tree too
- Source roots and content roots keep the IDE's own icons, so you don't lose that information
- Free and open source (MIT)

Open [`docs/preview.html`](docs/preview.html) in a browser to see a sample project tree in the light and dark themes.

## Glance's own icons

Drawn on the 16 px grid, some of them after official logos (see [Credits](#credits-and-license)). They take priority over the catalogue. The TypeScript and component icons, files and folders, also have their own drawing for Retina screens.

| Icon | Used for |
| --- | --- |
| <img src="src/main/resources/icons/files/typescript.svg" width="16"> | TypeScript, pixel-sharp: `.ts` files without a more specific icon (tests, configs and the Angular types below have their own) |
| <img src="src/main/resources/icons/files/component.svg" width="16"> | Angular components, a TypeScript-blue C with the Angular logo: `*.component.ts`, or any `.ts` file with `@Component(…)`, such as `app.ts` |
| <img src="src/main/resources/icons/files/angular.svg" width="16"> | `angular.json`, with the new Angular logo |
| <img src="src/main/resources/icons/files/service.svg" width="16"> | Angular services: `*.service.ts`, `@Service()` (Angular 22) or `@Injectable(…)` |
| <img src="src/main/resources/icons/files/directive.svg" width="16"> | Directives: `*.directive.ts` or `@Directive(…)` |
| <img src="src/main/resources/icons/files/pipe.svg" width="16"> | Pipes: `*.pipe.ts`, `*-pipe.ts` or `@Pipe(…)` |
| <img src="src/main/resources/icons/files/guard.svg" width="16"> | Guards: `*.guard.ts`, `*-guard.ts`, `CanActivateFn` and friends, or `implements CanActivate` |
| <img src="src/main/resources/icons/files/interceptor.svg" width="16"> | Interceptors: `*.interceptor.ts`, `*-interceptor.ts`, `HttpInterceptorFn` or `implements HttpInterceptor` |
| <img src="src/main/resources/icons/files/routes.svg" width="16"> | Routes and resolvers: `*.routes.ts`, `app.routes.server.ts`, `*-routing-module.ts`, `*-resolver.ts`, `ResolveFn` |
| <img src="src/main/resources/icons/files/module.svg" width="16"> | Modules: `*.module.ts`, `*-module.ts` or `@NgModule(…)` |
| <img src="src/main/resources/icons/files/store.svg" width="16"> | State (NgRx logo): `*.store.ts`, NgRx files, `signalStore(…)`, `createReducer(…)` |
| <img src="src/main/resources/icons/files/model.svg" width="16"> | Models and data: `*.model.ts`, `*.interface.ts`, `*.types.ts`, `*.enum.ts`, `*.dto.ts`, `*.data.ts` |
| <img src="src/main/resources/icons/files/readme.svg" width="16"> | `README*`: `README`, `README.md`, `README.de.md`, `readme.txt`, … |
| <img src="src/main/resources/icons/files/java.svg" width="16"> | `.java` |
| <img src="src/main/resources/icons/files/java_test.svg" width="16"> | `*Test.java`, `*Tests.java`, `*IT.java` |
| <img src="src/main/resources/icons/files/kotlin.svg" width="16"> | `.kt`, `.kts` (Gradle scripts get the Gradle icon) |
| <img src="src/main/resources/icons/files/kotlin_test.svg" width="16"> | `*Test.kt`, `*Tests.kt`, `*IT.kt` |
| <img src="src/main/resources/icons/folders/directives.svg" width="16"> | `directives` folder (the catalogue has none) |
| <img src="src/main/resources/icons/folders/component.svg" width="16"> | Component folders, found by content: a folder named after the component inside it (`accordion/accordion.ts`, `card/card.component.ts`) |

Since Angular 20, `ng generate` names components, directives and services without a type suffix (`user-card.ts`), and joins the other suffixes with a hyphen (`auth-guard.ts`). File names alone can't tell those apart, so the plugin reads the decorator. The result is cached per file and refreshed when you save.

Folders work the same way: `ng generate` puts each component in a folder of its own name, so a folder whose same-named file is a component (`accordion/accordion.ts`) gets the component folder icon. A folder name that has an icon of its own keeps it, so `app/` stays the app folder although it holds the root component.

What the code declares also beats what the catalogue guesses from a bare name: it would show Node.js for `app.ts` and `main.ts`, Expo for `app.config.ts` and Next.js for `layout.ts` or `page.ts`. In Angular code, `app.ts` gets the component icon and `main.ts` and `app.config.ts` the TypeScript icon. Tests, stories and mocks (`*.spec.ts`, `*.stories.ts`, `*.mock.ts`) keep their own icons.

## A few of the catalogue's icons

| Icon | Used for | Icon | Used for |
| --- | --- | --- | --- |
| <img src="src/main/resources/atom/files/html.svg" width="16"> | `.html` | <img src="src/main/resources/atom/files/css.svg" width="16"> | `.css` |
| <img src="src/main/resources/atom/files/tsconfig.svg" width="16"> | `tsconfig.json` | <img src="src/main/resources/atom/files/testts.svg" width="16"> | `*.spec.ts` |
| <img src="src/main/resources/atom/files/docker.svg" width="16"> | `Dockerfile` | <img src="src/main/resources/atom/files/dockercompose.svg" width="16"> | `docker-compose.yml` |
| <img src="src/main/resources/atom/files/maven.svg" width="16"> | `pom.xml` | <img src="src/main/resources/atom/files/gradle.svg" width="16"> | `build.gradle` |
| <img src="src/main/resources/atom/files/kubernetes.svg" width="16"> | Kubernetes manifests | <img src="src/main/resources/atom/files/helmcharts.svg" width="16"> | `Chart.yaml` |
| <img src="src/main/resources/atom/files/azurepipelines.svg" width="16"> | `azure-pipelines.yml` | <img src="src/main/resources/atom/files/bicep.svg" width="16"> | `.bicep` |
| <img src="src/main/resources/atom/files/claude.svg" width="16"> | `CLAUDE.md` | <img src="src/main/resources/atom/files/makefile.svg" width="16"> | `Makefile` |
| <img src="src/main/resources/atom/folders/src.svg" width="16"> | `src` | <img src="src/main/resources/atom/folders/node.svg" width="16"> | `node_modules` |
| <img src="src/main/resources/atom/folders/kubernetes.svg" width="16"> | `k8s`, `kubernetes` | <img src="src/main/resources/atom/folders/azure-devops.svg" width="16"> | `.azure` |

The full rule set is in [`icon_associations.xml`](src/main/resources/atom/icon_associations.xml) and [`folder_associations.xml`](src/main/resources/atom/folder_associations.xml).

## How an icon is chosen

1. Files in `.github/workflows/` get the GitHub Actions icon.
2. Glance's own rules ([`IconRules.kt`](src/main/kotlin/io/github/shangotek/glanceicons/IconRules.kt)): README files, Angular suffixes and Java/Kotlin.
3. The catalogue's rules ([code](src/main/kotlin/io/github/shangotek/glanceicons/AtomCatalog.kt)): a case-insensitive match of the whole name (or of the path, when a rule mentions a folder), highest priority wins.
4. TypeScript files are checked for Angular decorators ([`AngularFiles.kt`](src/main/kotlin/io/github/shangotek/glanceicons/AngularFiles.kt)), which win over the catalogue's guesses from the name, and plain YAML files for Kubernetes `apiVersion` and `kind`.

## Build and run

You need JDK 25 (Gradle downloads everything else). The plugin is compiled to Java 21 bytecode, so it runs in every JetBrains IDE from 2025.1 on.

```bash
./gradlew runIde                                # start a sandbox IDE with the plugin installed
./gradlew test                                  # unit tests and headless IDE tests (IDEA 2026.2.3)
./gradlew test -PplatformVersion=2025.1.7.2     # the same tests in the oldest supported IDE
./gradlew buildPlugin                           # build/distributions/glance-icons-<version>.zip
./gradlew verifyPlugin                          # check compatibility against recommended IDE versions
./gradlew verifyPlugin -PverifyIde=/path/to/ide # check against one installed IDE
```

To install the zip in your own IDE, open **Settings → Plugins → ⚙ → Install Plugin from Disk…**.

## Updating the icons

- **Glance's own icons:** save a 16×16 SVG with its edges on whole pixels to `src/main/resources/icons/files/`, add an entry with the same name in upper case to `FileIcon` in [`GlanceIcons.kt`](src/main/kotlin/io/github/shangotek/glanceicons/GlanceIcons.kt), map names to it in `IconRules.kt`, and add a case to `IconRulesTest`. IntelliJ also picks up `<name>_dark.svg` for dark themes and `<name>@2x.svg` / `<name>@2x_dark.svg` for Retina screens. Draw a Retina file on a 32-unit `viewBox` but keep `width="16" height="16"`: IntelliJ takes the icon's size from the file it draws.
- **The catalogue:** unpack a newer version of the plugin jar it comes from (named in [`THIRD-PARTY-NOTICES.md`](src/main/resources/META-INF/THIRD-PARTY-NOTICES.md)) and run `python3 tools/import_catalog.py <unpacked jar>`. It copies the icons and rules and pixel-aligns the folder outlines.

Run `./gradlew test` afterwards. The tests fail if a rule names a missing icon, an icon isn't used by any rule, or an icon (or one of its variants) doesn't draw at 16 × 16.

To see every icon in a sandbox IDE, run `python3 tools/make_icon_gallery.py <folder>`. It creates one example file or folder per icon (with real Angular decorators and a Kubernetes manifest for Glance's own detection); open that folder with `./gradlew runIde --args=<folder>`.

## Publishing on the JetBrains Marketplace

The plugin is listed at [plugins.jetbrains.com/plugin/35402-glance-icons](https://plugins.jetbrains.com/plugin/35402-glance-icons). For a new release, raise `version` in `gradle.properties`, add a line to `<change-notes>` in `plugin.xml`, run `./gradlew buildPlugin`, and use **Upload Update** on the plugin page.

## Credits and license

The plugin code and Glance's own icons are released under the [MIT license](LICENSE), except for the artwork below, which keeps its own license.

- The icon catalogue and its rules are third-party artwork, also under the MIT license.
- The Angular logo in the `angular.json` and component icons comes from the [Angular press kit](https://angular.dev/press-kit) (CC BY 4.0), scaled down and redrawn on the pixel grid.
- The TypeScript icon is drawn after the [TypeScript logo](https://www.typescriptlang.org/branding/) from the TypeScript website (CC BY 4.0).
- The NgRx logo comes from [Simple Icons](https://simpleicons.org) (CC0).
- The Java icon is an original coffee cup, because the official Java logo isn't freely licensed.

Brand logos are trademarks of their owners and are used only to identify file types. Full license texts are in [`THIRD-PARTY-NOTICES.md`](src/main/resources/META-INF/THIRD-PARTY-NOTICES.md), which also ships inside the plugin.
