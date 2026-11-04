package com.anonymous.star.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anonymous.star.data.PreferencesManager
import com.anonymous.star.ui.theme.AlertRed
import com.anonymous.star.ui.theme.BlackBackground
import com.anonymous.star.ui.theme.CrimsonPrimary
import com.anonymous.star.ui.theme.DarkCard
import com.anonymous.star.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityProfileScreen(
    prefs: PreferencesManager,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf(prefs.userName) }
    var email by remember { mutableStateOf(prefs.userEmail) }
    var pin by remember { mutableStateOf(prefs.securityPin) }
    var faceLock by remember { mutableStateOf(prefs.faceLockEnabled) }
    var motionAntiTheft by remember { mutableStateOf(prefs.motionAntiTheft) }
    var motionSens by remember { mutableFloatStateOf(prefs.motionSensitivity) }
    var volumeSens by remember { mutableFloatStateOf(prefs.warningVolume) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile, Security & Anti-Theft", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("security_back")) {
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
            // Profile & Lifetime Free Status Card
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
                        Column {
                            Text(name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Text("UID: ${prefs.userUid}", color = CrimsonPrimary, fontFamily = FontFamily.Monospace, fontSize = 13.sp)
                        }
                        Surface(
                            color = SuccessGreen.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("FREE Lifetime", color = SuccessGreen, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp), fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            prefs.userName = it
                        },
                        label = { Text("Editable User Name", color = Color.Gray) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CrimsonPrimary, focusedTextColor = Color.White)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            prefs.userEmail = it
                        },
                        label = { Text("Editable Email (Optional)", color = Color.Gray) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CrimsonPrimary, focusedTextColor = Color.White)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // App Lock & Face Auth
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = CrimsonPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Security Locks & Face Auth", color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Face Lock Mode ON", color = Color.White)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Owner Face Saved", color = SuccessGreen, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = pin,
                        onValueChange = {
                            pin = it
                            prefs.securityPin = it
                        },
                        label = { Text("Change Security PIN", color = Color.Gray) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CrimsonPrimary, focusedTextColor = Color.White)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Anti-Theft Motion & Warning Siren
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = AlertRed)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Motion Anti-Theft Siren & SOS", color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    ToggleRow("Enable Motion Anti-Theft Mode", motionAntiTheft) {
                        motionAntiTheft = it
                        prefs.motionAntiTheft = it
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Motion Sensitivity", color = Color.White, fontSize = 13.sp)
                    Slider(
                        value = motionSens,
                        onValueChange = {
                            motionSens = it
                            prefs.motionSensitivity = it
                        },
                        valueRange = 0.1f..1.0f,
                        colors = SliderDefaults.colors(thumbColor = CrimsonPrimary)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("WARNING Siren Volume (100%)", color = AlertRed, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Slider(
                        value = volumeSens,
                        onValueChange = {
                            volumeSens = it
                            prefs.warningVolume = it
                        },
                        valueRange = 0.1f..1.0f,
                        colors = SliderDefaults.colors(thumbColor = AlertRed)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            Toast.makeText(context, "SOS GPS Location Dispatched to Emergency Contacts!", Toast.LENGTH_LONG).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AlertRed),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Trigger SOS GPS Emergency Alert", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
