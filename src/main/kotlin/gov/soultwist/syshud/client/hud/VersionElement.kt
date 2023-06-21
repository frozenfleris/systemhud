package gov.soultwist.syshud.client.hud

import gov.soultwist.syshud.client.hud.backend.HUDConstraints
import gov.soultwist.syshud.util.ModConfig
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.SharedConstants
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.util.Colors

object VersionElement: HudRenderCallback {
    override fun onHudRender(drawContext: DrawContext, tickDelta: Float) {
        val client = MinecraftClient.getInstance()
        val mcVers = "Minecraft " + SharedConstants.getGameVersion().name
        val versionRenderer = client.textRenderer
        val i = versionRenderer.getWidth(mcVers)
        val ts = ModConfig.TEXT_SHADOW.value()

        if (ModConfig.ENABLE_CLIENT_VERSION.value()) {
            drawContext.drawText(
                versionRenderer,
                mcVers,
                HUDConstraints.hstack.trailing(mcVers),
                HUDConstraints.vstack.bottom(),
                Colors.WHITE,
                ts
            )
        }
    }

}