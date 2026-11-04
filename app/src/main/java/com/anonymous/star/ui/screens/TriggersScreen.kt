package com.anonymous.star.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anonymous.star.data.PreferencesManager
import com.anonymous.star.ui.theme.BlackBackground
import com.anonymous.star.ui.theme.CrimsonPrimary
import com.anonymous.star.ui.theme.DarkCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriggersScreen(
    prefs: PreferencesManager,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    var chargingTrigger by remember { mutableStateOf(prefs.chargingTriggerEnabled) }
    var batteryTrigger by remember { mutableStateOf(prefs.batteryTriggerEnabled) }
    var screenTrigger by remember { mutableStateOf(prefs.screenUnlockTriggerEnabled) }
    var autoReply by remember { mutableStateOf(prefs.autoReplyNotifications) }
    var speakCaller by remember { mutableStateOf(prefs.speakCallerName) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Triggers & Automations Engine", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("triggers_back")) {
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
            // Summary Card
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Bolt, contentDescription = null, tint = CrimsonPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Active Trigger Flows", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "Triggers run autonomously in background (Every 30 min / 11pm-7am mode active). Voice responses trigger automatically on state change.",
                        color = Color.LightGray,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 6 Trigger Types
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("1. Charging State Plugged/Unplugged", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("\"Boss charging start ho gayi, 100% hone par bata dungi\"", color = Color.Gray, fontSize = 12.sp)
                    Switch(
                        checked = chargingTrigger,
                        onCheckedChange = {
                            chargingTrigger = it
                            prefs.chargingTriggerEnabled = it
                        },
                        colors = SwitchDefaults.colors(checkedTrackColor = CrimsonPrimary)
                    )

                    Divider(color = BlackBackground, modifier = Modifier.padding(vertical = 12.dp))

                    Text("2. Battery Level Threshold (20% - 100%)", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("\"Battery level low par 20% se 100% tak main dekh lungi\"", color = Color.Gray, fontSize = 12.sp)
                    Switch(
                        checked = batteryTrigger,
                        onCheckedChange = {
                            batteryTrigger = it
                            prefs.batteryTriggerEnabled = it
                        },
                        colors = SwitchDefaults.colors(checkedTrackColor = CrimsonPrimary)
                    )

                    Divider(color = BlackBackground, modifier = Modifier.padding(vertical = 12.dp))

                    Text("3. Screen & Device Unlock Event", color = Color.White, fontWeight = FontWeight.Bold)
                    Switch(
                        checked = screenTrigger,
                        onCheckedChange = {
                            screenTrigger = it
                            prefs.screenUnlockTriggerEnabled = it
                        },
                        colors = SwitchDefaults.colors(checkedTrackColor = CrimsonPrimary)
                    )

                    Divider(color = BlackBackground, modifier = Modifier.padding(vertical = 12.dp))

                    Text("4. Headphones Plug / Bluetooth Link", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("Auto-switch audio stream to headset", color = Color.Gray, fontSize = 12.sp)

                    Divider(color = BlackBackground, modifier = Modifier.padding(vertical = 12.dp))

                    Text("5. Wi-Fi Home / Work Network Connect", color = Color.White, fontWeight = FontWeight.Bold)

                    Divider(color = BlackBackground, modifier = Modifier.padding(vertical = 12.dp))

                    Text("6. App Opened or Closed Event", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Notification & Caller Reader
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = CrimsonPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Smart Notification & Caller ID Reader", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    ToggleRow("Speak Caller Name Automatically", speakCaller) {
                        speakCaller = it
                        prefs.speakCallerName = it
                    }

                    Divider(color = BlackBackground)

                    ToggleRow("Read WhatsApp/Telegram OTP & Priority Messages", autoReply) {
                        autoReply = it
                        prefs.autoReplyNotifications = it
                    }
                }
            }
        }
    }
}
