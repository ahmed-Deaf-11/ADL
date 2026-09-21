# Ahmed-Deaf-11 ASL (ADL)

> AI-Powered Real-Time Sign Language Communication Assistant for the Deaf and Hard of Hearing  
> منصة التواصل للصم وضعاف السمع

Ahmed-Deaf-11 ASL is a native Android application built using Kotlin and Jetpack Compose. It bridges communication between hearing individuals and the Deaf community using real-time camera-based ASL sign recognition, speech-to-text, LLM context understanding, sequential sign language gesture playback, and instant assistance tools.

---

## ✨ Features

- 📹 **Live Camera & Real-Time ASL Detection**: Real-time CameraX preview with hand tracking overlay, neural gesture scanner, and live transcription feed.
- 🎙️ **Speech-to-Text Recognition**: Real-time voice capture and speech transcription in Arabic, English, French, and Indonesian.
- 🧠 **AI Context & Semantic Interpretation**: Understands sentence context and extracts sequential sign language gesture tokens (e.g., `"Halo apa kabar"` → `["hello", "how_are_you"]`).
- 🎬 **Sequential Gesture Animation Playback**: Automatically plays corresponding sign language gesture demonstrations sequentially with progress indicators, hand shape guidance, and movement paths.
- 🔊 **Text-to-Speech (TTS)**: Native speech synthesis for speaking typed phrases and sign translations aloud.
- 🤟 **ASL Gesture Dictionary**: Searchable library of gestures categorized by Greetings, Emergency, Conversation, Emotions, and Daily Life with visual illustrations.
- 💬 **Direct Connect & Emergency Assistance Cards**: Full-screen high-contrast visual display cards for deaf users to quickly communicate in public (e.g., *"I am Deaf - please write it down"*, *"Emergency: Call an ambulance"*), plus one-tap links to WhatsApp, Messenger, Facebook, and Instagram.
- 🌐 **Multilingual & RTL Support**: Seamless switching between Arabic (with full RTL layout), English, French, and Indonesian.

---

## 🛠 Tech Stack

- **Platform**: Android (minSdk 26, targetSdk 36)
- **Language**: Kotlin 2.2
- **UI Framework**: Jetpack Compose with Material Design 3 (M3)
- **Camera**: CameraX (Camera2, Lifecycle, PreviewView)
- **Speech**: Android SpeechRecognizer & TextToSpeech
- **Image Loading**: Coil Compose
- **Build System**: Gradle 9.3.1 (Kotlin DSL) with Android Gradle Plugin (AGP) 9.1.1
