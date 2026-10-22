package io.github.shangotek.glanceicons

/**
 * Picks an icon from a file or folder name alone, so lookups stay cheap enough for the project tree.
 *
 * Files are matched case-insensitively, first rule wins:
 * 1. names the IDE draws better itself (`build.gradle.kts`) get no icon
 * 2. exact name (`package.json`, `Dockerfile`)
 * 3. test class suffix, case-sensitive (`UserTest.java`, but not `Contest.java`)
 * 4. name prefix (`README.md`, `.env.local`, `eslint.config.js`)
 * 5. compound suffix, longest first (`user.component.ts`, `auth-guard.ts`)
 * 6. extension (`main.ts`, `logo.png`)
 *
 * Angular files named after the 2025 style guide (`user-card.ts`) look like plain TypeScript here;
 * [FileIcons] refines those by reading the decorator, and adds rules that need the file's location.
 */
object IconRules {

    fun fileIcon(fileName: String): FileIcon? {
        val name = fileName.lowercase()
        if (IDE_OWNED_SUFFIXES.any { name.endsWith(it) }) return null
        FILE_NAMES[name]?.let { return it }
        TEST_CLASS_SUFFIXES.firstOrNull { fileName.endsWith(it.first) }?.let { return it.second }
        FILE_PREFIXES.firstOrNull { name.startsWith(it.first) }?.let { return it.second }
        FILE_SUFFIXES.firstOrNull { name.endsWith(it.first) }?.let { return it.second }
        return EXTENSIONS[name.substringAfterLast('.', "")]
    }

    fun folderIcon(folderName: String): FolderIcon? = FOLDER_NAMES[folderName.lowercase()]

    /** Every icon a name rule can produce, so tests can prove that no icon is unreachable. */
    internal val mappedFileIcons: Set<FileIcon>
        get() = (FILE_NAMES.values + TEST_CLASS_SUFFIXES.map { it.second } + FILE_PREFIXES.map { it.second } +
            FILE_SUFFIXES.map { it.second } + EXTENSIONS.values).toSet()

    internal val mappedFolderIcons: Set<FolderIcon>
        get() = FOLDER_NAMES.values.toSet()

    // Gradle's own icon says more about build scripts than the Kotlin logo would.
    private val IDE_OWNED_SUFFIXES = listOf(".gradle.kts")

    private val FILE_NAMES = rules(
        FileIcon.ANGULAR to listOf("angular.json"),
        FileIcon.NEXTJS to listOf("next-env.d.ts"),
        FileIcon.PYTHON to listOf("requirements.txt", "requirements-dev.txt", "pyproject.toml", "pipfile", ".python-version"),
        FileIcon.RUST to listOf("cargo.toml", "rust-toolchain", "rust-toolchain.toml"),
        FileIcon.GO_MOD to listOf("go.mod", "go.sum", "go.work"),
        FileIcon.RUBY to listOf("gemfile", "rakefile"),
        FileIcon.PACKAGE to listOf("package.json"),
        FileIcon.NPM to listOf("package-lock.json"),
        FileIcon.YARN to listOf("yarn.lock", ".yarnrc", ".yarnrc.yml"),
        FileIcon.PNPM to listOf("pnpm-lock.yaml", "pnpm-workspace.yaml", ".pnpmfile.cjs"),
        FileIcon.BUN to listOf("bun.lock", "bun.lockb", "bunfig.toml"),
        FileIcon.LOCK to listOf("gradle.lockfile"),
        FileIcon.NX to listOf("nx.json", "project.json"),
        FileIcon.NESTJS to listOf("nest-cli.json"),
        FileIcon.FIREBASE to listOf("firebase.json", ".firebaserc", "firestore.rules", "firestore.indexes.json", "storage.rules"),
        FileIcon.VERCEL to listOf("vercel.json", ".vercelignore"),
        FileIcon.NETLIFY to listOf("netlify.toml"),
        FileIcon.GITLAB to listOf(".gitlab-ci.yml"),
        FileIcon.GIT to listOf(".gitignore", ".gitattributes", ".gitmodules", ".gitkeep", ".mailmap"),
        FileIcon.DOCKER to listOf("dockerfile", ".dockerignore", "docker-compose.yml", "docker-compose.yaml", "compose.yml", "compose.yaml"),
        FileIcon.ESLINT to listOf(".eslintignore"),
        FileIcon.PRETTIER to listOf(".prettierignore"),
        FileIcon.NPM to listOf(".npmrc", ".npmignore"),
        FileIcon.NODE to listOf(".nvmrc", ".node-version"),
        FileIcon.EDITORCONFIG to listOf(".editorconfig"),
        FileIcon.CONFIG to listOf(
            "config.json", "config.yaml", "config.yml", "config.toml", "settings.json", ".browserslistrc",
        ),
    ).toMap()

