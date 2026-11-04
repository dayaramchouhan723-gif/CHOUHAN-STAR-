package com.anonymous.star.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
    var wakeWordInput by remember { mutableStateOf(prefs.wakeWord) }
    var picovoiceOn by remember { mutableStateOf(prefs.picovoiceEnabled) }
    var offlineBetaOn by remember { mutableStateOf(prefs.offlineWakeWordBeta) }
    var heyGoogleOn by remember { mutableStateOf(prefs.heyGoogleVariant) }
    var pitch by remember { mutableFloatStateOf(prefs.pitch) }
    var vad by remember { mutableFloatStateOf(prefs.vadSensitivity) }
    var isListeningTapToTalk by remember { mutableStateOf(false) }

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
            // Tap to Talk Foreground Listening Banner
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isListeningTapToTalk) CrimsonPrimary.copy(alpha = 0.2f) else DarkCard
                ),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isListeningTapToTalk = !isListeningTapToTalk }
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isListeningTapToTalk) Icons.Default.Mic else Icons.Default.MicOff,
                            contentDescription = "Listening Mode",
                            tint = if (isListeningTapToTalk) CrimsonPrimary else Color.Gray,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (isListeningTapToTalk) "Tap to Talk: LISTENING ACTIVE..." else "Tap to Talk Mode",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "Foreground voice listening active on touch",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Surface(
                        color = if (isListeningTapToTalk) CrimsonPrimary else DarkSurface,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (isListeningTapToTalk) "ACTIVE" else "TAP TO START",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

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

            // Wake Word Engine & Custom Name Input Card
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("Wake Word Real Engine", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Current Wake Word: ${prefs.wakeWord}",
                        color = CrimsonPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = wakeWordInput,
                        onValueChange = { wakeWordInput = it },
                        label = { Text("Just the name, e.g. Zara", color = Color.Gray) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CrimsonPrimary,
                            focusedTextColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Red Save Button
                    Button(
                        onClick = {
                            if (wakeWordInput.isNotBlank()) {
                                prefs.wakeWord = wakeWordInput.trim()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("save_wakeword_red_btn")
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Save Wake Word", color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(color = BlackBackground)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Hey Google Variant Option
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Hey Google Variant Trigger", color = Color.White, fontSize = 14.sp)
                        Switch(
                            checked = heyGoogleOn,
                            onCheckedChange = {
                                heyGoogleOn = it
                                prefs.heyGoogleVariant = it
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = CrimsonPrimary
                            )
                        )
                    }

                    // Picovoice Engine Option
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Picovoice Porcupine Engine", color = Color.White, fontSize = 14.sp)
                        Switch(
                            checked = picovoiceOn,
                            onCheckedChange = {
                                picovoiceOn = it
                                prefs.picovoiceEnabled = it
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = CrimsonPrimary
                            )
                        )
                    }

                    // Offline Beta Engine Option
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Offline Local Engine (Beta)", color = Color.White, fontSize = 14.sp)
                        Switch(
                            checked = offlineBetaOn,
                            onCheckedChange = {
                                offlineBetaOn = it
                                prefs.offlineWakeWordBeta = it
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = CrimsonPrimary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

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

                    Spacer(modifier = Modifier.height(8.dp))

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
        }
    }
}
