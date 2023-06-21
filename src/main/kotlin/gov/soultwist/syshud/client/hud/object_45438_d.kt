package gov.soultwist.syshud.client.hud

import gov.soultwist.syshud.util.ModConfig
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.util.Colors

object object_45438_d : HudRenderCallback {
    override fun onHudRender(drawContext: DrawContext?, tickDelta: Float) {
        val x = 0
        val y = 0

        val client = MinecraftClient.getInstance()

        val dr = HUDParams.getRealTime.DUAL
        val dt = HUDParams.getRealTime.DATE
        val ti = HUDParams.getRealTime.TIME
        val ts = ModConfig.TEXT_SHADOW.value()

        val width = client.window.scaledWidth
        val height = client.window.scaledHeight

        x ; width / 2
        y ; height

        if(ModConfig.ENABLE_SYSTEM_TIME.value()) {
            if (!client.options.debugEnabled) {
                val dualRenderer = client.textRenderer
                val dateRenderer = client.textRenderer
                val timeRenderer = client.textRenderer

                if (!ModConfig.ENABLE_MULTILINE.value()) {
                    drawContext?.drawText(
                        dualRenderer,
                        dr,
                        HUDConstraints.hstack.top(),
                        HUDConstraints.vstack.leading(),
                        Colors.WHITE,
                        ts

                    )
                } else {
                    if (!ModConfig.FLIP_DATE_AND_TIME.value()) {
                        val i = dateRenderer.fontHeight
                        drawContext?.drawText(
                            dateRenderer,
                            dt,
                            HUDConstraints.hstack.top(),
                            HUDConstraints.vstack.leading(),
                            Colors.WHITE,
                            ts
                        )
                        drawContext?.drawText(
                            timeRenderer,
                            ti,
                            HUDConstraints.hstack.top(),
                            HUDConstraints.vstack.leading() + i,
                            Colors.WHITE,
                            ts
                        )
                    }
                    else {

                            val i = timeRenderer.fontHeight
                            drawContext?.drawText(
                                timeRenderer,
                                ti,
                                HUDConstraints.hstack.top(),
                                HUDConstraints.vstack.leading(),
                                Colors.WHITE,
                                ts
                            )
                            drawContext?.drawText(
                                dateRenderer,
                                dt,
                                HUDConstraints.hstack.top(),
                                HUDConstraints.vstack.leading() + i,
                                Colors.WHITE,
                                ts
                            )

                    }
                }
            }
        }


    }
}