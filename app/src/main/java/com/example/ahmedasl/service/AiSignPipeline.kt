package com.example.ahmedasl.service

import com.example.ahmedasl.data.SignGestureRepository
import com.example.ahmedasl.model.AiTranslationPipelineResult
import com.example.ahmedasl.model.AppLanguage
import com.example.ahmedasl.model.SignGesture

object AiSignPipeline {

    /**
     * Translates sentence context and extracts sequential gesture tokens
     * following the AI pipeline architecture in ARCHITECTURE.md.
     */
    fun processSentence(input: String, sourceLang: AppLanguage): AiTranslationPipelineResult {
        val trimmed = input.trim()
        val lower = trimmed.lowercase()

        // 1. Context interpretation and smart token extraction
        val extractedTokens = mutableListOf<String>()
        val matchedGestures = mutableListOf<SignGesture>()

        // Check compound phrases first
        if (containsAny(lower, "apa kabar", "how are you", "كيف حالك", "comment ca va", "comment vas-tu")) {
            extractedTokens.add("how_are_you")
        }
        if (containsAny(lower, "halo", "hello", "hi", "hey", "مرحبا", "أهلا", "اهلا", "bonjour", "salut")) {
            extractedTokens.add("hello")
        }
        if (containsAny(lower, "terima kasih", "thank you", "thanks", "شكرا", "شكرًا", "merci")) {
            extractedTokens.add("thank_you")
        }
        if (containsAny(lower, "cinta", "love", "i love you", "أحبك", "احبك", "je t'aime")) {
            extractedTokens.add("love")
        }
        if (containsAny(lower, "tolong", "bantuan", "help", "مساعدة", "نجدة", "aidez-moi", "au secours")) {
            extractedTokens.add("help")
        }
        if (containsAny(lower, "ya", "yes", "oke", "okay", "نعم", "أجل", "oui")) {
            extractedTokens.add("yes")
        }
        if (containsAny(lower, "tidak", "no", "bukan", "لا", "non")) {
            extractedTokens.add("no")
        }
        if (containsAny(lower, "mohon", "please", "من فضلك", "رجاء", "s'il vous plaît", "svp")) {
            extractedTokens.add("please")
        }
        if (containsAny(lower, "tuli", "deaf", "tunarungu", "أصم", "صم", "ضعيف السمع", "sourd")) {
            extractedTokens.add("deaf")
        }
        if (containsAny(lower, "dokter", "rumah sakit", "hospital", "doctor", "طبيب", "مستشفى", "دكتور", "docteur", "hôpital")) {
            extractedTokens.add("hospital")
        }
        if (containsAny(lower, "keluarga", "family", "عائلة", "أسرة", "famille")) {
            extractedTokens.add("family")
        }
        if (containsAny(lower, "damai", "peace", "سلام", "paix")) {
            extractedTokens.add("peace")
        }

        // If no compound tokens matched, match words individually
        if (extractedTokens.isEmpty()) {
            val words = lower.split(Regex("[\\s,?.!]+")).filter { it.isNotBlank() }
            for (word in words) {
                val gesture = SignGestureRepository.findGestureByToken(word)
                if (gesture != null && !extractedTokens.contains(gesture.token)) {
                    extractedTokens.add(gesture.token)
                }
            }
        }

        // Match tokens to repository gestures
        for (token in extractedTokens) {
            val gesture = SignGestureRepository.findGestureByToken(token)
            if (gesture != null) {
                matchedGestures.add(gesture)
            }
        }

        // Fallback default if empty sentence
        if (matchedGestures.isEmpty() && trimmed.isNotBlank()) {
            SignGestureRepository.gestures.firstOrNull()?.let { matchedGestures.add(it) }
        }

        // Translation generation
        val (transEn, transAr, context) = generateTranslations(trimmed, sourceLang, extractedTokens)

        return AiTranslationPipelineResult(
            originalText = trimmed,
            sourceLanguage = sourceLang,
            translatedTextEn = transEn,
            translatedTextAr = transAr,
            semanticContext = context,
            gestureTokens = extractedTokens,
            matchedGestures = matchedGestures
        )
    }

    private fun containsAny(text: String, vararg phrases: String): Boolean {
        return phrases.any { text.contains(it, ignoreCase = true) }
    }

    private fun generateTranslations(
        input: String,
        sourceLang: AppLanguage,
        tokens: List<String>
    ): Triple<String, String, String> {
        val lower = input.lowercase()

        // Common phrase mappings for high-accuracy translation
        return when {
            containsAny(lower, "halo apa kabar", "hello how are you", "مرحبا كيف حالك") -> {
                Triple(
                    "Hello! How are you doing today?",
                    "مرحبًا! كيف حالك اليوم؟",
                    "Greeting & Inquiring about well-being"
                )
            }
            containsAny(lower, "tolong saya", "bantu saya", "i need help", "help me", "أحتاج مساعدة", "ساعدني") -> {
                Triple(
                    "I need assistance / Please help me.",
                    "أنا بحاجة إلى مساعدة / أرجو مساعدتي.",
                    "Urgent Request / Emergency Support"
                )
            }
            containsAny(lower, "saya tuli", "saya tunarungu", "i am deaf", "i am hard of hearing", "أنا أصم", "أنا من ضعاف السمع") -> {
                Triple(
                    "I am Deaf or Hard of Hearing. Please communicate visually.",
                    "أنا أصم أو ضعيف السمع. يرجى التواصل كتابيًا أو بالإشارة.",
                    "Identity & Communication Accommodation"
                )
            }
            containsAny(lower, "terima kasih banyak", "thank you so much", "شكرا جزيلا", "شكراً جزيلاً") -> {
                Triple(
                    "Thank you very much!",
                    "شكرًا جزيلًا لك!",
                    "Gratitude & Appreciation"
                )
            }
            containsAny(lower, "di mana rumah sakit", "where is the hospital", "أين المستشفى") -> {
                Triple(
                    "Where is the nearest hospital or medical center?",
                    "أين يقع أقرب مستشفى أو مركز طبي؟",
                    "Medical Directions Inquiry"
                )
            }
            else -> {
                val en = if (sourceLang == AppLanguage.ENGLISH) input else "[$sourceLang -> EN] $input"
                val ar = if (sourceLang == AppLanguage.ARABIC) input else "[$sourceLang -> AR] $input"
                val ctx = if (tokens.isNotEmpty()) "Extracted ${tokens.size} sign gesture tokens" else "General conversation"
                Triple(en, ar, ctx)
            }
        }
    }
}
