package com.anonymous.star.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anonymous.star.R
import com.anonymous.star.data.PreferencesManager
import com.anonymous.star.ui.components.PulsingOrb
import com.anonymous.star.ui.theme.BlackBackground
import com.anonymous.star.ui.theme.CrimsonPrimary
import com.anonymous.star.ui.theme.DarkCard
import com.anonymous.star.ui.theme.DarkSurface
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    prefs: PreferencesManager,
    onNavigateTo: (String) -> Unit
) {
    var wallpaperIndex by remember { mutableIntStateOf(prefs.currentWallpaperIndex) }
    var showWallpaperSelector by remember { mutableStateOf(false) }

    val wallpapers = listOf(
        R.drawable.wallpaper_pink_hair_1793791451611 to "Pink Hair Cyber Girl",
        R.drawable.wallpaper_white_wings_1793791467846 to "White Wings Cyber Angel",
        R.drawable.wallpaper_brown_eyes_1793791484939 to "Expressive Brown Eyes",
        R.drawable.wallpaper_blue_crying_1793791500967 to "Blue Crying Tears",
        R.drawable.wallpaper_demon_red_1793791518645 to "Demon Red Aura",
        R.drawable.wallpaper_orange_hoodie_1793791535007 to "Orange Hoodie Boy"
    )

    val timeFormat = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    val dateFormat = remember { SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault()) }
    var currentTime by remember { mutableStateOf(timeFormat.format(Date())) }
    var currentDate by remember { mutableStateOf(dateFormat.format(Date())) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackBackground)
    ) {
        // Wallpaper Background
        Image(
            painter = painterResource(id = wallpapers[wallpaperIndex % wallpapers.size].first),
            contentDescription = "Background Wallpaper",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Dark Overlay for Cyber Effect
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        // Top Header - Greeting & Clock
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hello, ${prefs.userName}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White
                    )
                    Text(
                        text = "Anonymous Star ⭐ ${prefs.userUid}",
                        style = MaterialTheme.typography.labelMedium,
                        color = CrimsonPrimary
                    )
                }

                IconButton(
                    onClick = { showWallpaperSelector = !showWallpaperSelector },
                    modifier = Modifier
                        .background(DarkCard.copy(alpha = 0.8f), CircleShape)
                        .testTag("wallpaper_toggle_btn")
                ) {
                    Icon(Icons.Default.Wallpaper, contentDescription = "Wallpapers", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Launcher Clock
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = currentTime,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White
                )
                Text(
                    text = currentDate,
                    fontSize = 12.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }

        // Center Pulsing Orb
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentAlignment = Alignment.Center
        ) {
            PulsingOrb(
                baseSize = 220.dp,
                sizeScale = prefs.orbSizeScale,
                hueShift = prefs.orbHueShift,
                orbMode = prefs.orbMode,
                systemNodes = prefs.systemPresence,
                auraBorder = prefs.auraBorder,
                onClick = { onNavigateTo("orb_settings") }
            )
        }

        // Wallpaper Switcher Drawer (if active)
        AnimatedVisibility(
            visible = showWallpaperSelector,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 130.dp)
        ) {
            Surface(
                color = DarkSurface.copy(alpha = 0.95f),
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CrimsonPrimary),
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Select Cyber Wallpaper", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        itemsIndexed(wallpapers) { idx, item ->
                            Box(
                                modifier = Modifier
                                    .size(70.dp, 100.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(
                                        width = if (wallpaperIndex == idx) 2.dp else 0.dp,
                                        color = if (wallpaperIndex == idx) CrimsonPrimary else Color.Transparent,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        wallpaperIndex = idx
                                        prefs.currentWallpaperIndex = idx
                                        showWallpaperSelector = false
                                    }
                            ) {
                                Image(
                                    painter = painterResource(id = item.first),
                                    contentDescription = item.second,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }
        }

        // Bottom Launcher Docks (MYRA Style)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                color = DarkCard.copy(alpha = 0.88f),
                shape = RoundedCornerShape(28.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CrimsonPrimary.copy(alpha = 0.4f)),
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .height(68.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DockItem(Icons.Default.Mic, "Voice") { onNavigateTo("voice_settings") }
                    DockItem(Icons.Default.Apps, "Tools") { onNavigateTo("tools") }
                    DockItem(Icons.Default.CameraAlt, "Vision") { onNavigateTo("camera") }
                    DockItem(Icons.Default.Key, "API") { onNavigateTo("api_vault") }
                    DockItem(Icons.Default.Bolt, "Triggers") { onNavigateTo("triggers") }
                    DockItem(Icons.Default.Computer, "PC Connect") { onNavigateTo("pc_connect") }
                    DockItem(Icons.Default.Psychology, "Memory") { onNavigateTo("memory") }
                    DockItem(Icons.Default.Shield, "Security") { onNavigateTo("security") }
                }
            }
        }
    }
}

@Composable
fun DockItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(6.dp)
            .testTag("dock_${label.lowercase()}")
    ) {
        Icon(icon, contentDescription = label, tint = CrimsonPrimary, modifier = Modifier.size(22.dp))
        Text(label, fontSize = 9.sp, color = Color.White, fontWeight = FontWeight.Medium)
    }
}
