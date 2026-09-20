<!DOCTYPE html>
<html lang="ar" dir="rtl">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Ahmed-Deaf-11 - ASL</title>

  <style>
    :root {
      --primary: #2563eb;
      --secondary: #0f172a;
      --success: #16a34a;
      --danger: #dc2626;
      --light: #f8fafc;
      --border: #e2e8f0;
    }

    * {
      box-sizing: border-box;
      font-family: Arial, Tahoma, sans-serif;
    }

    body {
      margin: 0;
      min-height: 100vh;
      color: var(--secondary);
      background: linear-gradient(135deg, #dbeafe, #f8fafc);
    }

    header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      gap: 20px;
      padding: 18px 6%;
      color: white;
      background: var(--secondary);
    }

    header h1 {
      margin: 0;
      font-size: 1.35rem;
    }

    header span {
      color: #93c5fd;
    }

    .language-select {
      padding: 8px 12px;
      border: 0;
      border-radius: 8px;
      cursor: pointer;
    }

    .container {
      width: min(1150px, 92%);
      margin: 30px auto;
    }

    .hero {
      margin-bottom: 24px;
      text-align: center;
    }

    .hero h2 {
      margin-bottom: 8px;
      color: var(--secondary);
    }

    .hero p {
      color: #475569;
    }

    .layout {
      display: grid;
      grid-template-columns: 1.2fr 0.8fr;
      gap: 22px;
    }

    .card {
      padding: 22px;
      border: 1px solid var(--border);
      border-radius: 18px;
      background: rgba(255, 255, 255, 0.95);
      box-shadow: 0 12px 30px rgba(15, 23, 42, 0.08);
    }

    .card h3 {
      margin-top: 0;
    }

    .camera-box {
      position: relative;
      overflow: hidden;
      min-height: 330px;
      border-radius: 14px;
      background: #020617;
    }

    #camera {
      display: block;
      width: 100%;
      min-height: 330px;
      object-fit: cover;
      transform: scaleX(-1);
    }

    .camera-status {
      position: absolute;
      right: 14px;
      bottom: 14px;
      padding: 8px 12px;
      border-radius: 20px;
      color: white;
      background: rgba(15, 23, 42, 0.75);
    }

    .controls,
    .social-buttons,
    .chat-actions {
      display: flex;
      flex-wrap: wrap;
      gap: 10px;
      margin-top: 16px;
    }

    button,
    .social-button {
      display: inline-block;
      padding: 11px 15px;
      border: 0;
      border-radius: 9px;
      color: white;
      text-decoration: none;
      cursor: pointer;
      font-size: 0.95rem;
    }

    button:hover,
    .social-button:hover {
      opacity: 0.88;
    }

    .primary {
      background: var(--primary);
    }

    .success {
      background: var(--success);
    }

    .danger {
      background: var(--danger);
    }

    .google {
      background: #ea4335;
    }

    .facebook {
      background: #1877f2;
    }

    .instagram {
      background: #c13584;
    }

    .messenger {
      background: #0084ff;
    }

    .whatsapp {
      background: #25d366;
    }

    .result-box {
      min-height: 115px;
      margin-top: 12px;
      padding: 15px;
      border: 1px solid var(--border);
      border-radius: 10px;
      white-space: pre-wrap;
      color: #334155;
      background: #f8fafc;
    }

    textarea {
      width: 100%;
      min-height: 100px;
      resize: vertical;
      padding: 13px;
      border: 1px solid var(--border);
      border-radius: 10px;
      font-size: 1rem;
      outline: none;
    }

    textarea:focus {
      border-color: var(--primary);
    }

    .feature {
      display: flex;
      align-items: flex-start;
      gap: 12px;
      margin: 15px 0;
    }

    .feature-icon {
      font-size: 1.5rem;
    }

    .feature strong {
      display: block;
      margin-bottom: 4px;
    }

    .notice {
      margin-top: 15px;
      padding: 12px;
      border-right: 4px solid #f59e0b;
      border-radius: 8px;
      color: #92400e;
      background: #fef3c7;
      line-height: 1.7;
    }

    footer {
      padding: 25px;
      text-align: center;
      color: #64748b;
    }

    @media (max-width: 800px) {
      header {
        align-items: flex-start;
        flex-direction: column;
      }

      .layout {
        grid-template-columns: 1fr;
      }

      .camera-box,
      #camera {
        min-height: 250px;
      }
    }
  </style>
