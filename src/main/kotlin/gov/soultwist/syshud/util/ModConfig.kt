package gov.soultwist.syshud.util

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.util.function.Function


object ModConfig {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    val ENABLE_SYSTEM_TIME = Value("enable_irl_time", true)
    val ENABLE_CLIENT_VERSION = Value("show_version", true)
    val ENABLE_PC_SPECS = Value("enable_pc_specs", true)
    val HIDE_JRE_ARCHITECTURE = Value("hide_jre_arch", false)
    val HIDE_JRE_VENDOR = Value("hide_jre_vendor", false)
    val ENABLE_MULTILINE = Value("enable_multiline", true)
    val FLIP_DATE_AND_TIME = Value("flip_date_and_time", false)
    val DATE_AND_TIME_FORMATTING = Value("date_and_time_formatting", "yyyy-MM-dd hh:mm a")
    val DATE_FORMATTING = Value("date_formatting", "yyyy-MM-dd")
    val TIME_FORMATTING = Value("time_formatting", "hh:mm a")
    val HUD_HSTACK_PADDING = Value("time_horizontal_padding", 2)
    val HUD_VSTACK_PADDING = Value("time_vertical_padding", 2)
    val TEXT_SHADOW = Value("text_shadow", true)
    fun loadFromFile() {
        val configFile: File = File(KFileUtils.configDirectory, "config.json")
        try {

            FileReader(configFile).use { fileReader ->
                val element = gson.fromJson(
                    fileReader,
                    JsonElement::class.java
                )
                if (element != null && element.isJsonObject) {
                    if (element.asJsonObject["syshud"].isJsonObject) {
                        val root =
                            element.asJsonObject["syshud"].asJsonObject
                        ENABLE_SYSTEM_TIME.read(
                            root["enable_irl_time"]
                        ) { obj: JsonElement -> obj.asBoolean }
                        ENABLE_CLIENT_VERSION.read(
                            root["show_version"]
                        ) { obj: JsonElement -> obj.asBoolean }
                        ENABLE_PC_SPECS.read(
                            root["enable_pc_specs"]
                        ) { obj: JsonElement -> obj.asBoolean }
                        HIDE_JRE_ARCHITECTURE.read(
                            root["hide_jre_arch"]
                        ) { obj: JsonElement -> obj.asBoolean }
                        HIDE_JRE_VENDOR.read(
                            root["hide_jre_vendor"]
                        ) { obj: JsonElement -> obj.asBoolean }
                        ENABLE_MULTILINE.read(
                            root["enable_multiline"]
                        ) { obj: JsonElement -> obj.asBoolean }
                        FLIP_DATE_AND_TIME.read(
                            root["flip_date_and_time"]
                        ) {obj: JsonElement -> obj.asBoolean }
                        DATE_AND_TIME_FORMATTING.read(
                            root["date_and_time_formatting"]
                        ) { obj: JsonElement -> obj.asString }
                        DATE_FORMATTING.read(
                            root["date_formatting"]
                        ) { obj: JsonElement -> obj.asString }
                        TIME_FORMATTING.read(
                            root["time_formatting"]
                        ) { obj: JsonElement -> obj.asString }
                        HUD_HSTACK_PADDING.read(
                            root["time_horizontal_padding"]
                        ) { obj: JsonElement -> obj.asInt }
                        HUD_VSTACK_PADDING.read(
                            root["time_vertical_padding"]
                        ) { obj: JsonElement -> obj.asInt }
                        TEXT_SHADOW.read(
                            root["text_shadow"]
                        ) { obj: JsonElement -> obj.asBoolean }
                    }
                }
            }
        } catch (exception: IOException) {
            if (exception !is FileNotFoundException) {
                LogManager.getLogger().error("Failed to read config", exception)
            }
        }
    }

    fun saveToFile() {
        try {
            // Create JSON object.
            val root = JsonObject()
            val hraddonsObject = JsonObject()
            hraddonsObject.addProperty(ENABLE_SYSTEM_TIME.name(), ENABLE_SYSTEM_TIME.value())
            hraddonsObject.addProperty(ENABLE_CLIENT_VERSION.name(), ENABLE_CLIENT_VERSION.value())
            hraddonsObject.addProperty(ENABLE_PC_SPECS.name(), ENABLE_PC_SPECS.value())
            hraddonsObject.addProperty(HIDE_JRE_ARCHITECTURE.name(), HIDE_JRE_ARCHITECTURE.value())
            hraddonsObject.addProperty(HIDE_JRE_VENDOR.name(), HIDE_JRE_VENDOR.value())
            hraddonsObject.addProperty(ENABLE_MULTILINE.name(), ENABLE_MULTILINE.value())
            hraddonsObject.addProperty(FLIP_DATE_AND_TIME.name(), FLIP_DATE_AND_TIME.value())
            hraddonsObject.addProperty(DATE_AND_TIME_FORMATTING.name(), DATE_AND_TIME_FORMATTING.value())
            hraddonsObject.addProperty(DATE_FORMATTING.name(), DATE_FORMATTING.value())
            hraddonsObject.addProperty(TIME_FORMATTING.name(), TIME_FORMATTING.value())
            hraddonsObject.addProperty(HUD_HSTACK_PADDING.name(), HUD_HSTACK_PADDING.value())
            hraddonsObject.addProperty(HUD_VSTACK_PADDING.name(), HUD_VSTACK_PADDING.value())
            hraddonsObject.addProperty(TEXT_SHADOW.name(), TEXT_SHADOW.value())
            root.add("syshud", hraddonsObject)

            // Write to file.
            KFileUtils.writeJsonToFile("config.json", root, gson)
            LogManager.getLogger().info("Saved settings.")
        } catch (e: IOException) {
            LogManager.getLogger().error("Failed to save settings!", e)
        }
    }

    class Value<T>(private val name: String, private var value: T) {
        private val defaultValue: T

        init {
            defaultValue = value
        }

        fun name(): String {
            return name
        }

        fun value(): T {
            return value
        }

        fun defaultValue(): T {
            return defaultValue
        }

        fun setValue(value: T) {
            this.value = value
        }

        fun <V> read(element: V, elementToValue: Function<V, T>) {
            try {
                setValue(elementToValue.apply(element))
            } catch (exception: Exception) {
                LogManager.getLogger().warn("Failed to read $name from JSON config (type mismatch)")
            }
        }
    }
}