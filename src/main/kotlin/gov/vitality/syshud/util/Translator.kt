package gov.vitality.syshud.util

internal interface TranslatorHelper {
    companion object {
        private val os_name: String = System.getProperty("os.name")
        private val os_version: String = System.getProperty("os.version")
        private val sierra: String = "10.12" //macOS 10.11.6 would still be Mac OS X 10.11.6

        fun oscheck(): String {
            return if (os_name.contains("Mac OS X") && sierra <= os_version) {
                "macOS"
            } else {
                os_name
            }
        }

    }
}