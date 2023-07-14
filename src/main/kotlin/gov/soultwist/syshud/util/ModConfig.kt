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
    val SHOW_JRE_ARCHITECTURE = Value("show_jre_arch", false)
    val SHOW_JRE_VENDOR = Value("show_jre_vendor", false)
    val ENABLE_MULTILINE = Value("enable_multiline", true)
    val FLIP_DATE_AND_TIME = Value("flip_date_and_time", false)
    val DATE_AND_TIME_FORMATTING = Value("date_and_time_formatting", "yyyy-MM-dd hh:mm a")
    val DATE_FORMATTING = Value("date_formatting", "yyyy-MM-dd")
    val TIME_FORMATTING = Value("time_formatting", "hh:mm a")
    val HUD_HSTACK_PADDING = Value("time_horizontal_padding", 2)
    val HUD_VSTACK_PADDING = Value("time_vertical_padding", 2)
    val TEXT_SHADOW = Value("text_shadow", true)
    val SHOW_DEVICE_CPU = Value("show_device_cpu", false)
    val FLIP_VERSION_AND_SYSTEM = Value("flip_version_and_system", false)
    val TEXT_COLOR = Value("text_color", -1)
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
                        SHOW_JRE_ARCHITECTURE.read(
                            root["SHOW_jre_arch"]
                        ) { obj: JsonElement -> obj.asBoolean }
                        SHOW_JRE_VENDOR.read(
                            root["SHOW_jre_vendor"]
                        ) { obj: JsonElement -> obj.asBoolean }
                        ENABLE_MULTILINE.read(
                            root["enable_multiline"]
                        ) { obj: JsonElement -> obj.asBoolean }
                        FLIP_DATE_AND_TIME.read(
                            root["flip_date_and_time"]
                        ) { obj: JsonElement -> obj.asBoolean }
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
                        TEXT_COLOR.read(
                            root["text_color"]
                        ) { obj: JsonElement -> obj.asInt }
                        SHOW_DEVICE_CPU.read(
                            root["show_device_cpu"]
                        ) { obj: JsonElement -> obj.asBoolean }
                        FLIP_VERSION_AND_SYSTEM.read(
                            root["flip_version_and_system"]
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
            val shudObj = JsonObject()
            shudObj.addProperty(ENABLE_SYSTEM_TIME.name(), ENABLE_SYSTEM_TIME.value())
            shudObj.addProperty(ENABLE_CLIENT_VERSION.name(), ENABLE_CLIENT_VERSION.value())
            shudObj.addProperty(ENABLE_PC_SPECS.name(), ENABLE_PC_SPECS.value())
            shudObj.addProperty(SHOW_JRE_ARCHITECTURE.name(), SHOW_JRE_ARCHITECTURE.value())
            shudObj.addProperty(SHOW_JRE_VENDOR.name(), SHOW_JRE_VENDOR.value())
            shudObj.addProperty(ENABLE_MULTILINE.name(), ENABLE_MULTILINE.value())
            shudObj.addProperty(FLIP_DATE_AND_TIME.name(), FLIP_DATE_AND_TIME.value())
            shudObj.addProperty(DATE_AND_TIME_FORMATTING.name(), DATE_AND_TIME_FORMATTING.value())
            shudObj.addProperty(DATE_FORMATTING.name(), DATE_FORMATTING.value())
            shudObj.addProperty(TIME_FORMATTING.name(), TIME_FORMATTING.value())
            shudObj.addProperty(HUD_HSTACK_PADDING.name(), HUD_HSTACK_PADDING.value())
            shudObj.addProperty(HUD_VSTACK_PADDING.name(), HUD_VSTACK_PADDING.value())
            shudObj.addProperty(TEXT_SHADOW.name(), TEXT_SHADOW.value())
            shudObj.addProperty(TEXT_COLOR.name(), TEXT_COLOR.value())
            shudObj.addProperty(SHOW_DEVICE_CPU.name(), SHOW_DEVICE_CPU.value())
            shudObj.addProperty(FLIP_VERSION_AND_SYSTEM.name(), FLIP_VERSION_AND_SYSTEM.value())
            root.add("syshud", shudObj)

            // Write to file.
            KFileUtils.writeJsonToFile("config.json", root, gson)
            LogManager.getLogger().info("Saved settings.")
        } catch (e: IOException) {
            LogManager.getLogger().error("Failed to save settings!", e)
        }
    }

    class Value<ValueType>(private val name: String, private var value: ValueType) {
        private val defaultValue: ValueType = value

        fun name(): String {
            return name
        }

        fun value(): ValueType {
            return value
        }

        fun defaultValue(): ValueType {
            return defaultValue
        }

        fun setValue(value: ValueType) {
            this.value = value
        }

        fun <V> read(element: V, elementToValue: Function<V, ValueType>) {
            try {
                setValue(elementToValue.apply(element))
            } catch (exception: Exception) {
                LogManager.getLogger().warn("Failed to read $name from JSON config (type mismatch)")
            }
        }
    }
}