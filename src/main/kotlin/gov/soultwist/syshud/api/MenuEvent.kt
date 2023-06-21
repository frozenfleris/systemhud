package gov.soultwist.syshud.api

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import gov.soultwist.syshud.client.screen.ModConfigScreen
import net.minecraft.client.gui.screen.Screen


class MenuEvent : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory<Screen> { screen: Screen? ->
            ModConfigScreen.getConfigScreen(
                screen
            ).build()
        }
    }
}