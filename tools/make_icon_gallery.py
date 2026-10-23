#!/usr/bin/env python3
"""Creates a folder tree that shows every icon Glance Icons can draw, for checking them in a sandbox IDE.

Usage: python3 tools/make_icon_gallery.py <target folder>

  <target>/glance/    one file per Glance icon (Angular files with real decorators, a Kubernetes manifest, ...)
  <target>/files/     one example file per catalogue file icon
  <target>/folders/   one example folder per catalogue folder icon

Example names come from the catalogue's own sample names (fileNames / folderNames) and are only used when the
plugin's rules really pick that icon for them: all rules on, whole-name match ignoring case, highest priority wins.
"""
import re
import sys
from pathlib import Path

ATOM = Path(__file__).resolve().parent.parent / "src/main/resources/atom"
# Names the IDE hides by default, and folders that would make it look for version control or projects.
SKIP = {".git", ".hg", ".svn", "cvs", "_svn", "__pycache__", ".ds_store", "vssver.scc", "vssver2.scc", ".idea"}


def load(xml_name, names_attr):
    rules = []
    for index, raw in enumerate(re.findall(r"<regex\b([^>]*?)/?>", (ATOM / xml_name).read_text())):
        attrs = dict(re.findall(r'(\w+)="([^"]*)"', raw))
        if not attrs.get("pattern") or not attrs.get("icon"):
            continue
        try:
            regex = re.compile(attrs["pattern"], re.I)
        except re.error:
            continue
        examples = [n.strip() for n in attrs.get(names_attr, "").split(",") if n.strip()]
        rules.append({"priority": int(attrs.get("priority", 100)), "order": index, "regex": regex,
                      "path": "/" in attrs["pattern"], "icon": Path(attrs["icon"]).name, "examples": examples})
    rules.sort(key=lambda r: (-r["priority"], r["order"]))
    return rules


def resolve(rules, name, path):
    for rule in rules:
        if rule["regex"].fullmatch(path if rule["path"] else name):
            return rule["icon"]
    return None


README_FORMATS = ("md", "markdown", "mdx", "txt", "text", "rst", "adoc", "asciidoc", "org", "html")


def is_readme(lower):
    """The same test as IconRules.isReadme."""
    if not lower.startswith("readme"):
        return False
    rest = lower[len("readme"):]
    return not rest or rest[0] in "._-" and ("." not in rest or lower.rsplit(".", 1)[1] in README_FORMATS)


def taken_by_glance(name):
    """Names whose icon comes from Glance's own rules, so they can't show a catalogue icon."""
    lower = name.lower()
    if lower in ("angular.json", "angular-cli.json", ".angular-cli.json") or is_readme(lower):
        return True
    if re.search(r"(Tests?|IT|TestCase)\.(java|kt)$", name) or lower.endswith((".java", ".kt")):
        return True
    if lower.endswith(".kts") and not lower.endswith(".gradle.kts"):
        return True
    angular = (".component.ts", ".service.ts", ".pipe.ts", "-pipe.ts", ".directive.ts", ".guard.ts", "-guard.ts",
               ".interceptor.ts", "-interceptor.ts", ".module.ts", "-module.ts", ".store.ts", ".state.ts",
               ".reducer.ts", ".actions.ts", ".effects.ts", ".selectors.ts", ".model.ts", ".interface.ts",
               ".types.ts", ".type.ts", ".enum.ts", ".dto.ts", ".entity.ts", ".data.ts", ".routes.ts",
               ".routing.ts", ".resolver.ts", "-resolver.ts")
    return lower.endswith(angular)


def candidates(rule):
    """Concrete names for a rule: its sample names with '*' filled in (they may include folders, like
    'docker/*.yml'), a name spelled out by a plain pattern such as '^\\.redwood\\.toml$', and extension lists."""
    for example in rule["examples"]:
        yield example.replace("*", "example")
    literal = re.fullmatch(r"\^?((?:[\w-]|\\\.)+)\$?", rule["regex"].pattern)
    if literal:
        yield literal.group(1).replace("\\.", ".")
    match = re.fullmatch(r"\.\*\\\.\(([\w|]+)\)\$?", rule["regex"].pattern) or re.fullmatch(r"\.\*\\\.(\w+)\$?", rule["regex"].pattern)
    if match:
        for ext in match.group(1).split("|"):
            yield f"example.{ext}"