    private val TEST_CLASS_SUFFIXES = rules(
        FileIcon.JAVA_TEST to listOf("Test.java", "Tests.java", "IT.java", "TestCase.java"),
        FileIcon.KOTLIN_TEST to listOf("Test.kt", "Tests.kt", "IT.kt"),
    )

    private val FILE_PREFIXES = rules(
        FileIcon.README to listOf("readme"),
        FileIcon.LICENSE to listOf("license", "licence", "copying"),
        FileIcon.CHANGELOG to listOf("changelog"),
        FileIcon.ENV to listOf(".env", "environment."),
        FileIcon.NEXTJS to listOf("next.config."),
        FileIcon.DOCKER to listOf("dockerfile.", "docker-compose."),
        FileIcon.TSCONFIG to listOf("tsconfig."),
        FileIcon.ESLINT to listOf(".eslintrc", "eslint.config."),
        FileIcon.PRETTIER to listOf(".prettierrc", "prettier.config."),
        FileIcon.VITE to listOf("vite.config."),
        FileIcon.VITEST to listOf("vitest.config.", "vitest.workspace."),
        FileIcon.JEST to listOf("jest.config.", "jest.setup."),
        FileIcon.TAILWIND to listOf("tailwind.config."),
        FileIcon.WEBPACK to listOf("webpack."),
        FileIcon.BABEL to listOf(".babelrc", "babel.config."),
        FileIcon.CYPRESS to listOf("cypress.config."),
        FileIcon.CONFIG to listOf("jsconfig.", ".stylelintrc", "karma.conf."),
    )

    // Angular's 2025 style guide joins pipe, guard, interceptor, resolver and module suffixes with "-" instead of ".".
    private val FILE_SUFFIXES = rules(
        FileIcon.COMPONENT to listOf(".component.ts"),
        FileIcon.SERVICE to listOf(".service.ts"),
        FileIcon.PIPE to listOf(".pipe.ts", "-pipe.ts"),
        FileIcon.DIRECTIVE to listOf(".directive.ts"),
        FileIcon.GUARD to listOf(".guard.ts", "-guard.ts"),
        FileIcon.INTERCEPTOR to listOf(".interceptor.ts", "-interceptor.ts"),
        FileIcon.MODULE to listOf(".module.ts", "-module.ts"),
        FileIcon.STORE to listOf(".store.ts", ".state.ts", ".reducer.ts", ".actions.ts", ".effects.ts", ".selectors.ts"),
        FileIcon.MODEL to listOf(".model.ts", ".interface.ts", ".types.ts", ".type.ts", ".enum.ts", ".dto.ts", ".entity.ts", ".data.ts"),
        FileIcon.STORYBOOK to listOf(".stories.ts", ".stories.tsx", ".stories.js", ".stories.jsx", ".stories.mdx"),
        FileIcon.ROUTES to listOf(
            ".routes.ts", ".routes.server.ts", ".routing.ts", "-routing.module.ts", ".routing.module.ts", "-routing-module.ts",
            ".resolver.ts", "-resolver.ts",
        ),
        FileIcon.TEST to listOf(
            ".spec.ts", ".test.ts", ".spec.tsx", ".test.tsx",
            ".spec.js", ".test.js", ".spec.jsx", ".test.jsx",
            ".cy.ts", ".cy.js", ".e2e-spec.ts",
        ),
        FileIcon.CONFIG to listOf(
            ".config.ts", ".config.server.ts", ".config.mts", ".config.js", ".config.mjs", ".config.cjs", ".config.json", ".conf.json",
        ),
    ).sortedByDescending { it.first.length }

