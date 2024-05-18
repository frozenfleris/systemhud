package gov.vitality.syshud.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import net.minecraft.client.MinecraftClient
import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardCopyOption

object KFileUtils {
    val configDirectory: File
        get() {
            val configDir = File(MinecraftClient.getInstance().runDirectory, "config/syshud")
            synchronized(KFileUtils) { // Synchronise this in case multiple threads try this.
                if (!configDir.isDirectory && configDir.exists()) LogManager.getLogger()
                    .warn("[HE] A file was found in place of the config folder!") else if (!configDir.isDirectory) {
                    val created = configDir.mkdirs()
                    if (!created) {
                        LogManager.getLogger()
                            .warn("Failed to create config folder \"SysHud\"! This may cause errors!")
                    }
                }
            }
            return configDir
        }

    @Throws(IOException::class)
    fun writeJsonToFile(name: String, `object`: JsonObject?, gson: Gson) {
        // Write to temporary file.
        val dir = configDirectory
        FileWriter(File(dir, "$name~")).use { fileWriter -> gson.toJson(`object`, fileWriter) }
        // Overwrite proper file atomically with temporary file after write has finished.
        Files.move(
            dir.toPath().resolve("$name~"),
            dir.toPath().resolve(name),
            StandardCopyOption.ATOMIC_MOVE,
            StandardCopyOption.REPLACE_EXISTING
        )
    }
}