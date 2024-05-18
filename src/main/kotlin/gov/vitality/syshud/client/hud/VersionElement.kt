package gov.vitality.syshud.client.hud

import gov.vitality.syshud.client.hud.backend.HUDConstraints
import gov.vitality.syshud.client.hud.backend.HUDParams
import gov.vitality.syshud.util.ModConfig
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext

object VersionElement : HudRenderCallback {
    override fun onHudRender(drawContext: DrawContext, tickDelta: Float) {
        val client = MinecraftClient.getInstance()
        val mcVers = HUDParams.getGameClientSpecs.MINECRAFT_VERSION
        val versionRenderer = client.textRenderer
        val ts = ModConfig.TEXT_SHADOW.value()

        if (ModConfig.ENABLE_CLIENT_VERSION.value()) {
            drawContext.drawText(
                versionRenderer,
                mcVers,
                if (!ModConfig.FLIP_VERSION_AND_SYSTEM.value()) {
                    HUDConstraints.hstack.trailing(mcVers)
                } else {
                    HUDConstraints.hstack.leading()
                },
                HUDConstraints.vstack.bottom(),
                ModConfig.TEXT_COLOR.value(),
                ts
            )

        }
    }

}