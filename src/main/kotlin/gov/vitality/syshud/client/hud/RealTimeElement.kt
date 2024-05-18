package gov.vitality.syshud.client.hud

import gov.vitality.syshud.client.hud.backend.HUDConstraints
import gov.vitality.syshud.util.ModConfig
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

object RealTimeElement : HudRenderCallback {

    private fun determineVerticalByConfig(modifier : Int = 0) : Int{
        return if (ModConfig.INVERT_VERTICAL_DATE_TIME.value()) {
            HUDConstraints.vstack.bottom() - modifier
        } else {
            HUDConstraints.vstack.top() + modifier
        }
    }

    private fun determineHorizontalByConfig(trailingText : String, modifier : Int = 0) : Int{
        return if (ModConfig.INVERT_HORIZONTAL_DATE_TIME.value()) {
            HUDConstraints.hstack.trailing(trailingText) - modifier
        } else {
            HUDConstraints.hstack.leading() + modifier
        }
    }

    override fun onHudRender(drawContext: DrawContext?, tickDelta: Float) {


        val client = MinecraftClient.getInstance()

        val dr =
            SimpleDateFormat(ModConfig.DATE_AND_TIME_FORMATTING.value()).format(Date.from(Instant.now())).toString()
        val dt = SimpleDateFormat(ModConfig.DATE_FORMATTING.value()).format(Date.from(Instant.now())).toString()
        val ti = SimpleDateFormat(ModConfig.TIME_FORMATTING.value()).format(Date.from(Instant.now())).toString()


        val ts = ModConfig.TEXT_SHADOW.value()






        if (ModConfig.ENABLE_SYSTEM_TIME.value()) {
            if (!client.inGameHud.debugHud.shouldShowDebugHud()) {
                val dualRenderer = client.textRenderer
                val dateRenderer = client.textRenderer
                val timeRenderer = client.textRenderer

                if (!ModConfig.ENABLE_MULTILINE.value()) {
                    drawContext?.drawText(
                        dualRenderer,
                        dr,
                        determineHorizontalByConfig(dr),
                        determineVerticalByConfig(),
                        ModConfig.TEXT_COLOR.value(),
                        ts

                    )
                } else {

                    val i = dateRenderer.fontHeight
                    val n = timeRenderer.fontHeight
                    drawContext?.drawText(
                        dateRenderer,
                        dt,
                        determineHorizontalByConfig(dt),
                        if (ModConfig.FLIP_DATE_AND_TIME.value()) {
                            determineVerticalByConfig(n)
                        } else {
                            determineVerticalByConfig()
                        },
                        ModConfig.TEXT_COLOR.value(),
                        ts
                    )
                    drawContext?.drawText(
                        timeRenderer,
                        ti,
                        determineHorizontalByConfig(ti),
                        if (ModConfig.FLIP_DATE_AND_TIME.value()) {
                            determineVerticalByConfig()
                        } else {
                            determineVerticalByConfig(i)
                        },

                        ModConfig.TEXT_COLOR.value(),
                        ts
                    )


                }
            }
        }
    }
}


