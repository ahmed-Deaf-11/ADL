package com.example.ahmedasl.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ahmedasl.model.AppLanguage
import com.example.ahmedasl.ui.theme.BorderColor
import com.example.ahmedasl.ui.theme.DeepNavy
import com.example.ahmedasl.ui.theme.PrimaryBlue
import com.example.ahmedasl.ui.theme.SkyAccent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDialog(
    currentLanguage: AppLanguage,
    onDismiss: () -> Unit
) {
    var loggedInUser by remember { mutableStateOf<String?>("ahmed01095956350@gmail.com") }
    var autoPlayGestures by remember { mutableStateOf(true) }
    var highContrastMode by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 36.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        tint = PrimaryBlue,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.ARABIC) "حساب المستخدم والإعدادات" else "User Account & Settings",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // User Profile Card
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = DeepNavy,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = SkyAccent,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "🤟", fontSize = 24.sp)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = loggedInUser ?: "Guest User",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Text(
                            text = if (currentLanguage == AppLanguage.ARABIC) "عضو نشط في منصة Ahmed ASL" else "Active Member • Ahmed ASL",
                            color = Color(0xFF94A3B8),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // OAuth login buttons
            Text(
                text = if (currentLanguage == AppLanguage.ARABIC) "تسجيل الدخول السريع (OAuth):" else "Quick Sign In:",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { loggedInUser = "google.user@gmail.com" },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEA4335)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).height(44.dp)
                ) {
                    Text(text = "Google", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { loggedInUser = "facebook.user@fb.com" },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1877F2)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).height(44.dp)
                ) {
                    Text(text = "Facebook", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Settings toggles
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (currentLanguage == AppLanguage.ARABIC) "تشغيل حركات الإشارة تلقائيًا" else "Auto-play Gesture Sequence",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = if (currentLanguage == AppLanguage.ARABIC) "التنقل بين الإشارات المتتالية تلقائيًا" else "Advance through sign tokens automatically",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = autoPlayGestures,
                            onCheckedChange = { autoPlayGestures = it }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (currentLanguage == AppLanguage.ARABIC) "وضع التباين العالي" else "High Contrast Display",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = if (currentLanguage == AppLanguage.ARABIC) "تحسين وضوح النصوص للصم وضعاف البصر" else "Enhanced text legibility",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = highContrastMode,
                            onCheckedChange = { highContrastMode = it }
                        )
                    }
                }
            }
        }
    }
}
