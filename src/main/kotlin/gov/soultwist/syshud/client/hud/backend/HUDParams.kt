package gov.soultwist.syshud.client.hud.backend

import gov.soultwist.syshud.util.ModConfig
import gov.soultwist.syshud.util.TranslatorHelper
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

interface HUDParams {
    interface getJavaSpecs {
        companion object {
            val VENDOR: String = System.getProperty("java.vendor")
            val VERSION: String = "Java " + System.getProperty("java.version")
            val VERSION_ARCH: String = "Java " + System.getProperty("java.version") + " (" + System.getProperty("os.arch") + ")"
        }
    }

    interface getGameClientSpecs {
        companion object {  }
    }

    interface getSystemSpecs {
        companion object {
            val OS = TranslatorHelper.oscheck() + " " + System.getProperty("os.version")
        }
    }

    interface getRealTime {
        companion object {
            val DUAL: String = SimpleDateFormat(ModConfig.DATE_AND_TIME_FORMATTING.value()).format(Date.from(Instant.now())).toString()
            val DATE: String = SimpleDateFormat(ModConfig.DATE_FORMATTING.value()).format(Date.from(Instant.now())).toString()
            val TIME: String = SimpleDateFormat(ModConfig.TIME_FORMATTING.value()).format(Date.from(Instant.now())).toString()
        }
    }
}