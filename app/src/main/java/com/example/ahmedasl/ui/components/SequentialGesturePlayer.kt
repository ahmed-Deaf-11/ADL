package com.example.ahmedasl.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ahmedasl.model.AppLanguage
import com.example.ahmedasl.model.SignGesture
import com.example.ahmedasl.ui.theme.BorderColor
import com.example.ahmedasl.ui.theme.DeepNavy
import com.example.ahmedasl.ui.theme.PrimaryBlue
import com.example.ahmedasl.ui.theme.SkyAccent
import com.example.ahmedasl.ui.theme.SuccessGreen
import kotlinx.coroutines.delay

@Composable
fun SequentialGesturePlayer(
    gestures: List<SignGesture>,
    currentLanguage: AppLanguage,
    onSpeakText: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (gestures.isEmpty()) return

    var currentIndex by remember(gestures) { mutableIntStateOf(0) }
    var isPlaying by remember { mutableStateOf(true) }

    val currentGesture = gestures.getOrNull(currentIndex) ?: gestures[0]

    // Auto-advance sequential gesture playback
    LaunchedEffect(isPlaying, currentIndex, gestures) {
        if (isPlaying && gestures.size > 1) {
            delay(2800)
            currentIndex = (currentIndex + 1) % gestures.size
        }
    }

    Card(
        modifier = modifier.testTag("sequential_player_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🎬", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.ARABIC)
                            "عرض حركات لغة الإشارة المتتالية"
                        else
                            "Sequential Sign Gesture Playback",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = PrimaryBlue.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = "${currentIndex + 1} / ${gestures.size}",
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Progress bar
            LinearProgressIndicator(
                progress = { (currentIndex + 1).toFloat() / gestures.size },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = SkyAccent,
                trackColor = BorderColor
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Animated Visual Display Box
            AnimatedContent(
                targetState = currentGesture,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "gesture_animation"
            ) { gesture ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF0F172A))
                ) {
                    // Image asset
                    if (gesture.imageRes != null) {
                        Image(
                            painter = painterResource(id = gesture.imageRes),
                            contentDescription = gesture.nameEn,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Token Badge overlay
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = DeepNavy.copy(alpha = 0.85f),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "TOKEN: ${gesture.token.uppercase()}",
                            color = SkyAccent,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    // Bottom info gradient banner
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth(),
                        color = DeepNavy.copy(alpha = 0.88f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (currentLanguage == AppLanguage.ARABIC) gesture.nameAr else gesture.nameEn,
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = gesture.handShape,
                                    color = Color(0xFFCBD5E1),
                                    fontSize = 12.sp
                                )
                            }

                            IconButton(
                                onClick = {
                                    val textToSpeak = if (currentLanguage == AppLanguage.ARABIC) gesture.nameAr else gesture.nameEn
                                    onSpeakText(textToSpeak)
                                },
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryBlue)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Speak Gesture",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Motion Instruction Description
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = if (currentLanguage == AppLanguage.ARABIC) "طريقة الحركة والإشارة:" else "Movement Guide:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.ARABIC) currentGesture.descriptionAr else currentGesture.descriptionEn,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Player Controls Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Previous
                IconButton(
                    onClick = {
                        currentIndex = if (currentIndex > 0) currentIndex - 1 else gestures.size - 1
                    },
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Previous Gesture"
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Play / Pause Toggle
                IconButton(
                    onClick = { isPlaying = !isPlaying },
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlue)
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Next
                IconButton(
                    onClick = {
                        currentIndex = (currentIndex + 1) % gestures.size
                    },
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next Gesture"
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Replay
                IconButton(
                    onClick = {
                        currentIndex = 0
                        isPlaying = true
                    },
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Icon(
                        imageVector = Icons.Default.Replay,
                        contentDescription = "Replay from Start"
                    )
                }
            }
        }
    }
}
