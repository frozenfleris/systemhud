package gov.soultwist.syshud.client.hud

import gov.soultwist.syshud.client.hud.backend.HUDConstraints
import gov.soultwist.syshud.util.ModConfig
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.util.Colors
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

object RealTimeElement : HudRenderCallback {
    override fun onHudRender(drawContext: DrawContext?, tickDelta: Float) {

        val x = 0
        val y = 0

        val client = MinecraftClient.getInstance()

        val dr = SimpleDateFormat(ModConfig.DATE_AND_TIME_FORMATTING.value()).format(Date.from(Instant.now())).toString()
        val dt = SimpleDateFormat(ModConfig.DATE_FORMATTING.value()).format(Date.from(Instant.now())).toString()
        val ti = SimpleDateFormat(ModConfig.TIME_FORMATTING.value()).format(Date.from(Instant.now())).toString()



        val ts = ModConfig.TEXT_SHADOW.value()

        val width = client.window.scaledWidth
        val height = client.window.scaledHeight

        x ; width / 2
        y ; height


            if (ModConfig.ENABLE_SYSTEM_TIME.value()) {
                if (!client.options.debugEnabled) {
                    val dualRenderer = client.textRenderer
                    val dateRenderer = client.textRenderer
                    val timeRenderer = client.textRenderer

                    if (!ModConfig.ENABLE_MULTILINE.value()) {
                        drawContext?.drawText(
                            dualRenderer,
                            dr,
                            HUDConstraints.hstack.leading(),
                            HUDConstraints.vstack.top(),
                            ModConfig.TEXT_COLOR.value(),
                            ts

                        )
                    }
                    else
                    {

                            val i = dateRenderer.fontHeight
                            val n = timeRenderer.fontHeight
                            drawContext?.drawText(
                                dateRenderer,
                                dt,
                                HUDConstraints.hstack.leading(),
                                if (ModConfig.FLIP_DATE_AND_TIME.value()){
                                    HUDConstraints.vstack.top() + n
                                } else { HUDConstraints.vstack.top() },
                                ModConfig.TEXT_COLOR.value(),
                                ts
                            )
                            drawContext?.drawText(
                                timeRenderer,
                                ti,
                                HUDConstraints.hstack.leading(),
                                if (ModConfig.FLIP_DATE_AND_TIME.value()) { HUDConstraints.vstack.top() }
                                else {
                                    HUDConstraints.vstack.top() + i
                                     },

                                ModConfig.TEXT_COLOR.value(),
                                ts
                            )



                        }
                    }
                }
            }
        }


