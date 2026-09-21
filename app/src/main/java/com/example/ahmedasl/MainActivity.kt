package com.example.ahmedasl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.LayoutDirection
import com.example.ahmedasl.model.AppLanguage
import com.example.ahmedasl.service.SpeechRecognitionHelper
import com.example.ahmedasl.service.TextToSpeechHelper
import com.example.ahmedasl.ui.components.AppHeader
import com.example.ahmedasl.ui.components.ProfileDialog
import com.example.ahmedasl.ui.screens.AiAssistantScreen
import com.example.ahmedasl.ui.screens.CameraSignScreen
import com.example.ahmedasl.ui.screens.DirectConnectScreen
import com.example.ahmedasl.ui.screens.SignDictionaryScreen
import com.example.ahmedasl.ui.theme.AhmedASLTheme

enum class AppDestination(
    val titleEn: String,
    val titleAr: String,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector,
    val testTag: String
) {
    CAMERA(
        "Camera ASL",
        "الكاميرا والإشارة",
        Icons.Filled.Videocam,
        Icons.Outlined.Videocam,
        "nav_camera"
    ),
    AI_ASSISTANT(
        "AI Assistant",
        "المساعد الذكي",
        Icons.Filled.AutoAwesome,
        Icons.Outlined.AutoAwesome,
        "nav_ai"
    ),
    DICTIONARY(
        "Dictionary",
        "قاموس الإشارات",
        Icons.Filled.MenuBook,
        Icons.Outlined.MenuBook,
        "nav_dictionary"
    ),
    CONNECT(
        "Connect",
        "تواصل ومساعدة",
        Icons.Filled.Message,
        Icons.Outlined.Message,
        "nav_connect"
    )
}

class MainActivity : ComponentActivity() {

    private lateinit var speechHelper: SpeechRecognitionHelper
    private lateinit var ttsHelper: TextToSpeechHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        speechHelper = SpeechRecognitionHelper(this)
        ttsHelper = TextToSpeechHelper(this)

        setContent {
            var currentLanguage by remember { mutableStateOf(AppLanguage.ARABIC) }
            var currentDestination by remember { mutableStateOf(AppDestination.CAMERA) }
            var showProfileDialog by remember { mutableStateOf(false) }

            val layoutDirection = if (currentLanguage.isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr

            CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                AhmedASLTheme {
                    Scaffold(
                        topBar = {
                            AppHeader(
                                currentLanguage = currentLanguage,
                                onLanguageChange = { currentLanguage = it },
                                onProfileClick = { showProfileDialog = true }
                            )
                        },
                        bottomBar = {
                            NavigationBar(
                                modifier = Modifier
                                    .windowInsetsPadding(WindowInsets.navigationBars)
                                    .testTag("bottom_navigation")
                            ) {
                                AppDestination.entries.forEach { dest ->
                                    val isSelected = currentDestination == dest
                                    NavigationBarItem(
                                        selected = isSelected,
                                        onClick = { currentDestination = dest },
                                        icon = {
                                            Icon(
                                                imageVector = if (isSelected) dest.activeIcon else dest.inactiveIcon,
                                                contentDescription = dest.titleEn
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = if (currentLanguage == AppLanguage.ARABIC) dest.titleAr else dest.titleEn
                                            )
                                        },
                                        modifier = Modifier.testTag(dest.testTag)
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            when (currentDestination) {
                                AppDestination.CAMERA -> CameraSignScreen(
                                    currentLanguage = currentLanguage,
                                    onSpeakText = { text ->
                                        ttsHelper.speak(text, currentLanguage)
                                    }
                                )
                                AppDestination.AI_ASSISTANT -> AiAssistantScreen(
                                    currentLanguage = currentLanguage,
                                    speechHelper = speechHelper,
                                    onSpeakText = { text ->
                                        ttsHelper.speak(text, currentLanguage)
                                    }
                                )
                                AppDestination.DICTIONARY -> SignDictionaryScreen(
                                    currentLanguage = currentLanguage,
                                    onSpeakText = { text ->
                                        ttsHelper.speak(text, currentLanguage)
                                    }
                                )
                                AppDestination.CONNECT -> DirectConnectScreen(
                                    currentLanguage = currentLanguage,
                                    onSpeakText = { text ->
                                        ttsHelper.speak(text, currentLanguage)
                                    }
                                )
                            }

                            if (showProfileDialog) {
                                ProfileDialog(
                                    currentLanguage = currentLanguage,
                                    onDismiss = { showProfileDialog = false }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        speechHelper.stopListening()
        ttsHelper.shutdown()
    }
}
