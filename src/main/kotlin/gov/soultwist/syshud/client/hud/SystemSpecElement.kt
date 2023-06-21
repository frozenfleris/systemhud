package gov.soultwist.syshud.client.hud

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
        val jVer = jSpec.VERSION
        val jVerArch = jSpec.VERSION_ARCH
        val os = sysSpec.OS

        if(ModConfig.ENABLE_PC_SPECS.value()) {
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
                if (ModConfig.HIDE_JRE_ARCHITECTURE.value()) {
                    drawContext?.drawText(
                        sysRender,
                        jVer,
                        HUDConstraints.hstack.leading(),
                        HUDConstraints.vstack.bottom() - i,
                        Colors.WHITE,
                        ts
                    )
                }
                else {
                    drawContext?.drawText(
                        sysRender,
                        jVerArch,
                        HUDConstraints.hstack.leading(),
                        HUDConstraints.vstack.bottom() - i,
                        Colors.WHITE,
                        ts
                    )

                }
                if (!ModConfig.HIDE_JRE_VENDOR.value()) {
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