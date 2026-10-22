# Glance Icons

Colourful file and folder icons for IntelliJ IDEA, WebStorm and every other JetBrains IDE. They make the project tree easier to scan, and they understand Angular, up to Angular 22's `@Service()` and the 2025 style guide where `ng generate` no longer puts `.component` or `.service` in file names.

- 92 file icons and 25 folder icons, with dark-theme variants where a logo needs one
- **One icon per Angular type:** component, service, directive, pipe, guard, interceptor, routes, module, store and model each have their own drawing, found by file name *or* by the decorator inside the file
- Folders and files never share an icon: the `components` folder looks different from the components in it
- Official logos for languages, frameworks and tools, and config files that show the tool they configure
- Java and Kotlin test classes get a test badge, in the project tree too
- Each image format has its own icon: SVG, PNG, JPG, GIF, WebP and favicons
- Source roots, content roots and excluded folders keep the IDE's own icons, so you don't lose that information
- Free and open source (MIT)

Open [`docs/preview.html`](docs/preview.html) in a browser to see every icon in a light and a dark project tree.

## Angular

| Icon | Used for |
| --- | --- |
| <img src="src/main/resources/icons/files/component.svg" width="16"> | Components: `*.component.ts`, or any `.ts` file with `@Component(…)` |
| <img src="src/main/resources/icons/files/service.svg" width="16"> | Services: `*.service.ts`, `@Service()` (Angular 22) or `@Injectable(…)` |
| <img src="src/main/resources/icons/files/directive.svg" width="16"> | Directives: `*.directive.ts` or `@Directive(…)` |
| <img src="src/main/resources/icons/files/pipe.svg" width="16"> | Pipes: `*.pipe.ts`, `*-pipe.ts` or `@Pipe(…)` |
| <img src="src/main/resources/icons/files/guard.svg" width="16"> | Guards: `*.guard.ts`, `*-guard.ts`, `CanActivateFn` and friends, or `implements CanActivate` |
| <img src="src/main/resources/icons/files/interceptor.svg" width="16"> | Interceptors: `*.interceptor.ts`, `*-interceptor.ts`, `HttpInterceptorFn` or `implements HttpInterceptor` |
| <img src="src/main/resources/icons/files/routes.svg" width="16"> | Routes and resolvers: `*.routes.ts`, `app.routes.server.ts`, `*-routing-module.ts`, `*-resolver.ts`, `ResolveFn` |
| <img src="src/main/resources/icons/files/module.svg" width="16"> | Modules: `*.module.ts`, `*-module.ts` or `@NgModule(…)` |
| <img src="src/main/resources/icons/files/store.svg" width="16"> | State (NgRx logo): `*.store.ts`, NgRx files, `signalStore(…)`, `createReducer(…)` |
| <img src="src/main/resources/icons/files/model.svg" width="16"> | Models and data: `*.model.ts`, `*.interface.ts`, `*.types.ts`, `*.enum.ts`, `*.dto.ts`, `*.data.ts` |
| <img src="src/main/resources/icons/files/test.svg" width="16"> | Specs: `*.spec.ts`, `*.test.ts`, `*.cy.ts` and the JS/TSX equivalents |
| <img src="src/main/resources/icons/files/storybook.svg" width="16"> | Stories: `*.stories.ts` |
| <img src="src/main/resources/icons/files/env.svg" width="16"> | Environments: `environment.ts`, `environment.*.ts`, `.env*` |
| <img src="src/main/resources/icons/files/config.svg" width="16"> | App config: `app.config.ts`, `app.config.server.ts` |
| <img src="src/main/resources/icons/files/angular.svg" width="16"> | `angular.json` |

Since Angular 20, `ng generate` names components, directives and services without a type suffix (`user-card.ts`), and joins the other suffixes with a hyphen (`auth-guard.ts`). File names alone can't tell those apart, so the plugin reads the decorator. The result is cached per file and refreshed when you save.

