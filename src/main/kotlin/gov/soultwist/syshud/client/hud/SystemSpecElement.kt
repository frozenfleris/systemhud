package gov.soultwist.syshud.client.hud

import gov.soultwist.syshud.client.hud.backend.HUDConstraints
import gov.soultwist.syshud.client.hud.backend.HUDParams
import gov.soultwist.syshud.util.ModConfig
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.util.Colors

object SystemSpecElement : HudRenderCallback {
    override fun onHudRender(drawContext: DrawContext?, tickDelta: Float) {
        val client = MinecraftClient.getInstance()

        val jSpec = HUDParams.getJavaSpecs
        val sysSpec = HUDParams.getSystemSpecs

        val ts = ModConfig.TEXT_SHADOW.value()

        val jVendor = jSpec.VENDOR

        val newJVer = if (ModConfig.SHOW_JRE_ARCHITECTURE.value()) {
            jSpec.VERSION_ARCH
        } else {
            jSpec.VERSION
        }


        val os = if (ModConfig.SHOW_DEVICE_CPU.value()) {
            sysSpec.OS_WITH_CPU
        } else {
            sysSpec.OS
        }

        if (ModConfig.ENABLE_PC_SPECS.value()) {
            if (!client.options.debugEnabled) {
                val sysRender = client.textRenderer
                val i = 9
                val i1 = 19
                drawContext?.drawText(
                    sysRender,
                    os,
                    HUDConstraints.hstack.leading(),
                    HUDConstraints.vstack.bottom(),
                    Colors.WHITE,
                    ts
                )

                drawContext?.drawText(
                    sysRender,
                    newJVer,
                    HUDConstraints.hstack.leading(),
                    HUDConstraints.vstack.bottom() - i,
                    Colors.WHITE,
                    ts
                )

                    if (ModConfig.SHOW_JRE_VENDOR.value()) {
                        drawContext?.drawText(
                            sysRender,
                            jVendor,
                            HUDConstraints.hstack.leading(),
                            HUDConstraints.vstack.bottom() - i1,
                            Colors.WHITE,
                            ts
                        )
                    }
            }
        }
    }
}