package com.example.ahmedasl.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ahmedasl.model.AppLanguage
import com.example.ahmedasl.model.CameraSignDetection
import com.example.ahmedasl.ui.components.CameraPreviewView
import com.example.ahmedasl.ui.theme.BorderColor
import com.example.ahmedasl.ui.theme.PrimaryBlue
import com.example.ahmedasl.ui.theme.SkyAccent
import com.example.ahmedasl.ui.theme.SuccessGreen
import com.example.ahmedasl.ui.theme.WarningAmber

data class ConversationMessage(
    val id: String,
    val sender: String,
    val isDeafUser: Boolean,
    val text: String,
    val time: String,
    val token: String? = null
)

@Composable
fun CameraSignScreen(
    currentLanguage: AppLanguage,
    onSpeakText: (String) -> Unit
) {
    var isCameraRunning by remember { mutableStateOf(false) }
    var isConversationMode by remember { mutableStateOf(false) }

    val liveTranscript = remember {
        mutableStateListOf(
            ConversationMessage(
                id = "1",
                sender = "Sign AI Engine",
                isDeafUser = true,
                text = "مرحبًا بك في منصة التواصل بلغة الإشارة",
                time = "Now",
                token = "hello"
            )
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Notice
        item {
            Surface(
                color = WarningAmber.copy(alpha = 0.12f),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, WarningAmber.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = WarningAmber,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.ARABIC)
                            "ترجمة ASL بالكاميرا: وجّه يديك نحو الكاميرا ليقوم النموذج العصبي بالتعرف على الإشارات وتحويلها إلى كلمات فورية."
                        else
                            "ASL Camera Detection: Point your hands toward the camera. The neural model recognizes signs and transcribes them in real time.",
                        color = Color(0xFF78350F),
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Camera Preview Card
        item {
            CameraPreviewView(
                isCameraRunning = isCameraRunning,
                onToggleCamera = { isCameraRunning = !isCameraRunning },
                currentLanguage = currentLanguage,
                onSignRecognized = { detection ->
                    val signText = if (currentLanguage == AppLanguage.ARABIC)
                        detection.gestureNameAr
                    else
                        detection.gestureNameEn

                    if (liveTranscript.none { it.token == detection.detectedToken && it.text == signText }) {
                        liveTranscript.add(
                            0,
                            ConversationMessage(
                                id = System.currentTimeMillis().toString(),
                                sender = "Deaf User (Camera)",
                                isDeafUser = true,
                                text = signText,
                                time = "Live",
                                token = detection.detectedToken
                            )
                        )
                    }
                }
            )
        }

        // Conversation Action Bar
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { isConversationMode = !isConversationMode },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isConversationMode) SuccessGreen else PrimaryBlue
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("start_conversation_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isConversationMode) {
                            if (currentLanguage == AppLanguage.ARABIC) "محادثة مباشرة نشطة" else "Live Session Active"
                        } else {
                            if (currentLanguage == AppLanguage.ARABIC) "بدء محادثة مباشرة" else "Start Live Session"
                        }
                    )
                }

                if (liveTranscript.isNotEmpty()) {
                    IconButton(
                        onClick = { liveTranscript.clear() },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CleaningServices,
                            contentDescription = "Clear Feed",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // Live Recognition Feed Header
        item {
            Text(
                text = if (currentLanguage == AppLanguage.ARABIC) "سجل التعرف المباشر على الإشارات:" else "Live Gesture Recognition Feed:",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Transcript Items
        if (liveTranscript.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (currentLanguage == AppLanguage.ARABIC)
                                "في انتظار إشارات من الكاميرا..."
                            else
                                "Waiting for gestures from camera...",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        } else {
            items(liveTranscript) { msg ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = PrimaryBlue.copy(alpha = 0.12f),
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(text = "🤟", fontSize = 18.sp)
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = msg.sender,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 12.sp,
                                        color = PrimaryBlue
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = msg.time,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = msg.text,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        IconButton(
                            onClick = { onSpeakText(msg.text) },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Speak Text",
                                tint = PrimaryBlue,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