</head>

<body>
  <header>
    <h1>Ahmed-Deaf-11 <span>ASL</span></h1>

    <select class="language-select" id="languageSelect">
      <option value="ar-SA">العربية</option>
      <option value="en-US">English</option>
      <option value="fr-FR">Français</option>
    </select>
  </header>

  <main class="container">
    <section class="hero">
      <h2>منصة التواصل للصم وضعاف السمع</h2>
      <p>
        محادثة مباشرة، فيديو من الكاميرا، تحويل الكلام إلى نص،
        وترجمة لغة الإشارة باستخدام الذكاء الاصطناعي.
      </p>
    </section>

    <section class="layout">
      <div class="card">
        <h3>📹 فيديو الكاميرا ولغة الإشارة</h3>

        <div class="camera-box">
          <video id="camera" autoplay muted playsinline></video>
          <div class="camera-status" id="cameraStatus">
            الكاميرا متوقفة
          </div>
        </div>

        <div class="controls">
          <button class="primary" id="startCamera">
            تشغيل الكاميرا
          </button>

          <button class="danger" id="stopCamera">
            إيقاف الكاميرا
          </button>

          <button class="success" id="startConversation">
            ابدأ محادثة مباشرة
          </button>
        </div>

        <div class="notice">
          ترجمة ASL المعروضة حاليًا هي واجهة تجريبية.
          لقراءة إشارات اليد وترجمتها فعليًا، يجب إضافة نموذج AI للتعرف على الحركة.
        </div>
      </div>

      <div class="card">
        <h3>🧠 خدمات الذكاء الاصطناعي</h3>

        <div class="feature">
          <div class="feature-icon">🤟</div>
          <div>
            <strong>ترجمة لغة الإشارة الأمريكية ASL</strong>
            <span id="signResult">في انتظار إشارة من الكاميرا...</span>
          </div>
        </div>

        <div class="feature">
          <div class="feature-icon">🎙️</div>
          <div>
            <strong>تحويل الكلام إلى نص</strong>
            <span>اضغط على الزر وتحدث، وسيظهر النص هنا.</span>
          </div>
        </div>

        <button class="primary" id="speechButton">
          🎙️ بدء تحويل الكلام إلى نص
        </button>

        <div class="result-box" id="speechResult">
          لم يتم تسجيل أي كلام بعد.
        </div>

        <div class="feature">
          <div class="feature-icon">🔊</div>
          <div>
            <strong>تحويل النص إلى صوت</strong>
            <span>اكتب رسالة واضغط على تشغيل الصوت.</span>
          </div>
        </div>

        <textarea id="messageInput" placeholder="اكتب رسالة هنا..."></textarea>

        <div class="chat-actions">
          <button class="success" id="speakButton">
            🔊 تشغيل الصوت
          </button>

          <button class="primary" id="sendMessage">
            إرسال الرسالة
          </button>
        </div>
      </div>

      <div class="card">
        <h3>🔐 تسجيل الدخول</h3>
        <p>اختر طريقة تسجيل الدخول المناسبة:</p>

        <div class="social-buttons">
          <a class="social-button google" href="#" onclick="login('Google')">
            Google
          </a>

          <a class="social-button facebook" href="#" onclick="login('Facebook')">
            Facebook
          </a>
        </div>

        <div class="notice">
          أزرار تسجيل الدخول تحتاج إلى ربطها بخادم OAuth حقيقي.
          لا تضع كلمات المرور داخل ملف HTML.
        </div>
      </div>

      <div class="card">
        <h3>💬 مراسلة مباشرة</h3>
        <p>تواصل مع الآخرين عبر التطبيقات الاجتماعية:</p>

        <div class="social-buttons">
          <a
            class="social-button whatsapp"
            href="https://wa.me/"
            target="_blank"
            rel="noopener"
          >
            WhatsApp
          </a>

          <a
            class="social-button messenger"
            href="https://www.messenger.com/"
            target="_blank"
            rel="noopener"
          >
            Messenger
          </a>

          <a
            class="social-button facebook"
            href="https://www.facebook.com/messages/"
            target="_blank"
            rel="noopener"
          >
            Facebook
          </a>

          <a
            class="social-button instagram"
            href="https://www.instagram.com/direct/inbox/"
            target="_blank"
            rel="noopener"
          >
            Instagram
          </a>
        </div>
      </div>
    </section>
  </main>

  <footer>
    Ahmed-Deaf-11 - ASL © 2026
  </footer>

  <script>
    const camera = document.getElementById("camera");
    const cameraStatus = document.getElementById("cameraStatus");
    const startCameraButton = document.getElementById("startCamera");
    const stopCameraButton = document.getElementById("stopCamera");
    const speechButton = document.getElementById("speechButton");
    const speechResult = document.getElementById("speechResult");
    const languageSelect = document.getElementById("languageSelect");

    let cameraStream = null;
    let recognition = null;

    async function startCamera() {
      try {
        cameraStream = await navigator.mediaDevices.getUserMedia({
          video: true,
          audio: false
        });

        camera.srcObject = cameraStream;
        cameraStatus.textContent = "الكاميرا تعمل الآن";
        cameraStatus.style.background = "#16a34a";
      } catch (error) {
        cameraStatus.textContent = "تعذر تشغيل الكاميرا";
        cameraStatus.style.background = "#dc2626";
        alert("يرجى السماح بالوصول إلى الكاميرا.");
      }
    }

    function stopCamera() {
      if (cameraStream) {
        cameraStream.getTracks().forEach(track => track.stop());
        camera.srcObject = null;
        cameraStream = null;
      }

      cameraStatus.textContent = "الكاميرا متوقفة";
      cameraStatus.style.background = "rgba(15, 23, 42, 0.75)";
    }

    function startSpeechRecognition() {
      const SpeechRecognition =
        window.SpeechRecognition || window.webkitSpeechRecognition;

      if (!SpeechRecognition) {
        alert("المتصفح لا يدعم تحويل الكلام إلى نص.");
        return;
      }

      recognition = new SpeechRecognition();
      recognition.lang = languageSelect.value;
      recognition.continuous = false;
      recognition.interimResults = true;

      speechButton.textContent = "🎙️ جاري الاستماع...";
      speechResult.textContent = "تحدث الآن...";

      recognition.onresult = event => {
        let text = "";

        for (let i = event.resultIndex; i < event.results.length; i++) {
          text += event.results[i][0].transcript;
        }

        speechResult.textContent = text;
        document.getElementById("messageInput").value = text;
      };

      recognition.onend = () => {
        speechButton.textContent = "🎙️ بدء تحويل الكلام إلى نص";
      };

      recognition.onerror = () => {
        speechButton.textContent = "🎙️ بدء تحويل الكلام إلى نص";
        speechResult.textContent = "حدث خطأ أثناء تسجيل الصوت.";
      };

      recognition.start();
    }

    function speakText() {
      const text = document.getElementById("messageInput").value.trim();

      if (!text) {
        alert("اكتب رسالة أولًا.");
        return;
      }

      const speech = new SpeechSynthesisUtterance(text);
      speech.lang = languageSelect.value;
      window.speechSynthesis.speak(speech);
    }

    function sendMessage() {
      const text = document.getElementById("messageInput").value.trim();

      if (!text) {
        alert("اكتب رسالة قبل الإرسال.");
        return;
      }

      alert("تم تجهيز الرسالة للإرسال:\n\n" + text);
    }

    function login(provider) {
      alert(
        "سيتم تسجيل الدخول باستخدام " +
        provider +
        ".\nيجب ربط هذا الزر بخدمة OAuth في الخادم."
      );
    }

    startCameraButton.addEventListener("click", startCamera);
    stopCameraButton.addEventListener("click", stopCamera);
    speechButton.addEventListener("click", startSpeechRecognition);
    document.getElementById("speakButton").addEventListener("click", speakText);
    document.getElementById("sendMessage").addEventListener("click", sendMessage);

    document.getElementById("startConversation").addEventListener("click", () => {
      alert("تم بدء المحادثة المباشرة. يمكنك تشغيل الكاميرا والتحدث الآن.");
    });

    window.addEventListener("beforeunload", stopCamera);
  </script>
