package gov.soultwist.syshud.client.hud

import gov.soultwist.syshud.util.TranslatorHelper

interface HUDParams {
    interface javaVersion {
        companion object {
            val VENDOR: String = System.getProperty("java.vendor")
            val VERSION: String = "Java " + System.getProperty("java.version")
            val VERSION_ARCH: String = "Java " + System.getProperty("java.version") + " (" + System.getProperty("os.arch") + ")"
        }
    }



    interface clientData {
        companion object { val OS = TranslatorHelper.oscheck() + " " + System.getProperty("os.version") }
    }
}