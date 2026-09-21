package com.example.ahmedasl.data

import com.example.ahmedasl.R
import com.example.ahmedasl.model.SignCategory
import com.example.ahmedasl.model.SignGesture

object SignGestureRepository {

    val gestures: List<SignGesture> = listOf(
        SignGesture(
            id = "hello",
            token = "hello",
            nameEn = "Hello / Welcome",
            nameAr = "مرحبًا / أهلاً",
            nameId = "Halo",
            category = SignCategory.GREETINGS,
            descriptionEn = "Touch forehead with open hand and move forward in a friendly salute motion.",
            descriptionAr = "المس الجبهة باليد المفتوحة ثم حرّكها للأمام بحركة تحية ودية.",
            handShape = "Open 5-hand, palm facing outward",
            movement = "Forehead to outward wave",
            imageRes = R.drawable.img_4617
        ),
        SignGesture(
            id = "how_are_you",
            token = "how_are_you",
            nameEn = "How are you?",
            nameAr = "كيف حالك؟",
            nameId = "Apa kabar",
            category = SignCategory.QUESTIONS,
            descriptionEn = "Place both cupped hands over chest, rotate upwards, then point forward.",
            descriptionAr = "ضع كلتا اليدين المقعرتين فوق الصدر ودوّرهما لأعلى ثم أشر للأمام.",
            handShape = "Curved hands rotating outwards, followed by index pointer",
            movement = "Chest outward roll then point",
            imageRes = R.drawable.img_4635
        ),
        SignGesture(
            id = "thank_you",
            token = "thank_you",
            nameEn = "Thank you",
            nameAr = "شكرًا لك",
            nameId = "Terima kasih",
            category = SignCategory.EMOTIONS,
            descriptionEn = "Touch fingers to lips or chin and extend hand outwards toward the person.",
            descriptionAr = "المس الشفاه أو الذقن بأطراف الأصابع ثم مد اليد للأمام باتجاه الشخص.",
            handShape = "Flat open hand, palm facing body initially",
            movement = "Chin outward toward listener",
            imageRes = R.drawable.img_4644
        ),
        SignGesture(
            id = "i_love_you",
            token = "love",
            nameEn = "I Love You (ILY)",
            nameAr = "أحبك / مودة",
            nameId = "Saya cinta kamu",
            category = SignCategory.EMOTIONS,
            descriptionEn = "Classic ASL ILY sign combining letters I, L, and Y.",
            descriptionAr = "إشارة الحب الشهيرة في لغة الإشارة تجمع الحروف I و L و Y.",
            handShape = "Thumb, index, and pinky extended, middle and ring finger curled",
            movement = "Hold upward with slight outward pulse",
            imageRes = R.drawable.img_4645
        ),
        SignGesture(
            id = "help",
            token = "help",
            nameEn = "Help / Assistance",
            nameAr = "مساعدة / نجدة",
            nameId = "Tolong / Bantuan",
            category = SignCategory.EMOTIONS,
            descriptionEn = "Place closed fist with thumb up on top of flat palm, then elevate both together.",
            descriptionAr = "ضع قبضة مغلقة مع الإبهام لأعلى فوق راحة يدك المسطحة ثم ارفعهما معًا للأعلى.",
            handShape = "Dominant fist thumb-up resting on non-dominant flat palm",
            movement = "Upward lifting motion",
            imageRes = R.drawable.img_4653
        ),
        SignGesture(
            id = "yes",
            token = "yes",
            nameEn = "Yes / Agree",
            nameAr = "نعم / موافق",
            nameId = "Ya / Setuju",
            category = SignCategory.CONVERSATION,
            descriptionEn = "Make an 'S' fist and nod it up and down like a head nod.",
            descriptionAr = "اصنع قبضة مثل حرف S وحرّكها لأعلى ولأسفل كإيماءة رأس.",
            handShape = "Closed fist (S-hand)",
            movement = "Vertical wrist nodding motion twice",
            imageRes = R.drawable.img_4681
        ),
        SignGesture(
            id = "no",
            token = "no",
            nameEn = "No / Disagree",
            nameAr = "لا / رفض",
            nameId = "Tidak",
            category = SignCategory.CONVERSATION,
            descriptionEn = "Snap index and middle finger down onto the thumb.",
            descriptionAr = "أطبق السبابة والوسطى بقوة على الإبهام بحركة سريعة وحاسمة.",
            handShape = "Index and middle extended, then meeting thumb",
            movement = "Quick snapping closure",
            imageRes = R.drawable.img_4685
        ),
        SignGesture(
            id = "please",
            token = "please",
            nameEn = "Please",
            nameAr = "من فضلك / رجاءً",
            nameId = "Tolong / Mohon",
            category = SignCategory.CONVERSATION,
            descriptionEn = "Rub flat open hand in clockwise circular motion on chest.",
            descriptionAr = "افرك اليد المفتوحة بحركة دائرية في اتجاه عقارب الساعة على الصدر.",
            handShape = "Open flat palm facing chest",
            movement = "Gentle circular rub on chest",
            imageRes = R.drawable.img_4686
        ),
        SignGesture(
            id = "deaf",
            token = "deaf",
            nameEn = "Deaf / Hard of Hearing",
            nameAr = "أصم / ضعيف السمع",
            nameId = "Tuli / Tunarungu",
            category = SignCategory.EMERGENCY,
            descriptionEn = "Point index finger near ear, then touch near corner of mouth.",
            descriptionAr = "أشر بالسبابة بالقرب من الأذن ثم المس بالقرب من زاوية الفم.",
            handShape = "Index finger pointing",
            movement = "Ear to mouth touch",
            imageRes = R.drawable.img_4617
        ),
        SignGesture(
            id = "doctor_hospital",
            token = "hospital",
            nameEn = "Doctor / Hospital",
            nameAr = "طبيب / مستشفى",
            nameId = "Dokter / Rumah Sakit",
            category = SignCategory.EMERGENCY,
            descriptionEn = "Touch M-shape or fingers to wrist pulse, indicating medical care.",
            descriptionAr = "المس نبض المعصم بأطراف الأصابع مشيرًا إلى الرعاية الطبية.",
            handShape = "Tapped fingers on wrist",
            movement = "Pulse check motion",
            imageRes = R.drawable.img_4653
        ),
        SignGesture(
            id = "family",
            token = "family",
            nameEn = "Family",
            nameAr = "عائلة / أسرة",
            nameId = "Keluarga",
            category = SignCategory.DAILY_LIFE,
            descriptionEn = "Both hands in 'F' shapes start touching and circle around to meet again.",
            descriptionAr = "كلتا اليدين بشكل الحرف F تبدآن متلامستين وتدوران لتلتقيا مجددًا في دائرة.",
            handShape = "Both hands F-handshape",
            movement = "Circular inward loop",
            imageRes = R.drawable.img_4644
        ),
        SignGesture(
            id = "peace",
            token = "peace",
            nameEn = "Peace / Calm",
            nameAr = "سلام / هدوء",
            nameId = "Damai",
            category = SignCategory.EMOTIONS,
            descriptionEn = "Clasp hands gently, twist them, then smooth outwards palms downward.",
            descriptionAr = "شبّك اليدين برفق ثم افردهما للخارج مع توجيه الراحتين لأسفل.",
            handShape = "Open smooth palms descending outwards",
            movement = "Clasp, twist, and smooth outwards",
            imageRes = R.drawable.img_4681
        )
    )

    fun findGestureByToken(token: String): SignGesture? {
        val cleanToken = token.trim().lowercase()
        return gestures.firstOrNull { g ->
            g.token.equals(cleanToken, ignoreCase = true) ||
            g.id.equals(cleanToken, ignoreCase = true) ||
            g.nameEn.contains(cleanToken, ignoreCase = true) ||
            g.nameAr.contains(cleanToken, ignoreCase = true) ||
            g.nameId.contains(cleanToken, ignoreCase = true)
        }
    }

    fun searchGestures(query: String, category: SignCategory? = null): List<SignGesture> {
        return gestures.filter { gesture ->
            val matchesCategory = category == null || gesture.category == category
            val matchesQuery = query.isBlank() ||
                    gesture.nameEn.contains(query, ignoreCase = true) ||
                    gesture.nameAr.contains(query, ignoreCase = true) ||
                    gesture.nameId.contains(query, ignoreCase = true) ||
                    gesture.descriptionEn.contains(query, ignoreCase = true) ||
                    gesture.descriptionAr.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }
}