## Languages and frameworks

| Icon | Used for |
| --- | --- |
| <img src="src/main/resources/icons/files/typescript.svg" width="16"> | `.ts`, `.mts`, `.cts` |
| <img src="src/main/resources/icons/files/javascript.svg" width="16"> | `.js`, `.mjs`, `.cjs` |
| <img src="src/main/resources/icons/files/react.svg" width="16"> | `.tsx`, `.jsx` |
| <img src="src/main/resources/icons/files/nextjs.svg" width="16"> | `next.config.*`, `next-env.d.ts` |
| <img src="src/main/resources/icons/files/vue.svg" width="16"> | `.vue` |
| <img src="src/main/resources/icons/files/svelte.svg" width="16"> | `.svelte` |
| <img src="src/main/resources/icons/files/html.svg" width="16"> | `.html`, `.htm` |
| <img src="src/main/resources/icons/files/css.svg" width="16"> | `.css`, `.less` |
| <img src="src/main/resources/icons/files/scss.svg" width="16"> | `.scss`, `.sass` |
| <img src="src/main/resources/icons/files/json.svg" width="16"> | `.json`, `.jsonc`, `.json5` |
| <img src="src/main/resources/icons/files/yaml.svg" width="16"> | `.yml`, `.yaml` |
| <img src="src/main/resources/icons/files/markdown.svg" width="16"> | `.md`, `.mdx` |
| <img src="src/main/resources/icons/files/graphql.svg" width="16"> | `.graphql`, `.gql` |
| <img src="src/main/resources/icons/files/python.svg" width="16"> | `.py`, `requirements.txt`, `pyproject.toml`, `Pipfile` |
| <img src="src/main/resources/icons/files/jupyter.svg" width="16"> | `.ipynb` |
| <img src="src/main/resources/icons/files/java.svg" width="16"> | `.java`, `.jar`, `.class` |
| <img src="src/main/resources/icons/files/java_test.svg" width="16"> | `*Test.java`, `*Tests.java`, `*IT.java` |
| <img src="src/main/resources/icons/files/kotlin.svg" width="16"> | `.kt`, `.kts` (Gradle scripts keep their Gradle icon) |
| <img src="src/main/resources/icons/files/kotlin_test.svg" width="16"> | `*Test.kt`, `*Tests.kt`, `*IT.kt` |
| <img src="src/main/resources/icons/files/rust.svg" width="16"> | `.rs`, `Cargo.toml` |
| <img src="src/main/resources/icons/files/go.svg" width="16"> | `.go` |
| <img src="src/main/resources/icons/files/go_mod.svg" width="16"> | `go.mod`, `go.sum`, `go.work` |
| <img src="src/main/resources/icons/files/swift.svg" width="16"> | `.swift` |
| <img src="src/main/resources/icons/files/dart.svg" width="16"> | `.dart` |
| <img src="src/main/resources/icons/files/php.svg" width="16"> | `.php` |
| <img src="src/main/resources/icons/files/ruby.svg" width="16"> | `.rb`, `Gemfile`, `Rakefile` |
| <img src="src/main/resources/icons/files/c.svg" width="16"> | `.c`, `.h` |
| <img src="src/main/resources/icons/files/cpp.svg" width="16"> | `.cpp`, `.cc`, `.hpp` |
| <img src="src/main/resources/icons/files/dotnet.svg" width="16"> | `.cs`, `.fs`, `.csproj`, `.sln` |
| <img src="src/main/resources/icons/files/lua.svg" width="16"> | `.lua` |
| <img src="src/main/resources/icons/files/shell.svg" width="16"> | `.sh`, `.bash`, `.zsh`, `.fish` |

Java and Kotlin files that hold a single class show up in the project tree as that class. The plugin puts the language icon on those class nodes too, which replaces IntelliJ's class, interface and enum markers there.

## Tools and config files

