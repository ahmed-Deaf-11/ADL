package com.example.ahmedasl.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ahmedasl.model.AppLanguage
import com.example.ahmedasl.ui.theme.DangerRed
import com.example.ahmedasl.ui.theme.DeepNavy
import com.example.ahmedasl.ui.theme.PrimaryBlue
import com.example.ahmedasl.ui.theme.SkyAccent
import com.example.ahmedasl.ui.theme.SuccessGreen
import com.example.ahmedasl.ui.theme.WarningAmber

data class SocialChannel(
    val name: String,
    val iconEmoji: String,
    val brandColor: Color,
    val baseUrl: String
)

data class AssistanceCard(
    val id: String,
    val titleEn: String,
    val titleAr: String,
    val fullTextEn: String,
    val fullTextAr: String,
    val icon: ImageVector,
    val cardColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectConnectScreen(
    currentLanguage: AppLanguage,
    onSpeakText: (String) -> Unit
) {
    val context = LocalContext.current
    var customMessage by remember { mutableStateOf("") }
    var activeFullscreenCard by remember { mutableStateOf<AssistanceCard?>(null) }

    val socialChannels = listOf(
        SocialChannel("WhatsApp", "💬", Color(0xFF25D366), "https://wa.me/?text="),
        SocialChannel("Messenger", "⚡", Color(0xFF0084FF), "https://www.messenger.com/"),
        SocialChannel("Facebook", "📘", Color(0xFF1877F2), "https://www.facebook.com/messages/"),
        SocialChannel("Instagram", "📷", Color(0xFFE1306C), "https://www.instagram.com/direct/inbox/")
    )

    val assistanceCards = listOf(
        AssistanceCard(
            id = "deaf_identity",
            titleEn = "I am Deaf / Hard of Hearing",
            titleAr = "أنا شخص أصم / ضعيف السمع",
            fullTextEn = "I am Deaf / Hard of Hearing. Please speak facing me, write down what you want to say, or use this phone to communicate.",
            fullTextAr = "أنا أصم / ضعيف السمع. يرجى التحدث في مواجهتي، أو كتابة ما تريد قوله، أو استخدام هذا الهاتف للتواصل معي.",
            icon = Icons.Default.Hearing,
            cardColor = PrimaryBlue
        ),
        AssistanceCard(
            id = "emergency",
            titleEn = "Emergency: I Need Medical Help!",
            titleAr = "طوارئ: أحتاج مساعدة طبية عاجلة!",
            fullTextEn = "EMERGENCY! I need urgent medical assistance. Please call an ambulance (911/123) for me right now!",
            fullTextAr = "حالة طوارئ! أحتاج إلى مساعدة طبية عاجلة. يرجى الاتصال بالإسعاف فورًا لمساعدتي!",
            icon = Icons.Default.Emergency,
            cardColor = DangerRed
        ),
        AssistanceCard(
            id = "hospital",
            titleEn = "Where is the Nearest Hospital?",
            titleAr = "أين يقع أقرب مستشفى؟",
            fullTextEn = "Excuse me, I am looking for the nearest hospital or clinic. Could you please show me the directions on my map?",
            fullTextAr = "عذرًا، أبحث عن أقرب مستشفى أو عيادة طبية. هل يمكنك إرشادي إلى الاتجاهات على الخريطة؟",
            icon = Icons.Default.LocalHospital,
            cardColor = WarningAmber
        ),
        AssistanceCard(
            id = "write_it_down",
            titleEn = "Please Write it Down",
            titleAr = "من فضلك اكتب ما تريد قوله",
            fullTextEn = "I cannot hear your voice. Please type your message into my phone or write it on paper. Thank you for understanding!",
            fullTextAr = "لا أستطيع سماع صوتك. يرجى كتابة رسالتك على هاتفي أو على ورقة. شكرًا لتفهمك ولطفك!",
            icon = Icons.Default.QuestionAnswer,
            cardColor = SuccessGreen
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Assistance Cards Section
        item {
            Column {
                Text(
                    text = if (currentLanguage == AppLanguage.ARABIC)
                        "بطاقات المساعدة والتواصل السريع (Deaf Assist Cards):"
                    else
                        "Deaf Assistance & Communication Cards:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (currentLanguage == AppLanguage.ARABIC)
                        "اضغط على أي بطاقة لعرضها بحجم كامل وشديد الوضوح للآخرين، أو لتشغيل الصوت"
                    else
                        "Tap any card to show fullscreen in high-contrast or speak aloud",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Assistance Cards list
        items(assistanceCards) { card ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = card.cardColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { activeFullscreenCard = card }
                    .testTag("assistance_card_${card.id}")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.2f),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = card.icon,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (currentLanguage == AppLanguage.ARABIC) card.titleAr else card.titleEn,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (currentLanguage == AppLanguage.ARABIC) card.fullTextAr else card.fullTextEn,
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 12.sp,
                                maxLines = 2
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            val text = if (currentLanguage == AppLanguage.ARABIC) card.fullTextAr else card.fullTextEn
                            onSpeakText(text)
                        },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.25f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Speak Card",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // Social Channels Section Header
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (currentLanguage == AppLanguage.ARABIC)
                            "💬 مراسلة وتواصل عبر التطبيقات"
                        else
                            "💬 Direct Messaging & Social Connect",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.ARABIC)
                            "أرسل رسالتك مباشرة عبر تطبيقات التواصل المفضلة:"
                        else
                            "Send message directly via popular messaging channels:",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = customMessage,
                        onValueChange = { customMessage = it },
                        placeholder = {
                            Text(
                                if (currentLanguage == AppLanguage.ARABIC)
                                    "اكتب رسالة لإرسالها عبر التطبيقات..."
                                else
                                    "Type message to send via apps..."
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .testTag("social_message_input")
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Buttons Grid
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        socialChannels.forEach { channel ->
                            Button(
                                onClick = {
                                    val encoded = Uri.encode(customMessage.ifBlank { "Hello from Ahmed ASL Assistant!" })
                                    val url = if (channel.name == "WhatsApp") {
                                        "${channel.baseUrl}$encoded"
                                    } else {
                                        channel.baseUrl
                                    }
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    try {
                                        context.startActivity(intent)
                                    } catch (_: Exception) {}
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = channel.brandColor),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("social_button_${channel.name.lowercase()}")
                            ) {
                                Text(text = channel.iconEmoji, fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "${channel.name} Direct",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    imageVector = Icons.Default.OpenInNew,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Fullscreen High Contrast Assist Card Modal
    activeFullscreenCard?.let { card ->
        ModalBottomSheet(
            onDismissRequest = { activeFullscreenCard = null },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = card.cardColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { activeFullscreenCard = null },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.25f))
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Icon(
                    imageVector = card.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (currentLanguage == AppLanguage.ARABIC) card.titleAr else card.titleEn,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (currentLanguage == AppLanguage.ARABIC) card.fullTextAr else card.fullTextEn,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        lineHeight = 28.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val text = if (currentLanguage == AppLanguage.ARABIC) card.fullTextAr else card.fullTextEn
                        onSpeakText(text)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepNavy),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Icon(Icons.Default.VolumeUp, contentDescription = null, tint = SkyAccent)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.ARABIC) "🔊 تشغيل الصوت عاليًا" else "🔊 Speak Aloud Now",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}
