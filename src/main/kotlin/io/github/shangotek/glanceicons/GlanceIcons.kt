package io.github.shangotek.glanceicons

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

/**
 * File icons, loaded from `/icons/files/<name>.svg` where `<name>` is the lowercase entry name.
 * Dark themes pick up `<name>_dark.svg` automatically when it exists.
 */
enum class FileIcon {
    // Angular: the Angular logo with a letter badge, plus stores, models and specs
    COMPONENT, SERVICE, DIRECTIVE, PIPE, GUARD, INTERCEPTOR, ROUTES, MODULE, STORE, MODEL, TEST, ANGULAR,

    // Languages and frameworks
    TYPESCRIPT, JAVASCRIPT, REACT, NEXTJS, VUE, SVELTE, HTML, CSS, SCSS, JSON, YAML, MARKDOWN, GRAPHQL,
    PYTHON, JUPYTER, JAVA, JAVA_TEST, KOTLIN, KOTLIN_TEST, RUST, GO, GO_MOD, SWIFT, DART, PHP, RUBY, C, CPP, DOTNET, LUA, SHELL,

    // Config files, each in the style of the tool it configures
    CONFIG, TSCONFIG, ESLINT, PRETTIER, VITE, VITEST, JEST, TAILWIND, WEBPACK, BABEL, STORYBOOK, NX,
    NPM, YARN, PNPM, BUN, NODE, EDITORCONFIG, CYPRESS, NESTJS, PRISMA,

    // Project files and hosting
    PACKAGE, LOCK, ENV, GIT, GITHUB_ACTIONS, GITLAB, DOCKER, FIREBASE, VERCEL, NETLIFY, README, LICENSE, CHANGELOG,

    // Media and documents
    SVG, PNG, JPG, GIF, WEBP, FAVICON, IMAGE, VIDEO, AUDIO, FONT, ARCHIVE, PDF, DOCUMENT, PRESENTATION, SPREADSHEET;

    val path: String = "/icons/files/${name.lowercase()}.svg"
    val icon: Icon by lazy { IconLoader.getIcon(path, FileIcon::class.java) }
}

/** Folder icons, loaded from `/icons/folders/<name>.svg` where `<name>` is the lowercase entry name. */
enum class FolderIcon {
    APP, SOURCE, CORE, FRONTEND, BACKEND,
    COMPONENTS, SERVICES, DIRECTIVES, PIPES, GUARDS, INTERCEPTORS, STORE, MODELS, SHARED, UTILS, PAGES,
    ASSETS, STYLES, TESTS, DOCS, SCRIPTS, CONFIG, GIT, PACKAGES, I18N;

    val path: String = "/icons/folders/${name.lowercase()}.svg"
    val icon: Icon by lazy { IconLoader.getIcon(path, FolderIcon::class.java) }
}