| Icon | Used for |
| --- | --- |
| <img src="src/main/resources/icons/files/tsconfig.svg" width="16"> | `tsconfig*.json` |
| <img src="src/main/resources/icons/files/eslint.svg" width="16"> | `eslint.config.*`, `.eslintrc*` |
| <img src="src/main/resources/icons/files/prettier.svg" width="16"> | `.prettierrc*`, `prettier.config.*` |
| <img src="src/main/resources/icons/files/vite.svg" width="16"> | `vite.config.*` |
| <img src="src/main/resources/icons/files/vitest.svg" width="16"> | `vitest.config.*` |
| <img src="src/main/resources/icons/files/jest.svg" width="16"> | `jest.config.*` |
| <img src="src/main/resources/icons/files/cypress.svg" width="16"> | `cypress.config.*` |
| <img src="src/main/resources/icons/files/tailwind.svg" width="16"> | `tailwind.config.*` |
| <img src="src/main/resources/icons/files/webpack.svg" width="16"> | `webpack.*` |
| <img src="src/main/resources/icons/files/babel.svg" width="16"> | `.babelrc`, `babel.config.*` |
| <img src="src/main/resources/icons/files/storybook.svg" width="16"> | `*.stories.*` |
| <img src="src/main/resources/icons/files/nx.svg" width="16"> | `nx.json`, `project.json` |
| <img src="src/main/resources/icons/files/nestjs.svg" width="16"> | `nest-cli.json` |
| <img src="src/main/resources/icons/files/prisma.svg" width="16"> | `.prisma` |
| <img src="src/main/resources/icons/files/npm.svg" width="16"> | `package-lock.json`, `.npmrc` |
| <img src="src/main/resources/icons/files/yarn.svg" width="16"> | `yarn.lock`, `.yarnrc.yml` |
| <img src="src/main/resources/icons/files/pnpm.svg" width="16"> | `pnpm-lock.yaml`, `pnpm-workspace.yaml` |
| <img src="src/main/resources/icons/files/bun.svg" width="16"> | `bun.lock`, `bunfig.toml` |
| <img src="src/main/resources/icons/files/node.svg" width="16"> | `.nvmrc`, `.node-version` |
| <img src="src/main/resources/icons/files/editorconfig.svg" width="16"> | `.editorconfig` |
| <img src="src/main/resources/icons/files/config.svg" width="16"> | everything else: `config.json`, `*.config.ts`, `proxy.conf.json`, `karma.conf.js` |

## Project files and hosting

| Icon | Used for |
| --- | --- |
| <img src="src/main/resources/icons/files/package.svg" width="16"> | `package.json` |
| <img src="src/main/resources/icons/files/lock.svg" width="16"> | other lock files: `*.lock`, `gradle.lockfile` |
| <img src="src/main/resources/icons/files/git.svg" width="16"> | `.gitignore`, `.gitattributes` |
| <img src="src/main/resources/icons/files/github_actions.svg" width="16"> | workflows in `.github/workflows/` |
| <img src="src/main/resources/icons/files/gitlab.svg" width="16"> | `.gitlab-ci.yml` |
| <img src="src/main/resources/icons/files/docker.svg" width="16"> | `Dockerfile`, `docker-compose.yml`, `compose.yaml` |
| <img src="src/main/resources/icons/files/firebase.svg" width="16"> | `firebase.json`, `.firebaserc`, `firestore.rules` |
| <img src="src/main/resources/icons/files/vercel.svg" width="16"> | `vercel.json` |
| <img src="src/main/resources/icons/files/netlify.svg" width="16"> | `netlify.toml` |
| <img src="src/main/resources/icons/files/readme.svg" width="16"> | `README*` |
| <img src="src/main/resources/icons/files/license.svg" width="16"> | `LICENSE*` |
| <img src="src/main/resources/icons/files/changelog.svg" width="16"> | `CHANGELOG*` |

## Media and documents

