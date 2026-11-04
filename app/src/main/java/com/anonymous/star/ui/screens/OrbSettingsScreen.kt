package com.anonymous.star.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.anonymous.star.ui.components.PulsingOrb
import com.anonymous.star.ui.theme.BlackBackground
import com.anonymous.star.ui.theme.CrimsonPrimary
import com.anonymous.star.ui.theme.DarkCard
import com.anonymous.star.ui.theme.DarkSurface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrbSettingsScreen(
    prefs: PreferencesManager,
    onBack: () -> Unit
) {
    var orbSize by remember { mutableFloatStateOf(prefs.orbSizeScale) }
    var hueShift by remember { mutableFloatStateOf(prefs.orbHueShift) }
    var mode by remember { mutableStateOf(prefs.orbMode) }
    var presence by remember { mutableStateOf(prefs.systemPresence) }
    var aura by remember { mutableStateOf(prefs.auraBorder) }
    var signature by remember { mutableStateOf(prefs.auraSignature) }
    var core by remember { mutableStateOf(prefs.auraCore) }
    var haptic by remember { mutableStateOf(prefs.hapticFeedback) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Orb Control & Aura Settings", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("orb_settings_back")) {
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
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Orb Live Preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(DarkSurface, RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                PulsingOrb(
                    baseSize = 160.dp,
                    sizeScale = orbSize,
                    hueShift = hueShift,
                    orbMode = mode,
                    systemNodes = presence,
                    auraBorder = aura,
                    auraSignature = signature,
                    auraCore = core,
                    hapticFeedback = haptic
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Size Control
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Adjust Orb Size", color = Color.White, fontWeight = FontWeight.SemiBold)
                        Text("${(orbSize * 100).toInt()}%", color = CrimsonPrimary, fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = orbSize,
                        onValueChange = {
                            orbSize = it
                            prefs.orbSizeScale = it
                        },
                        valueRange = 0.6f..1.5f,
                        colors = SliderDefaults.colors(
                            thumbColor = CrimsonPrimary,
                            activeTrackColor = CrimsonPrimary
                        ),
                        modifier = Modifier.testTag("orb_size_slider")
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Hue Shift Control
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Adjust Hue & Color Wheel", color = Color.White, fontWeight = FontWeight.SemiBold)
                        Text("${hueShift.toInt()}°", color = CrimsonPrimary, fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = hueShift,
                        onValueChange = {
                            hueShift = it
                            prefs.orbHueShift = it
                        },
                        valueRange = 0f..360f,
                        colors = SliderDefaults.colors(
                            thumbColor = CrimsonPrimary,
                            activeTrackColor = CrimsonPrimary
                        ),
                        modifier = Modifier.testTag("orb_hue_slider")
                    )

                    // Quick Color Wheel Presets
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ColorPreset(Color(0xFFFF1744), "Red") { hueShift = 0f; prefs.orbHueShift = 0f }
                        ColorPreset(Color(0xFFFFD600), "Yellow") { hueShift = 60f; prefs.orbHueShift = 60f }
                        ColorPreset(Color(0xFF00E676), "Green") { hueShift = 120f; prefs.orbHueShift = 120f }
                        ColorPreset(Color(0xFF00E5FF), "Cyan") { hueShift = 180f; prefs.orbHueShift = 180f }
                        ColorPreset(Color(0xFF7C4DFF), "Purple") { hueShift = 270f; prefs.orbHueShift = 270f }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Orb Modes
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Orb Engine Modes", color = Color.White, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Classic", "Energy", "Neon").forEach { item ->
                            FilterChip(
                                selected = mode == item,
                                onClick = {
                                    mode = item
                                    prefs.orbMode = item
                                },
                                label = { Text(item) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = CrimsonPrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Toggles
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ToggleRow("System Nodes Presence", presence) {
                        presence = it
                        prefs.systemPresence = it
                    }
                    Divider(color = BlackBackground, thickness = 1.dp)
                    ToggleRow("Aura Glow Border", aura) {
                        aura = it
                        prefs.auraBorder = it
                    }
                    Divider(color = BlackBackground, thickness = 1.dp)
                    ToggleRow("Aura Signature Rings", signature) {
                        signature = it
                        prefs.auraSignature = it
                    }
                    Divider(color = BlackBackground, thickness = 1.dp)
                    ToggleRow("Aura Core Gradient", core) {
                        core = it
                        prefs.auraCore = it
                    }
                    Divider(color = BlackBackground, thickness = 1.dp)
                    ToggleRow("Haptic Feedback ON", haptic) {
                        haptic = it
                        prefs.hapticFeedback = it
                    }
                }
            }
        }
    }
}

@Composable
fun ColorPreset(color: Color, name: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .background(color, CircleShape)
            .clickable { onClick() }
    )
}

@Composable
fun ToggleRow(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, color = Color.White, fontSize = 14.sp)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = CrimsonPrimary
            )
        )
    }
}
