package io.github.shangotek.glanceicons

import com.intellij.openapi.util.IconLoader
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.awt.image.BufferedImage
import java.io.File
import javax.swing.Icon

/** Draws every bundled icon the way the IDE does, so a broken or wrongly sized SVG fails here instead of in the tree. */
class IconRenderingTest : BasePlatformTestCase() {

    fun `test every icon draws at 16 by 16 and is visible`() {
        val problems = mutableListOf<String>()
        for (dir in listOf("icons/files", "icons/folders", "atom/files", "atom/folders")) {
            val svgs = File("src/main/resources/$dir").listFiles { f -> f.name.endsWith(".svg") && !f.name.endsWith("_dark.svg") && !f.name.contains("@2x") }!!
            for (svg in svgs.sortedBy { it.name }) {
                val path = "/$dir/${svg.name}"
                val icon = Icons.load(path)
                if (icon.iconWidth != 16 || icon.iconHeight != 16) {
                    problems += "$path is ${icon.iconWidth}x${icon.iconHeight}"
                    continue
                }
                // Scale 2 draws <name>@2x.svg where there is one, the dark icon <name>_dark.svg.
                val dark = IconLoader.getDarkIcon(icon, true)
                for ((variant, scale) in listOf(icon to 1, icon to 2, dark to 1, dark to 2)) {
                    if (visiblePixels(variant, scale) == 0) problems += "$path draws nothing at scale $scale${if (variant === dark) " in the dark theme" else ""}"
                }
            }
        }
        assertEmpty(problems.joinToString("\n"), problems)
    }

    private fun visiblePixels(icon: Icon, scale: Int): Int {
        val size = 16 * scale
        val image = BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB)
        val graphics = image.createGraphics()
        graphics.scale(scale.toDouble(), scale.toDouble())
        icon.paintIcon(null, graphics, 0, 0)
        graphics.dispose()
        return (0 until size).sumOf { x -> (0 until size).count { y -> image.getRGB(x, y) ushr 24 > 0 } }
    }
}