| Icon | Used for |
| --- | --- |
| <img src="src/main/resources/icons/files/svg.svg" width="16"> | `.svg` |
| <img src="src/main/resources/icons/files/png.svg" width="16"> | `.png` |
| <img src="src/main/resources/icons/files/jpg.svg" width="16"> | `.jpg`, `.jpeg` |
| <img src="src/main/resources/icons/files/gif.svg" width="16"> | `.gif` |
| <img src="src/main/resources/icons/files/webp.svg" width="16"> | `.webp` |
| <img src="src/main/resources/icons/files/favicon.svg" width="16"> | `.ico` |
| <img src="src/main/resources/icons/files/image.svg" width="16"> | `.avif`, `.bmp`, `.tiff`, `.heic` |
| <img src="src/main/resources/icons/files/video.svg" width="16"> | `.mp4`, `.mov`, `.webm` |
| <img src="src/main/resources/icons/files/audio.svg" width="16"> | `.mp3`, `.wav`, `.ogg` |
| <img src="src/main/resources/icons/files/font.svg" width="16"> | `.woff`, `.woff2`, `.ttf`, `.otf` |
| <img src="src/main/resources/icons/files/archive.svg" width="16"> | `.zip`, `.tar.gz`, `.7z`, `.rar` |
| <img src="src/main/resources/icons/files/pdf.svg" width="16"> | `.pdf` |
| <img src="src/main/resources/icons/files/document.svg" width="16"> | `.docx`, `.odt`, `.rtf` |
| <img src="src/main/resources/icons/files/presentation.svg" width="16"> | `.pptx`, `.key`, `.odp` |
| <img src="src/main/resources/icons/files/spreadsheet.svg" width="16"> | `.csv`, `.xlsx`, `.ods` |

## Folders

| Icon | Folder names |
| --- | --- |
| <img src="src/main/resources/icons/folders/app.svg" width="16"> | `app` |
| <img src="src/main/resources/icons/folders/source.svg" width="16"> | `src`, `source` |
| <img src="src/main/resources/icons/folders/core.svg" width="16"> | `core` |
| <img src="src/main/resources/icons/folders/frontend.svg" width="16"> | `frontend`, `client`, `web` |
| <img src="src/main/resources/icons/folders/backend.svg" width="16"> | `backend`, `server`, `srv` |
| <img src="src/main/resources/icons/folders/components.svg" width="16"> | `components`, `ui`, `widgets` |
| <img src="src/main/resources/icons/folders/services.svg" width="16"> | `services`, `api` |
| <img src="src/main/resources/icons/folders/directives.svg" width="16"> | `directives` (Angular logo) |
| <img src="src/main/resources/icons/folders/pipes.svg" width="16"> | `pipes` (Angular logo) |
| <img src="src/main/resources/icons/folders/guards.svg" width="16"> | `guards` (Angular logo) |
| <img src="src/main/resources/icons/folders/interceptors.svg" width="16"> | `interceptors` (Angular logo) |
| <img src="src/main/resources/icons/folders/store.svg" width="16"> | `store`, `state`, `ngrx` |
| <img src="src/main/resources/icons/folders/models.svg" width="16"> | `models`, `types`, `interfaces`, `entities` |
| <img src="src/main/resources/icons/folders/shared.svg" width="16"> | `shared`, `common` |
| <img src="src/main/resources/icons/folders/utils.svg" width="16"> | `utils`, `helpers`, `lib` |
| <img src="src/main/resources/icons/folders/pages.svg" width="16"> | `pages`, `views`, `features`, `layout` |
| <img src="src/main/resources/icons/folders/assets.svg" width="16"> | `assets`, `public`, `static`, `images`, `fonts`, `svgs` |
| <img src="src/main/resources/icons/folders/styles.svg" width="16"> | `styles`, `scss`, `theme` |
| <img src="src/main/resources/icons/folders/tests.svg" width="16"> | `test`, `tests`, `e2e`, `cypress` |
| <img src="src/main/resources/icons/folders/docs.svg" width="16"> | `docs` |
| <img src="src/main/resources/icons/folders/scripts.svg" width="16"> | `scripts`, `bin`, `tools` |
| <img src="src/main/resources/icons/folders/config.svg" width="16"> | `config`, `environments`, `settings`, `.vscode` |
| <img src="src/main/resources/icons/folders/git.svg" width="16"> | `.git`, `.github`, `.gitlab`, `.husky` |
| <img src="src/main/resources/icons/folders/packages.svg" width="16"> | `node_modules`, `packages`, `libs`, `vendor` |
| <img src="src/main/resources/icons/folders/i18n.svg" width="16"> | `i18n`, `locale`, `translations` |

