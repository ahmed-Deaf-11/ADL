package com.example.ahmedasl.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.ahmedasl.model.AiTranslationPipelineResult
import com.example.ahmedasl.model.AppLanguage
import com.example.ahmedasl.service.AiSignPipeline
import com.example.ahmedasl.service.SpeechRecognitionHelper
import com.example.ahmedasl.ui.components.SequentialGesturePlayer
import com.example.ahmedasl.ui.theme.DangerRed
import com.example.ahmedasl.ui.theme.DeepNavy
import com.example.ahmedasl.ui.theme.PrimaryBlue
import com.example.ahmedasl.ui.theme.SkyAccent
import com.example.ahmedasl.ui.theme.SuccessGreen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AiAssistantScreen(
    currentLanguage: AppLanguage,
    speechHelper: SpeechRecognitionHelper,
    onSpeakText: (String) -> Unit
) {
    val context = LocalContext.current
    val isListening by speechHelper.isListening.collectAsState()
    val speechText by speechHelper.recognizedText.collectAsState()
    val speechError by speechHelper.errorMessage.collectAsState()

    var inputText by remember { mutableStateOf("Halo apa kabar") }
    var pipelineResult by remember {
        mutableStateOf(
            AiSignPipeline.processSentence("Halo apa kabar", currentLanguage)
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            speechHelper.startListening(currentLanguage)
        }
    }

    // Update input text when speech recognition yields a result
    LaunchedEffect(speechText) {
        if (speechText.isNotBlank()) {
            inputText = speechText
            pipelineResult = AiSignPipeline.processSentence(speechText, currentLanguage)
        }
    }

    val quickPhrases = listOf(
        "Halo apa kabar",
        "Tolong saya",
        "Saya tuli",
        "Terima kasih banyak",
        "Di mana rumah sakit",
        "Hello, how are you",
        "مرحبًا كيف حالك",
        "أحتاج مساعدة"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // AI Pipeline Overview Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = DeepNavy),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🧠", fontSize = 22.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = if (currentLanguage == AppLanguage.ARABIC)
                                        "خدمات الذكاء الاصطناعي ولغة الإشارة"
                                    else
                                        "AI Sign Language Pipeline",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "Speech → LLM Context → Gesture Tokens → Animation",
                                    color = SkyAccent,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Voice Input & Speech Recognition Section
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🎙️", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = if (currentLanguage == AppLanguage.ARABIC)
                                    "تحويل الكلام إلى نص (Speech-to-Text)"
                                else
                                    "Speech to Text Recognition",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Text(
                                text = if (currentLanguage == AppLanguage.ARABIC)
                                    "اضغط على الميكروفون وتحدث بأي لغة"
                                else
                                    "Tap microphone and speak naturally",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Big Mic Button & Listening state
                    Button(
                        onClick = {
                            if (isListening) {
                                speechHelper.stopListening()
                            } else {
                                val hasPermission = ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.RECORD_AUDIO
                                ) == PackageManager.PERMISSION_GRANTED

                                if (hasPermission) {
                                    speechHelper.startListening(currentLanguage)
                                } else {
                                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isListening) DangerRed else PrimaryBlue
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("speech_button")
                    ) {
                        Icon(
                            imageVector = if (isListening) Icons.Default.MicOff else Icons.Default.Mic,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isListening) {
                                if (currentLanguage == AppLanguage.ARABIC) "🎙️ جارٍ الاستماع... تحدث الآن" else "🎙️ Listening... Speak now"
                            } else {
                                if (currentLanguage == AppLanguage.ARABIC) "بدء تحويل الكلام إلى نص" else "Start Speech Recognition"
                            },
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (speechError != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = speechError ?: "",
                            color = DangerRed,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Quick Input Phrases
        item {
            Column {
                Text(
                    text = if (currentLanguage == AppLanguage.ARABIC) "عبارات شائعة وسريعة:" else "Quick Phrases:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    quickPhrases.forEach { phrase ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .clickable {
                                    inputText = phrase
                                    pipelineResult = AiSignPipeline.processSentence(phrase, currentLanguage)
                                }
                        ) {
                            Text(
                                text = phrase,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // Text Input & Message Box
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (currentLanguage == AppLanguage.ARABIC) "الرسالة المدخلة:" else "Input Message:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = inputText,
                        onValueChange = {
                            inputText = it
                            pipelineResult = AiSignPipeline.processSentence(it, currentLanguage)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .testTag("message_input"),
                        placeholder = {
                            Text(
                                if (currentLanguage == AppLanguage.ARABIC) "اكتب رسالة هنا..." else "Type a message here..."
                            )
                        },
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                val textToSpeak = if (currentLanguage == AppLanguage.ARABIC)
                                    pipelineResult.translatedTextAr
                                else
                                    pipelineResult.translatedTextEn
                                onSpeakText(textToSpeak)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("speak_button")
                        ) {
                            Icon(Icons.Default.VolumeUp, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (currentLanguage == AppLanguage.ARABIC) "تشغيل الصوت" else "Speak Out",
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Button(
                            onClick = {
                                pipelineResult = AiSignPipeline.processSentence(inputText, currentLanguage)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("send_button")
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (currentLanguage == AppLanguage.ARABIC) "استخراج الإشارات" else "Extract Signs",
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // AI Semantic Interpretation & Extracted Tokens Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Translate,
                            contentDescription = null,
                            tint = PrimaryBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (currentLanguage == AppLanguage.ARABIC)
                                "الترجمة وفهم السياق بالذكاء الاصطناعي:"
                            else
                                "Context Understanding & Translation:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "English Translation:",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = pipelineResult.translatedTextEn,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "الترجمة إلى العربية:",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = pipelineResult.translatedTextAr,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Semantic Context: ${pipelineResult.semanticContext}",
                                fontSize = 12.sp,
                                color = PrimaryBlue
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (currentLanguage == AppLanguage.ARABIC)
                            "رموز لغة الإشارة المستخرجة (Gesture Tokens):"
                        else
                            "Extracted Gesture Tokens:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        pipelineResult.gestureTokens.forEach { token ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = DeepNavy
                            ) {
                                Text(
                                    text = token.uppercase(),
                                    color = SkyAccent,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Sequential Gesture Player (Sequential Video / Image Playback)
        item {
            SequentialGesturePlayer(
                gestures = pipelineResult.matchedGestures,
                currentLanguage = currentLanguage,
                onSpeakText = onSpeakText
            )
        }
    }
}
