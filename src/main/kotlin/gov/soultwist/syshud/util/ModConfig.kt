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
    val ENABLE_CUSTOM_MAIN_MENU = Value("custom_main_menu", true)
    val ENABLE_SYSTEM_TIME = Value("enable_irl_time", true)
    val ENABLE_VERSIONING = Value("show_version", true)
    val ENABLE_PC_SPECS = Value("enable_pc_specs", true)
    val SHOW_MAP_INSTEAD_OF_MC = Value("show_map_version_instead", true)
    val HIDE_JRE_ARCHITECTURE = Value("hide_jre_arch", false)
    val HIDE_JRE_VENDOR = Value("hide_jre_vendor", false)
    val ENABLE_MULTILINE = Value("enable_multiline", true)
    val FLIP_DATE_AND_TIME = Value("flip_date_and_time", false)
    val DATE_AND_TIME_FORMATTING = Value("date_and_time_formatting", "yyyy-MM-dd hh:mm a")
    val DATE_FORMATTING = Value("date_formatting", "yyyy-MM-dd")
    val TIME_FORMATTING = Value("time_formatting", "hh:mm a")
    val TIME_HORIZONTAL_PADDING = Value("time_horizontal_padding", 5)
    val TIME_VERTICAL_PADDING = Value("time_vertical_padding", 5)
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
                        ENABLE_CUSTOM_MAIN_MENU.read(
                            root["custom_main_menu"]
                        ) { obj: JsonElement -> obj.asBoolean }
                        ENABLE_SYSTEM_TIME.read(
                            root["enable_irl_time"]
                        ) { obj: JsonElement -> obj.asBoolean }
                        ENABLE_VERSIONING.read(
                            root["show_version"]
                        ) { obj: JsonElement -> obj.asBoolean }
                        ENABLE_PC_SPECS.read(
                            root["enable_pc_specs"]
                        ) { obj: JsonElement -> obj.asBoolean }
                        SHOW_MAP_INSTEAD_OF_MC.read(
                            root["show_map_version_instead"]
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
                        TIME_HORIZONTAL_PADDING.read(
                            root["time_horizontal_padding"]
                        ) { obj: JsonElement -> obj.asInt }
                        TIME_VERTICAL_PADDING.read(
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
            hraddonsObject.addProperty(ENABLE_CUSTOM_MAIN_MENU.name(), ENABLE_CUSTOM_MAIN_MENU.value())
            hraddonsObject.addProperty(ENABLE_SYSTEM_TIME.name(), ENABLE_SYSTEM_TIME.value())
            hraddonsObject.addProperty(ENABLE_VERSIONING.name(), ENABLE_VERSIONING.value())
            hraddonsObject.addProperty(ENABLE_PC_SPECS.name(), ENABLE_PC_SPECS.value())
            hraddonsObject.addProperty(SHOW_MAP_INSTEAD_OF_MC.name(), SHOW_MAP_INSTEAD_OF_MC.value())
            hraddonsObject.addProperty(HIDE_JRE_ARCHITECTURE.name(), HIDE_JRE_ARCHITECTURE.value())
            hraddonsObject.addProperty(HIDE_JRE_VENDOR.name(), HIDE_JRE_VENDOR.value())
            hraddonsObject.addProperty(ENABLE_MULTILINE.name(), ENABLE_MULTILINE.value())
            hraddonsObject.addProperty(FLIP_DATE_AND_TIME.name(), FLIP_DATE_AND_TIME.value())
            hraddonsObject.addProperty(DATE_AND_TIME_FORMATTING.name(), DATE_AND_TIME_FORMATTING.value())
            hraddonsObject.addProperty(DATE_FORMATTING.name(), DATE_FORMATTING.value())
            hraddonsObject.addProperty(TIME_FORMATTING.name(), TIME_FORMATTING.value())
            hraddonsObject.addProperty(TIME_HORIZONTAL_PADDING.name(), TIME_HORIZONTAL_PADDING.value())
            hraddonsObject.addProperty(TIME_VERTICAL_PADDING.name(), TIME_VERTICAL_PADDING.value())
            hraddonsObject.addProperty(TEXT_SHADOW.name(), TEXT_SHADOW.value())
            root.add("hogwarts_extras", hraddonsObject)

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