The full matching rules live in [`IconRules.kt`](src/main/kotlin/io/github/shangotek/glanceicons/IconRules.kt) and [`AngularFiles.kt`](src/main/kotlin/io/github/shangotek/glanceicons/AngularFiles.kt) and [`FileIcons.kt`](src/main/kotlin/io/github/shangotek/glanceicons/FileIcons.kt).

## Build and run

You need JDK 25 (Gradle downloads everything else). The plugin is compiled to Java 21 bytecode, so it runs in every JetBrains IDE from 2025.1 on.

```bash
./gradlew runIde                                # start a sandbox IDE with the plugin installed
./gradlew test                                  # unit tests and headless IDE tests (IDEA 2026.2.3)
./gradlew test -PplatformVersion=2025.1.7.2     # the same tests in the oldest supported IDE
./gradlew buildPlugin                           # build/distributions/glance-icons-<version>.zip
./gradlew verifyPlugin                          # check compatibility against recommended IDE versions
```

To install the zip in your own IDE, open **Settings → Plugins → ⚙ → Install Plugin from Disk…**.

## Publishing on the JetBrains Marketplace

The Marketplace hosts free plugins at no cost.

1. Sign in at [plugins.jetbrains.com](https://plugins.jetbrains.com) with a JetBrains account.
2. Choose **Upload plugin**, select the zip from `build/distributions`, pick a license (MIT) and the *Free* option.
3. JetBrains reviews the first upload, which usually takes a few working days. After that, people find it under **Settings → Plugins → Marketplace**.

Pick the plugin ID in `plugin.xml` before the first upload: it can't change afterwards. For later releases you can create a Marketplace token and publish with `./gradlew publishPlugin`.

## Adding an icon

1. Save a 16×16 SVG to `src/main/resources/icons/files/` (or `folders/`). Keep the colours mid-tone so the icon works in both themes, or add a `<name>_dark.svg` that IntelliJ picks up in dark themes.
2. Add an entry with the same name, in upper case, to `FileIcon` or `FolderIcon` in [`GlanceIcons.kt`](src/main/kotlin/io/github/shangotek/glanceicons/GlanceIcons.kt).
3. Map file names to it in [`IconRules.kt`](src/main/kotlin/io/github/shangotek/glanceicons/IconRules.kt) and add a case to `IconRulesTest`.
4. Run `./gradlew test`. The tests fail if an entry has no SVG.

## Credits and license

The plugin code and the original icons are released under the [MIT license](LICENSE).

- Brand logos come from [Simple Icons](https://simpleicons.org), released under CC0.
- The Go gopher and EditorConfig icons come from [Material Icon Theme](https://github.com/material-extensions/vscode-material-icon-theme) (MIT). The Go gopher was designed by Renée French (CC BY 4.0).
- The Java icon is an original coffee cup with flame-shaped steam, because the official Java logo isn't freely licensed.

Brand logos are trademarks of their owners and are used only to identify file types. Full license texts are in [THIRD-PARTY-NOTICES.md](THIRD-PARTY-NOTICES.md), which also ships inside the plugin.
