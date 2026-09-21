package com.example.ahmedasl.model

import androidx.annotation.DrawableRes

data class SignGesture(
    val id: String,
    val token: String,
    val nameEn: String,
    val nameAr: String,
    val nameId: String,
    val category: SignCategory,
    val descriptionEn: String,
    val descriptionAr: String,
    val handShape: String,
    val movement: String,
    @DrawableRes val imageRes: Int? = null,
    val videoSimulatedUrl: String? = null
)

enum class SignCategory(
    val labelEn: String,
    val labelAr: String,
    val icon: String
) {
    GREETINGS("Greetings", "التحيات", "👋"),
    EMERGENCY("Emergency & Help", "الطوارئ والمساعدة", "🚨"),
    CONVERSATION("Conversation", "محادثة عامة", "💬"),
    EMOTIONS("Emotions", "المشاعر والتقدير", "❤️"),
    QUESTIONS("Questions", "الأسئلة", "❓"),
    DAILY_LIFE("Daily Life", "الحياة اليومية", "🏠")
}
