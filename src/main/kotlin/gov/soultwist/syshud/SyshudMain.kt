package gov.soultwist.syshud

import gov.soultwist.syshud.client.hud.RealTimeElement
import gov.soultwist.syshud.client.hud.SystemSpecElement
import gov.soultwist.syshud.client.hud.VersionElement
import gov.soultwist.syshud.util.ModConfig
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import org.slf4j.LoggerFactory

object SyshudMain : ClientModInitializer {
    private val logger = LoggerFactory.getLogger("syshud")

    override fun onInitializeClient() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        logger.info("Kotlin Mod SystemHUD (id: syshud) loaded")
        val hrc = HudRenderCallback.EVENT

        hrc.register(RealTimeElement)
        hrc.register(SystemSpecElement)
        hrc.register(VersionElement)
        ModConfig.loadFromFile()
    }
}