package com.example.ahmedasl.model

enum class AppLanguage(
    val code: String,
    val displayName: String,
    val flag: String,
    val isRtl: Boolean
) {
    ARABIC("ar", "العربية", "🇸🇦", true),
    ENGLISH("en", "English", "🇺🇸", false),
    FRENCH("fr", "Français", "🇫🇷", false),
    INDONESIAN("id", "Bahasa Indonesia", "🇮🇩", false);

    companion object {
        fun fromCode(code: String): AppLanguage {
            return entries.firstOrNull { it.code.startsWith(code) } ?: ARABIC
        }
    }
}