</body>
</html>
🤟 Ahmed-Deaf-11-ASL

> AI-Powered Real-Time Sign Language Communication Assistant

Ahmed-Deaf-11-ASL is an AI-powered mobile application that bridges communication between hearing individuals and the Deaf community by translating spoken Indonesian into sign language gestures in real time.

Instead of displaying only text, Ahmed-Deaf-11-ASL understands the context of spoken language using a Large Language Model (LLM), extracts meaningful gesture tokens, and plays corresponding sign language videos sequentially.



✨ Features

- 🎤 Real-time Speech Recognition
- 🧠 AI Context Understanding
- 🌎 Indonesian → English Translation
- 🤟 Automatic Sign Language Gesture Extraction
- 🎬 Sequential Sign Language Video Playback
- ☁️ Cloud-based Gesture Database (Supabase)
- 📱 Cross-platform Mobile Application (Flutter)



🚀 System Architecture

```
User Speech
      │
      ▼
Speech-to-Text
      │
      ▼
Recognized Sentence
      │
      ▼
Fireworks AI (LLM)
      │
      ├── English Translation
      └── Gesture Extraction
              │
              ▼
Gesture Mapping
              │
              ▼
Supabase Storage
              │
              ▼
Gesture Video URLs
              │
              ▼
Flutter Video Player
              │
              ▼
Sign Language Animation
```


