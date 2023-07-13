package gov.soultwist.syshud.client.hud

import gov.soultwist.syshud.client.hud.backend.HUDConstraints
import gov.soultwist.syshud.client.hud.backend.HUDParams
import gov.soultwist.syshud.util.ModConfig
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.util.Colors

object VersionElement: HudRenderCallback {
    override fun onHudRender(drawContext: DrawContext, tickDelta: Float) {
        val client = MinecraftClient.getInstance()
        val mcVers = HUDParams.getGameClientSpecs.MINECRAFT_VERSION
        val versionRenderer = client.textRenderer
        val ts = ModConfig.TEXT_SHADOW.value()
        val i = versionRenderer.fontHeight
        val j = drawContext.scaledWindowWidth

        if (ModConfig.ENABLE_CLIENT_VERSION.value()) {
            drawContext.drawText(
                versionRenderer,
                mcVers,
                if (!ModConfig.FLIP_VERSION_AND_SYSTEM.value())
                {
                    HUDConstraints.hstack.trailing(mcVers)
                }
                else {
                    HUDConstraints.hstack.leading()
                     },
                HUDConstraints.vstack.bottom(),
                ModConfig.TEXT_COLOR.value(),
                ts
            )

        }
    }

}