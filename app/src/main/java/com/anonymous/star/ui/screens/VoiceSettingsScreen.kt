package com.anonymous.star.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anonymous.star.data.PreferencesManager
import com.anonymous.star.ui.theme.BlackBackground
import com.anonymous.star.ui.theme.CrimsonPrimary
import com.anonymous.star.ui.theme.DarkCard
import com.anonymous.star.ui.theme.DarkSurface
import com.anonymous.star.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceSettingsScreen(
    prefs: PreferencesManager,
    onBack: () -> Unit
) {
    var audioMode by remember { mutableStateOf(prefs.audioMode) }
    var personality by remember { mutableStateOf(prefs.personality) }
    var wakeWord by remember { mutableStateOf(prefs.wakeWord) }
    var pitch by remember { mutableFloatStateOf(prefs.pitch) }
    var vad by remember { mutableFloatStateOf(prefs.vadSensitivity) }

    val audioEngineModes = listOf("ElevenLabs Realtime", "Gemini 3.8 Live", "Dec 2025 Preview")
    val personalities = listOf(
        "Author", "Assistant", "Normal", "GF", "Romantic", "Friend", "Editor", "Cyber Sweet", "Cyber Rude"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Neural Voice & AI Personality", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("voice_settings_back")) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BlackBackground)
            )
        },
        containerColor = BlackBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Credit Bar Card
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.GraphicEq, contentDescription = null, tint = CrimsonPrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Neural Credit Balance", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                        Text("${prefs.audioCredits} / 10,000", color = SuccessGreen, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = { prefs.audioCredits / 10000f },
                        modifier = Modifier.fillMaxWidth().height(8.dp),
                        color = CrimsonPrimary,
                        trackColor = BlackBackground
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Audio Mode Selector
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("Audio Streaming Engine", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    audioEngineModes.forEach { modeItem ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = audioMode == modeItem,
                                onClick = {
                                    audioMode = modeItem
                                    prefs.audioMode = modeItem
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = CrimsonPrimary)
                            )
                            Text(modeItem, color = Color.White, fontSize = 15.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Personalities
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Psychology, contentDescription = null, tint = CrimsonPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("AI Personality Mirror", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Column {
                        personalities.chunked(3).forEach { rowItems ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                rowItems.forEach { pItem ->
                                    FilterChip(
                                        selected = personality == pItem,
                                        onClick = {
                                            personality = pItem
                                            prefs.personality = pItem
                                        },
                                        label = { Text(pItem) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = CrimsonPrimary,
                                            selectedLabelColor = Color.White
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Wake Word & Fine Tuning
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("Wake Word Detection", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = wakeWord,
                        onValueChange = {
                            wakeWord = it
                            prefs.wakeWord = it
                        },
                        label = { Text("Wake Word", color = Color.Gray) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CrimsonPrimary,
                            focusedTextColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Voice Pitch Modulation", color = Color.White, fontSize = 14.sp)
                    Slider(
                        value = pitch,
                        onValueChange = {
                            pitch = it
                            prefs.pitch = it
                        },
                        valueRange = 0.5f..1.5f,
                        colors = SliderDefaults.colors(
                            thumbColor = CrimsonPrimary,
                            activeTrackColor = CrimsonPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("VAD Sensitivity", color = Color.White, fontSize = 14.sp)
                    Slider(
                        value = vad,
                        onValueChange = {
                            vad = it
                            prefs.vadSensitivity = it
                        },
                        valueRange = 0.1f..1.0f,
                        colors = SliderDefaults.colors(
                            thumbColor = CrimsonPrimary,
                            activeTrackColor = CrimsonPrimary
                        )
                    )
                }
            }
        }
    }
}
