package com.example.ahmedasl.model

data class AiTranslationPipelineResult(
    val originalText: String,
    val sourceLanguage: AppLanguage,
    val translatedTextEn: String,
    val translatedTextAr: String,
    val semanticContext: String,
    val gestureTokens: List<String>,
    val matchedGestures: List<SignGesture>,
    val timestamp: Long = System.currentTimeMillis()
)

data class CameraSignDetection(
    val detectedToken: String,
    val gestureNameAr: String,
    val gestureNameEn: String,
    val confidence: Float,
    val boundingBoxTop: Float = 0.25f,
    val boundingBoxLeft: Float = 0.25f,
    val boundingBoxRight: Float = 0.75f,
    val boundingBoxBottom: Float = 0.75f,
    val isRecognized: Boolean = true
)
