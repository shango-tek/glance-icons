#!/usr/bin/env python3
"""Imports the Atom Material Icons catalogue (MIT, (c) Elior "Mallowigi" Boukhobza) into Glance Icons.

Usage: python3 tools/import_catalog.py <unpacked a-file-icon-idea jar>

Copies every icon that a rule refers to (plus its _dark variant) and both rule files, and redraws each folder's
outline on the 16 px grid so its edges land on whole pixels instead of between them.
"""
import re
import shutil
import sys
from pathlib import Path

src = Path(sys.argv[1])
dest = Path(__file__).resolve().parent.parent / "src/main/resources/atom"

# Folder outline on the 16 px grid: tab from (1,2) to (6,2), 45-degree step to the body top at y=4, body to (15,13).
FOLDER_16 = [("M", 6, 2), ("H", 2), ("C", 1.448, 2, 1, 2.448, 1, 3), ("V", 12), ("C", 1, 12.552, 1.448, 13, 2, 13),
             ("H", 14), ("C", 14.552, 13, 15, 12.552, 15, 12), ("V", 5), ("C", 15, 4.448, 14.552, 4, 14, 4), ("H", 8), ("Z",)]


def folder_outline(scale: float) -> str:
    return "".join(cmd[0] + " ".join(f"{v * scale:g}" for v in cmd[1:]) for cmd in FOLDER_16)


def referenced_icons(xml: Path) -> set[str]:
    return {Path(icon).name for icon in re.findall(r'\bicon="([^"]+)"', xml.read_text())}


def copy_icons(kind: str, names: set[str]) -> int:
    (dest / kind).mkdir(parents=True, exist_ok=True)
    copied = 0
    for name in sorted(names):
        for variant in (name, name.replace(".svg", "_dark.svg")):
            source = src / "assets/icons" / kind / variant
            if not source.exists():
                continue
            svg = size_to_16(source.read_text())
            if kind == "folders":
                svg = align_folder(svg)
            (dest / kind / variant).write_text(svg)
            copied += 1
    return copied


def size_to_16(svg: str) -> str:
    """The IDE draws an icon at its declared width and height, so every icon must say 16 (a few Atom ones say 24)."""
    root = re.search(r"<svg\b[^>]*>", svg).group(0)
    sized = re.sub(r'\s(width|height)="[^"]*"', "", root).replace("<svg", '<svg width="16" height="16"', 1)
    return svg.replace(root, sized, 1)


def align_folder(svg: str) -> str:
    view = re.search(r'viewBox="0 0 ([\d.]+) [\d.]+"', svg)
    outline = re.search(r'<path\b[^>]*\bdata-folderColor="[^"]*"[^>]*>', svg)
    if not view or not outline:
        return svg
    aligned = re.sub(r'\bd="[^"]*"', f'd="{folder_outline(float(view.group(1)) / 16)}"', outline.group(0), count=1)
    return svg.replace(outline.group(0), aligned, 1)


if __name__ == "__main__":
    shutil.rmtree(dest, ignore_errors=True)
    dest.mkdir(parents=True)
    xml_dir = src / "iconGenerator"
    for xml in ("icon_associations.xml", "folder_associations.xml"):
        shutil.copy(xml_dir / xml, dest / xml)
    files = copy_icons("files", referenced_icons(xml_dir / "icon_associations.xml"))
    folders = copy_icons("folders", referenced_icons(xml_dir / "folder_associations.xml"))
    print(f"copied {files} file icons and {folders} folder icons into {dest}")
