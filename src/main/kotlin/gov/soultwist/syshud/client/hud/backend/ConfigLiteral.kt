package gov.soultwist.syshud.client.hud.backend

interface ConfigLiteral {
    interface Tooltips {
        companion object {
            const val date_format_warn: String = "Date format defaults to using the CAN/CSA-Z234.4-89 (R2007) / ISO 8601 Canadian Standard format. This is intended. Additionally, when customizing the date and time, use the Java Simple Date Format or your game will crash"
        }
    }
}