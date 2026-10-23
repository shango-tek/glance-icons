package io.github.shangotek.glanceicons

import java.util.concurrent.ConcurrentHashMap
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Element

/**
 * The Atom Material Icons catalogue (MIT, bundled under `/atom`): about 1,800 file rules and 350 folder rules.
 *
 * Rules are applied the way Atom applies them: a case-insensitive match of the whole name, or of the whole path when
 * the pattern contains `/`, and the highest priority wins. Atom ships many rules switched off; Glance turns them all on.
 */
object AtomCatalog {

    /** Resource path of the icon for a file, or null when no rule matches. */
    fun fileIcon(name: String, path: String = name): String? = files.iconFor(name, path)

    /** Resource path of the icon for a folder, or null when no rule matches. */
    fun folderIcon(name: String, path: String = name): String? = folders.iconFor(name, path)

    internal val files by lazy { RuleSet.load("/atom/icon_associations.xml", "/atom/files/") }
    internal val folders by lazy { RuleSet.load("/atom/folder_associations.xml", "/atom/folders/") }

    internal class Rule(val regex: Regex, val usesPath: Boolean, val icon: String)

    internal class RuleSet(rules: List<Rule>) {

        // Highest priority first; ties keep file order, like Atom's maxByOrNull.
        val byName = rules.filter { !it.usesPath }
        val byPath = rules.filter { it.usesPath }
        private val order = rules.withIndex().associate { (index, rule) -> rule to index }
        private val nameCache = ConcurrentHashMap<String, Match>()

        private class Match(val rule: Rule?)

        fun iconFor(name: String, path: String): String? {
            val byNameMatch = nameCache.computeIfAbsent(name) { n -> Match(byName.firstOrNull { it.regex.matches(n) }) }.rule
            val byPathMatch = byPath.firstOrNull { it.regex.matches(path) }
            val best = listOfNotNull(byNameMatch, byPathMatch).minByOrNull { order.getValue(it) }
            return best?.icon
        }

        companion object {
            fun load(resource: String, iconDir: String): RuleSet {
                val stream = AtomCatalog::class.java.getResourceAsStream(resource) ?: error("Missing $resource")
                val factory = DocumentBuilderFactory.newInstance().apply {
                    setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
                }
                val regexes = stream.use { factory.newDocumentBuilder().parse(it) }.getElementsByTagName("regex")
                val rules = (0 until regexes.length)
                    .map { regexes.item(it) as Element }
                    .mapNotNull { element -> toRule(element, iconDir)?.let { (element.getAttribute("priority").toIntOrNull() ?: 100) to it } }
                    .withIndex()
                    .sortedWith(compareByDescending<IndexedValue<Pair<Int, Rule>>> { it.value.first }.thenBy { it.index })
                    .map { it.value.second }
                return RuleSet(rules)
            }

            private fun toRule(element: Element, iconDir: String): Rule? {
                val pattern = element.getAttribute("pattern")
                val icon = element.getAttribute("icon").substringAfterLast('/')
                if (pattern.isEmpty() || icon.isEmpty()) return null
                val regex = try {
                    Regex(pattern, RegexOption.IGNORE_CASE)
                } catch (e: IllegalArgumentException) {
                    return null
                }
                return Rule(regex, pattern.contains('/'), iconDir + icon)
            }
        }
    }
}