🧠 AI Pipeline

Our AI pipeline consists of four main stages.

1. Speech Recognition

The application captures the user's speech using Flutter Speech-to-Text.

Example


```
Halo apa kabar
```

↓

Recognized Text

```
Halo apa kabar
```

---

2. Context Understanding

Instead of performing simple keyword matching, our Large Language Model first understands the semantic meaning of the sentence.

The AI simultaneously performs:

- Context Understanding
- English Translation
- Gesture Token Extraction

Example

```
{
  "translation": "Hello, how are you?",
  "gesture": [
    "halo",
    "apa",
    "kabar"
  ]
}
```

---

3. Gesture Mapping

Each extracted gesture is mapped into our cloud-based gesture database stored in Supabase Storage.

Example

```
halo
apa
kabar
```

↓

```
halo.mp4
apa.mp4
kabar.mp4
```

↓

Public URLs

---

4. Sequential Gesture Playback

Gesture videos are played automatically in sequence.

```
halo.mp4

↓

apa.mp4

↓

kabar.mp4
```

This creates a smooth sign language animation for the user.

---

🛠 Tech Stack

| Technology | Purpose |
|------------|---------|
| Flutter | Mobile Application |
| Fireworks AI | Large Language Model |
| Speech-to-Text | Voice Recognition |
| Supabase | Cloud Storage |
| Video Player | Gesture Playback |
| Dart | Application Logic |

---

📂 Project Structure

```
mobile/

lib/

├── screens/

├── widgets/

├── services/

│ ├── speech_service.dart

│ ├── fireworks_service.dart

│ ├── sign_service.dart

│ └── ai_pipeline.dart

├── models/

└── main.dart
```

---

⚙️ Core Components

SpeechService

Captures speech and converts it into text.

---

FireworksService

Communicates with the Fireworks AI API to:

- Understand sentence context
- Translate Indonesian into English
- Extract gesture tokens

---

SignService

Maps gesture keywords into gesture video URLs stored in Supabase.



AIPipeline

Coordinates the entire AI workflow.

```
Speech

↓

AI

↓

Gesture Extraction

↓

Supabase

↓

Video

↓

UI
```



SignVideoPlayer

Automatically plays multiple gesture videos in sequence.



🎯 Why AI?

Traditional systems perform simple keyword matching.

Ahmed-Deaf-11-ASL uses a Large Language Model to understand the meaning of a sentence before selecting the most appropriate sign language gestures.

This enables more accurate gesture selection while preserving the context of natural conversation.



🔮 Future Work

- Real-time streaming conversation
- AI-generated sign language avatar
- Larger BISINDO gesture database
- Offline gesture caching
- Support for multiple sign languages



👥 Team

Team Ahmed-Deaf-11-ASL

Built for the AMD Developer Challenge.



# 📄 License

MIT License