def folders_in(pattern):
    """Literal folder names a path rule asks for, e.g. 'i18n' in '.*/i18n/.*'."""
    return [seg.replace("\\.", ".") for seg in re.findall(r"/((?:[\w-]|\\\.)+)/", pattern)] or [""]


def valid(relative):
    parts = relative.split("/")
    return all(p and ":" not in p and len(p) < 100 and p.lower() not in SKIP and not p.startswith("~") for p in parts)


def build(rules, root, make, is_file):
    root.mkdir(parents=True, exist_ok=True)
    used, placed = set(), {}
    for rule in rules:
        if rule["icon"] in placed:
            continue
        for candidate in candidates(rule):
            if "/" in candidate:
                relatives = [candidate]
            elif rule["path"]:
                relatives = [f"{sub}/{candidate}" if sub else candidate for sub in folders_in(rule["regex"].pattern)]
            else:
                relatives = [candidate]
            for relative in relatives:
                name = relative.rsplit("/", 1)[-1]
                if not valid(relative) or relative.lower() in used or (is_file and taken_by_glance(name)):
                    continue
                # Match against the real absolute path, the way the IDE will.
                if resolve(rules, name, str(root / relative)) == rule["icon"]:
                    make(root / relative)
                    used.add(relative.lower())
                    placed[rule["icon"]] = relative
                    break
            if rule["icon"] in placed:
                break
    return placed, {r["icon"] for r in rules}


def make_file(path):
    path.parent.mkdir(parents=True, exist_ok=True)
    path.touch()


def make_folder(path):
    path.mkdir(parents=True, exist_ok=True)


GLANCE = {
    "README.md": "# Glance Icons gallery\n",
    # Angular 20+ root files: the catalogue would show Node.js, Expo and Next.js logos for these names.
    "app.ts": "import { Component } from '@angular/core';\n\n@Component({ selector: 'app-root', template: '' })\nexport class App {}\n",
    "app.config.ts": "import { ApplicationConfig } from '@angular/core';\n\nexport const appConfig: ApplicationConfig = { providers: [] };\n",
    "main.ts": "import { bootstrapApplication } from '@angular/platform-browser';\nimport { App } from './app';\nimport { appConfig } from './app.config';\n\nbootstrapApplication(App, appConfig);\n",
    "layout.ts": "import { Component } from '@angular/core';\n\n@Component({ selector: 'app-layout', template: '' })\nexport class Layout {}\n",
    "user-card.ts": "import { Component } from '@angular/core';\n\n@Component({ selector: 'app-user-card', template: '' })\nexport class UserCard {}\n",
    "user-card.component.ts": "",
    "cart.ts": "import { Service } from '@angular/core';\n\n@Service()\nexport class Cart {}\n",
    "auth.service.ts": "",
    "tooltip.ts": "import { Directive } from '@angular/core';\n\n@Directive({ selector: '[appTooltip]' })\nexport class Tooltip {}\n",
    "euro-pipe.ts": "",
    "admin-guard.ts": "",
    "log-interceptor.ts": "",
    "app.routes.ts": "",
    "shared.module.ts": "",
    "users.store.ts": "",
    "user.model.ts": "",
    "utils.ts": "export const sum = (a: number, b: number) => a + b;\n",
    "angular.json": "{}\n",
    "Main.java": "public class Main {\n}\n",
    "MainTest.java": "class MainTest {\n}\n",
    "App.kt": "class App\n",
    "AppTest.kt": "class AppTest\n",
    "k8s-by-content/web.yaml": "apiVersion: apps/v1\nkind: Deployment\nmetadata:\n  name: web\n",
    ".github/workflows/release.yml": "on: push\n",
}

if __name__ == "__main__":
    target = Path(sys.argv[1])
    for relative, content in GLANCE.items():
        path = target / "glance" / relative
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(content)
    (target / "glance/directives").mkdir(exist_ok=True)
    file_placed, file_icons = build(load("icon_associations.xml", "fileNames"), target / "files", make_file, True)
    folder_placed, folder_icons = build(load("folder_associations.xml", "folderNames"), target / "folders", make_folder, False)
    print(f"file icons shown: {len(file_placed)} of {len(file_icons)}; folder icons shown: {len(folder_placed)} of {len(folder_icons)}")
    print("file icons without a usable example:", ", ".join(sorted(file_icons - set(file_placed))[:40]))
    print("folder icons without a usable example:", ", ".join(sorted(folder_icons - set(folder_placed))[:40]))
