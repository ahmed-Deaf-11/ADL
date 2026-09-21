package com.example.ahmedasl.ui.components

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.ahmedasl.model.AppLanguage
import com.example.ahmedasl.model.CameraSignDetection
import com.example.ahmedasl.ui.theme.DangerRed
import com.example.ahmedasl.ui.theme.DeepNavy
import com.example.ahmedasl.ui.theme.PrimaryBlue
import com.example.ahmedasl.ui.theme.SkyAccent
import com.example.ahmedasl.ui.theme.SuccessGreen
import kotlinx.coroutines.delay

@Composable
fun CameraPreviewView(
    isCameraRunning: Boolean,
    onToggleCamera: () -> Unit,
    currentLanguage: AppLanguage,
    onSignRecognized: (CameraSignDetection) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var lensFacing by remember { mutableIntStateOf(CameraSelector.LENS_FACING_FRONT) }
    var currentDetection by remember {
        mutableStateOf(
            CameraSignDetection(
                detectedToken = "hello",
                gestureNameAr = "مرحبًا (تحية)",
                gestureNameEn = "Hello (Salute)",
                confidence = 0.94f
            )
        )
    }

    // Hand detection simulation scan animation
    val infiniteTransition = rememberInfiniteTransition(label = "scan_transition")
    val scanLineProgress by infiniteTransition.animateFloat(
        initialValue = 0.15f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scan_line"
    )

    // Simulated real-time ASL sign recognizer loop when camera is active
    LaunchedEffect(isCameraRunning) {
        val sampleDetections = listOf(
            CameraSignDetection("hello", "مرحبًا", "Hello", 0.96f),
            CameraSignDetection("thank_you", "شكرًا لك", "Thank you", 0.92f),
            CameraSignDetection("how_are_you", "كيف حالك؟", "How are you?", 0.89f),
            CameraSignDetection("love", "أحبك (ILY)", "I Love You", 0.98f),
            CameraSignDetection("help", "طلب مساعدة", "Help Needed", 0.91f),
            CameraSignDetection("yes", "نعم / موافق", "Yes", 0.95f),
            CameraSignDetection("please", "من فضلك", "Please", 0.93f)
        )
        var index = 0
        while (isCameraRunning) {
            delay(3500)
            val detection = sampleDetections[index % sampleDetections.size]
            currentDetection = detection
            onSignRecognized(detection)
            index++
        }
    }

    Card(
        modifier = modifier.testTag("camera_card"),
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
                    Text(text = "📹", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.ARABIC)
                            "فيديو الكاميرا والتعرف على الإشارة"
                        else
                            "Camera & ASL Detection",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isCameraRunning) SuccessGreen.copy(alpha = 0.15f) else Color(0xFF64748B).copy(alpha = 0.15f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FiberManualRecord,
                            contentDescription = null,
                            tint = if (isCameraRunning) SuccessGreen else Color(0xFF64748B),
                            modifier = Modifier.size(10.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isCameraRunning) {
                                if (currentLanguage == AppLanguage.ARABIC) "الكاميرا تعمل" else "Live Camera"
                            } else {
                                if (currentLanguage == AppLanguage.ARABIC) "الكاميرا متوقفة" else "Camera Off"
                            },
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isCameraRunning) SuccessGreen else Color(0xFF64748B)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Camera Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF020617))
            ) {
                if (isCameraRunning) {
                    // Real CameraX AndroidView
                    AndroidView(
                        factory = { ctx ->
                            val previewView = PreviewView(ctx).apply {
                                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                            }
                            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                            cameraProviderFuture.addListener({
                                try {
                                    val cameraProvider = cameraProviderFuture.get()
                                    val preview = Preview.Builder().build().also {
                                        it.setSurfaceProvider(previewView.surfaceProvider)
                                    }
                                    val cameraSelector = CameraSelector.Builder()
                                        .requireLensFacing(lensFacing)
                                        .build()
                                    cameraProvider.unbindAll()
                                    cameraProvider.bindToLifecycle(
                                        lifecycleOwner,
                                        cameraSelector,
                                        preview
                                    )
                                } catch (_: Exception) {}
                            }, ContextCompat.getMainExecutor(ctx))
                            previewView
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                    // Hand Tracking Bounding Box & AI Scanner Overlay
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val boxLeft = size.width * 0.22f
                        val boxTop = size.height * 0.18f
                        val boxWidth = size.width * 0.56f
                        val boxHeight = size.height * 0.64f

                        // Scanning box border
                        drawRoundRect(
                            color = SkyAccent,
                            topLeft = Offset(boxLeft, boxTop),
                            size = Size(boxWidth, boxHeight),
                            style = Stroke(width = 3.dp.toPx()),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f)
                        )

                        // Scan sweep line
                        val lineY = boxTop + (boxHeight * scanLineProgress)
                        drawLine(
                            color = Color(0xAA38BDF8),
                            start = Offset(boxLeft + 10f, lineY),
                            end = Offset(boxLeft + boxWidth - 10f, lineY),
                            strokeWidth = 2.dp.toPx()
                        )
                    }

                    // Floating Detection Result Badge
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = DeepNavy.copy(alpha = 0.85f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(text = "🤟", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = if (currentLanguage == AppLanguage.ARABIC) currentDetection.gestureNameAr else currentDetection.gestureNameEn,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "AI Confidence: ${(currentDetection.confidence * 100).toInt()}%",
                                    color = SkyAccent,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    // Flip camera lens button
                    IconButton(
                        onClick = {
                            lensFacing = if (lensFacing == CameraSelector.LENS_FACING_FRONT)
                                CameraSelector.LENS_FACING_BACK
                            else
                                CameraSelector.LENS_FACING_FRONT
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(12.dp)
                            .size(40.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(DeepNavy.copy(alpha = 0.75f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cameraswitch,
                            contentDescription = "Switch Camera",
                            tint = Color.White
                        )
                    }
                } else {
                    // Camera Inactive State
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "📷", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = if (currentLanguage == AppLanguage.ARABIC)
                                "الكاميرا متوقفة حاليًا"
                            else
                                "Camera is currently inactive",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (currentLanguage == AppLanguage.ARABIC)
                                "اضغط على زر تشغيل الكاميرا لبدء قراءة لغة الإشارة المباشرة"
                            else
                                "Tap Start Camera to begin real-time ASL sign reading",
                            color = Color(0xFF94A3B8),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onToggleCamera,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isCameraRunning) DangerRed else PrimaryBlue
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("toggle_camera_button")
                ) {
                    Icon(
                        imageVector = if (isCameraRunning) Icons.Default.Stop else Icons.Default.Videocam,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isCameraRunning) {
                            if (currentLanguage == AppLanguage.ARABIC) "إيقاف الكاميرا" else "Stop Camera"
                        } else {
                            if (currentLanguage == AppLanguage.ARABIC) "تشغيل الكاميرا" else "Start Camera"
                        },
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