    private val EXTENSIONS = rules(
        FileIcon.TYPESCRIPT to listOf("ts", "mts", "cts"),
        FileIcon.JAVASCRIPT to listOf("js", "mjs", "cjs"),
        FileIcon.REACT to listOf("tsx", "jsx"),
        FileIcon.VUE to listOf("vue"),
        FileIcon.SVELTE to listOf("svelte"),
        FileIcon.GRAPHQL to listOf("graphql", "gql"),
        FileIcon.PRISMA to listOf("prisma"),
        FileIcon.PYTHON to listOf("py", "pyi", "pyw"),
        FileIcon.JUPYTER to listOf("ipynb"),
        FileIcon.JAVA to listOf("java", "jar", "class"),
        FileIcon.KOTLIN to listOf("kt", "kts"),
        FileIcon.RUST to listOf("rs"),
        FileIcon.GO to listOf("go"),
        FileIcon.SWIFT to listOf("swift"),
        FileIcon.DART to listOf("dart"),
        FileIcon.PHP to listOf("php"),
        FileIcon.RUBY to listOf("rb", "rake", "gemspec"),
        FileIcon.C to listOf("c", "h"),
        FileIcon.CPP to listOf("cpp", "cc", "cxx", "hpp", "hh"),
        FileIcon.DOTNET to listOf("cs", "csx", "csproj", "sln", "fs", "fsproj", "vb"),
        FileIcon.LUA to listOf("lua"),
        FileIcon.HTML to listOf("html", "htm"),
        FileIcon.CSS to listOf("css", "less"),
        FileIcon.SCSS to listOf("scss", "sass"),
        FileIcon.JSON to listOf("json", "jsonc", "json5"),
        FileIcon.YAML to listOf("yml", "yaml"),
        FileIcon.MARKDOWN to listOf("md", "markdown", "mdx"),
        FileIcon.SHELL to listOf("sh", "bash", "zsh", "fish", "command"),
        FileIcon.LOCK to listOf("lock"),
        FileIcon.ENV to listOf("env"),
        FileIcon.DOCKER to listOf("dockerfile"),
        FileIcon.SVG to listOf("svg"),
        FileIcon.PNG to listOf("png"),
        FileIcon.JPG to listOf("jpg", "jpeg"),
        FileIcon.GIF to listOf("gif"),
        FileIcon.WEBP to listOf("webp"),
        FileIcon.FAVICON to listOf("ico"),
        FileIcon.IMAGE to listOf("avif", "bmp", "tif", "tiff", "heic"),
        FileIcon.VIDEO to listOf("mp4", "mov", "webm", "mkv", "avi"),
        FileIcon.AUDIO to listOf("mp3", "wav", "ogg", "flac", "m4a", "aac"),
        FileIcon.FONT to listOf("woff", "woff2", "ttf", "otf", "eot"),
        FileIcon.ARCHIVE to listOf("zip", "tar", "gz", "tgz", "7z", "rar", "bz2", "xz"),
        FileIcon.PDF to listOf("pdf"),
        FileIcon.DOCUMENT to listOf("doc", "docx", "odt", "rtf"),
        FileIcon.PRESENTATION to listOf("ppt", "pptx", "key", "odp"),
        FileIcon.SPREADSHEET to listOf("csv", "tsv", "xls", "xlsx", "ods"),
    ).toMap()

    private val FOLDER_NAMES = rules(
        FolderIcon.APP to listOf("app"),
        FolderIcon.SOURCE to listOf("src", "source", "sources"),
        FolderIcon.CORE to listOf("core"),
        FolderIcon.FRONTEND to listOf("frontend", "client", "web", "webapp"),
        FolderIcon.BACKEND to listOf("backend", "server", "srv"),
        FolderIcon.COMPONENTS to listOf("components", "component", "ui", "widgets"),
        FolderIcon.SERVICES to listOf("services", "service", "api"),
        FolderIcon.DIRECTIVES to listOf("directives", "directive"),
        FolderIcon.PIPES to listOf("pipes"),
        FolderIcon.GUARDS to listOf("guards", "guard"),
        FolderIcon.INTERCEPTORS to listOf("interceptors", "interceptor"),
        FolderIcon.STORE to listOf("store", "stores", "state", "ngrx"),
        FolderIcon.MODELS to listOf("models", "model", "types", "interfaces", "entities", "dto"),
        FolderIcon.SHARED to listOf("shared", "common"),
        FolderIcon.UTILS to listOf("utils", "util", "helpers", "lib"),
        FolderIcon.PAGES to listOf("pages", "views", "screens", "features", "layout", "layouts"),
        FolderIcon.ASSETS to listOf("assets", "public", "static", "images", "img", "media", "icons", "svgs", "fonts", "videos"),
        FolderIcon.STYLES to listOf("styles", "css", "scss", "sass", "theme", "themes"),
        FolderIcon.TESTS to listOf("test", "tests", "__tests__", "spec", "specs", "e2e", "cypress", "playwright"),
        FolderIcon.DOCS to listOf("docs", "doc", "documentation"),
        FolderIcon.SCRIPTS to listOf("scripts", "bin", "tools"),
        FolderIcon.CONFIG to listOf("config", "configs", "conf", ".config", "environments", "settings", ".vscode", ".claude"),
        FolderIcon.GIT to listOf(".git", ".github", ".gitlab", ".husky"),
        FolderIcon.PACKAGES to listOf("node_modules", "packages", "libs", "vendor"),
        FolderIcon.I18N to listOf("i18n", "l10n", "locale", "locales", "translations", "lang"),
    ).toMap()

    private fun <T> rules(vararg groups: Pair<T, List<String>>): List<Pair<String, T>> =
        groups.flatMap { (icon, keys) -> keys.map { it to icon } }
}
