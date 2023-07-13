package gov.soultwist.syshud.client.screen

import gov.soultwist.syshud.client.hud.backend.ConfigLiteral
import gov.soultwist.syshud.util.ModConfig
import me.shedaniel.clothconfig2.api.ConfigBuilder
import me.shedaniel.clothconfig2.api.ConfigCategory
import me.shedaniel.clothconfig2.gui.entries.DropdownBoxEntry
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import net.minecraft.util.Colors

object ModConfigScreen {
    fun getConfigScreen(parent: Screen?): ConfigBuilder {
        val builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(Text.translatable("syshud.config_title"))
            .setSavingRunnable {
                ModConfig.saveToFile()
                ModConfig.loadFromFile()
            }

        //Catagories
        val general = builder.getOrCreateCategory(Text.translatable("syshud.config_title"))
        val customization = builder.getOrCreateCategory(Text.translatable("syshud.advanced_hud_title"))
        val experimental = builder.getOrCreateCategory(Text.translatable(
            "syshud.experimental_config_title"
        ))

        //Entries by Category
        //General
        addBooleanEntry(general, builder, ModConfig.ENABLE_SYSTEM_TIME)
        addBooleanEntry(general, builder, ModConfig.ENABLE_CLIENT_VERSION)
        addBooleanEntry(general, builder, ModConfig.ENABLE_PC_SPECS)
        addBooleanEntry(general, builder, ModConfig.SHOW_JRE_ARCHITECTURE)
        addBooleanEntry(general, builder, ModConfig.SHOW_JRE_VENDOR)
        addBooleanEntry(general, builder, ModConfig.SHOW_DEVICE_CPU)

        //Advanced HUD Settings
        addBooleanEntry(customization, builder, ModConfig.ENABLE_MULTILINE)
        if (!ModConfig.ENABLE_MULTILINE.value()) {
            dateAndTimeEntry(customization, builder, ModConfig.DATE_AND_TIME_FORMATTING)
        } else {
            dateAndTimeEntry(customization, builder, ModConfig.DATE_FORMATTING)
            addStringEntry(customization, builder, ModConfig.TIME_FORMATTING)
            addBooleanEntry(customization, builder, ModConfig.FLIP_DATE_AND_TIME)
        }
        addBooleanEntry(customization, builder, ModConfig.FLIP_VERSION_AND_SYSTEM)
        addIntEntry(customization, builder, ModConfig.HUD_HSTACK_PADDING, 0, 20)
        addIntEntry(customization, builder, ModConfig.HUD_VSTACK_PADDING, 0, 20)
        addBooleanEntry(customization, builder, ModConfig.TEXT_SHADOW)

        addColorEntry(experimental, builder, ModConfig.TEXT_COLOR)

        return builder
    }

    private fun addStringEntry(
        category: ConfigCategory, builder: ConfigBuilder,
        value: ModConfig.Value<String>
    ) {
        category.addEntry(
            builder.entryBuilder()
                .startStrField(Text.translatable("syshud.configs.${value.name()}.label"), value.value())
                .setDefaultValue(value.defaultValue())
                .setSaveConsumer(value::setValue)
                .build()
        )
    }

    private fun addBooleanEntry(
        category: ConfigCategory, builder: ConfigBuilder,
        value: ModConfig.Value<Boolean>
    ) {
        category.addEntry(
            builder.entryBuilder()
                .startBooleanToggle(Text.translatable("syshud.configs.${value.name()}.label"), value.value())
                .setDefaultValue(value.defaultValue())
                .setSaveConsumer(value::setValue)
                .build()
        )
    }

    private fun addIntEntry(
        category: ConfigCategory, builder: ConfigBuilder,
        value: ModConfig.Value<Int>, min: Int, max: Int
    ) {
        category.addEntry(
            builder.entryBuilder()
                .startIntSlider(
                    Text.translatable("syshud.configs.${value.name()}.label"),
                    value.value(),
                    min,
                    max
                )
                .setDefaultValue(value.defaultValue())
                .setSaveConsumer(value::setValue)
                .build()
        )
    }

    private fun addColorEntry(
        category: ConfigCategory, builder: ConfigBuilder, value: ModConfig.Value<Int>
    ) {
        category.addEntry(
            builder.entryBuilder()
                .startAlphaColorField(
                    Text.translatable("syshud.configs.${value.name()}.label"),
                    value.value()
                ).setDefaultValue(value.defaultValue())
                .setSaveConsumer(value::setValue)
                .build()
        )
    }


    //Setting-specific entries

    private fun dateAndTimeEntry(
        category: ConfigCategory, builder: ConfigBuilder,
        value: ModConfig.Value<String>
    ) {
        category.addEntry(
            builder.entryBuilder()
                .startStrField(Text.translatable("syshud.configs.${value.name()}.label"), value.value())
                .setDefaultValue(value.defaultValue())
                .setTooltip(Text.literal(ConfigLiteral.Tooltips.date_format_warn))
                .setSaveConsumer(value::setValue)
                .build()
        )
    }

}