package co.za.vusieam.mobile.absaweatherassessment.models.internal

enum class LoggingType(val logValue:String) {
    DEBUG("Debug"),
    SUCCESS("Success"),
    WARNING ("Warning"),
    ERROR ("Error"),
    EXCEPTION ("Exception");

    companion object {
        private val types = LoggingType.values().associateBy { it.logValue }
        fun findByValue(value: String) = types[value]
    }
}