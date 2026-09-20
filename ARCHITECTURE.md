# Architecture Overview

This project uses a mobile-first pipeline where spoken Indonesian is converted into sign-language video output using speech recognition, AI-based interpretation, and cloud-managed gesture assets.

```mermaid
flowchart LR
    U[User] --> A[Flutter Mobile App]
    A --> S[Speech-to-Text]
    S --> T[Recognized Text]
    T --> LLM[Fireworks AI LLM]

    LLM -->|Context understanding| C[Semantic Interpretation]
    C -->|English translation| EN[English Text]
    C -->|Gesture token extraction| GT[Gesture Tokens]

    GT --> GM[Gesture Mapping Service]
    GM --> DB[(Supabase Storage)]
    DB --> GV[Gesture Video URLs]
    GV --> VP[Flutter Video Player]
    VP --> AN[Sequential Sign Language Playback]

    AN --> UI[Mobile UI / Sign Animation]
    UI --> U

    subgraph Client
        A
        S
        VP
        UI
    end

    subgraph AI
        LLM
        C
        EN
        GT
        GM
    end

    subgraph Cloud
        DB
        GV
    end
```

## Flow Summary

1. The user speaks into the Flutter app.
2. Speech is transcribed by the speech recognition layer.
3. The Fireworks AI model interprets the sentence, translates it, and extracts gesture tokens.
4. Each token is matched to a gesture video stored in Supabase.
5. The app plays the corresponding sign-language videos sequentially to present the message visually.
