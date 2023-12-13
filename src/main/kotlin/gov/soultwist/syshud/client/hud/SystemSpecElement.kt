package gov.soultwist.syshud.client.hud

import gov.soultwist.syshud.client.hud.backend.HUDConstraints
import gov.soultwist.syshud.client.hud.backend.HUDParams
import gov.soultwist.syshud.util.ModConfig
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext

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
            if (!client.inGameHud.debugHud.shouldShowDebugHud()) {
                val sysRender = client.textRenderer
                val i = 9
                val i1 = 19
                drawContext?.drawText(
                    sysRender,
                    os,
                    if (
                        !ModConfig.FLIP_VERSION_AND_SYSTEM.value()
                    ) {
                        HUDConstraints.hstack.leading()
                    } else {
                        HUDConstraints.hstack.trailing(os)
                    },
                    HUDConstraints.vstack.bottom(),
                    ModConfig.TEXT_COLOR.value(),
                    ts
                )

                drawContext?.drawText(
                    sysRender,
                    newJVer,
                    if (
                        !ModConfig.FLIP_VERSION_AND_SYSTEM.value()
                    ) {
                        HUDConstraints.hstack.leading()
                    } else {
                        HUDConstraints.hstack.trailing(newJVer)
                    },
                    HUDConstraints.vstack.bottom() - i,
                    ModConfig.TEXT_COLOR.value(),
                    ts
                )

                if (ModConfig.SHOW_JRE_VENDOR.value()) {
                    drawContext?.drawText(
                        sysRender,
                        jVendor,
                        if (
                            !ModConfig.FLIP_VERSION_AND_SYSTEM.value()
                        ) {
                            HUDConstraints.hstack.leading()
                        } else {
                            HUDConstraints.hstack.trailing(jVendor)
                        },
                        HUDConstraints.vstack.bottom() - i1,
                        ModConfig.TEXT_COLOR.value(),
                        ts
                    )
                }
            }
        }
    }
}