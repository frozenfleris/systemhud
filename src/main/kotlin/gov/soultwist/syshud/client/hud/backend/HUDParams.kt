package gov.soultwist.syshud.client.hud.backend

import com.mojang.blaze3d.platform.GlDebugInfo
import gov.soultwist.syshud.util.TranslatorHelper
import net.minecraft.SharedConstants

interface HUDParams {
    interface getJavaSpecs {
        companion object {
            val VENDOR: String = System.getProperty("java.vendor")
            val VERSION: String = "Java ${System.getProperty("java.version")}"
            val VERSION_ARCH: String = "Java ${System.getProperty("java.version")} (${System.getProperty("os.arch")})"
        }
    }

    interface getGameClientSpecs {
        companion object {
            val MINECRAFT_VERSION = "Minecraft ${SharedConstants.getGameVersion().name}"
        }
    }

    interface getSystemSpecs {
        companion object {
            val OS = " ${TranslatorHelper.oscheck()} ${System.getProperty("os.version")}"
            val OS_WITH_CPU = "$OS (${GlDebugInfo.getCpuInfo()})"
        }
    }
}