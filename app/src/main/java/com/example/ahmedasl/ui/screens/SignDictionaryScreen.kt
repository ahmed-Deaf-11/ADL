package com.example.ahmedasl.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ahmedasl.data.SignGestureRepository
import com.example.ahmedasl.model.AppLanguage
import com.example.ahmedasl.model.SignCategory
import com.example.ahmedasl.model.SignGesture
import com.example.ahmedasl.ui.theme.DeepNavy
import com.example.ahmedasl.ui.theme.PrimaryBlue
import com.example.ahmedasl.ui.theme.SkyAccent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignDictionaryScreen(
    currentLanguage: AppLanguage,
    onSpeakText: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<SignCategory?>(null) }
    var selectedGesture by remember { mutableStateOf<SignGesture?>(null) }

    val filteredGestures = remember(searchQuery, selectedCategory) {
        SignGestureRepository.searchGestures(searchQuery, selectedCategory)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text(
                    if (currentLanguage == AppLanguage.ARABIC)
                        "ابحث عن إشارة أو كلمة..."
                    else
                        "Search sign gesture..."
                )
            },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("dictionary_search_input")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Category Filter Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                FilterChip(
                    selected = selectedCategory == null,
                    onClick = { selectedCategory = null },
                    label = {
                        Text(if (currentLanguage == AppLanguage.ARABIC) "الكل" else "All Signs")
                    }
                )
            }
            items(SignCategory.entries.toTypedArray()) { cat ->
                FilterChip(
                    selected = selectedCategory == cat,
                    onClick = {
                        selectedCategory = if (selectedCategory == cat) null else cat
                    },
                    label = {
                        Text("${cat.icon} " + if (currentLanguage == AppLanguage.ARABIC) cat.labelAr else cat.labelEn)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Grid of Gestures
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 90.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredGestures) { gesture ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedGesture = gesture }
                        .testTag("gesture_card_${gesture.token}")
                ) {
                    Column {
                        // Image Thumbnail
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .background(Color(0xFF0F172A))
                        ) {
                            if (gesture.imageRes != null) {
                                Image(
                                    painter = painterResource(id = gesture.imageRes),
                                    contentDescription = gesture.nameEn,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = DeepNavy.copy(alpha = 0.85f),
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(6.dp)
                            ) {
                                Text(
                                    text = gesture.token.uppercase(),
                                    color = SkyAccent,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        // Text content
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = if (currentLanguage == AppLanguage.ARABIC) gesture.nameAr else gesture.nameEn,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = gesture.handShape,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }

    // Detail Bottom Sheet
    selectedGesture?.let { gesture ->
        ModalBottomSheet(
            onDismissRequest = { selectedGesture = null },
            sheetState = rememberModalBottomSheetState()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 36.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (currentLanguage == AppLanguage.ARABIC) gesture.nameAr else gesture.nameEn,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Token: ${gesture.token.uppercase()} • ${gesture.nameId}",
                            fontSize = 13.sp,
                            color = PrimaryBlue
                        )
                    }

                    IconButton(
                        onClick = {
                            val textToSpeak = if (currentLanguage == AppLanguage.ARABIC) gesture.nameAr else gesture.nameEn
                            onSpeakText(textToSpeak)
                        },
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(PrimaryBlue)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Speak Gesture",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Big Visual Asset
                if (gesture.imageRes != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFF0F172A))
                    ) {
                        Image(
                            painter = painterResource(id = gesture.imageRes),
                            contentDescription = gesture.nameEn,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = if (currentLanguage == AppLanguage.ARABIC) "شكل اليد (Hand Shape):" else "Hand Shape:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = PrimaryBlue
                        )
                        Text(
                            text = gesture.handShape,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (currentLanguage == AppLanguage.ARABIC) "حركة الإشارة وطريقة الأداء:" else "Movement Execution:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = PrimaryBlue
                        )
                        Text(
                            text = if (currentLanguage == AppLanguage.ARABIC) gesture.descriptionAr else gesture.descriptionEn,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
