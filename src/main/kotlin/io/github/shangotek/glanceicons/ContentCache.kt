package io.github.shangotek.glanceicons

import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import java.io.IOException

/**
 * Reads a small text file once per saved version and keeps what [classify] made of it on the file itself,
 * so the project tree never re-reads a file that hasn't changed.
 */
internal class ContentCache<T : Any>(keyName: String, private val classify: (CharSequence) -> T?) {

    private class Entry<T>(val stamp: Long, val value: T?)

    private val key = Key.create<Entry<T>>(keyName)

    fun get(file: VirtualFile): T? {
        val stamp = file.modificationStamp
        file.getUserData(key)?.let { if (it.stamp == stamp) return it.value }
        val value = if (file.length > MAX_SIZE) null else classify(readText(file))
        file.putUserData(key, Entry(stamp, value))
        return value
    }

    private fun readText(file: VirtualFile): String =
        try {
            String(file.contentsToByteArray(), Charsets.UTF_8)
        } catch (e: IOException) {
            ""
        }

    private companion object {
        // Source and manifest files are small; skipping huge files keeps the project tree fast.
        const val MAX_SIZE = 256 * 1024
    }
}
