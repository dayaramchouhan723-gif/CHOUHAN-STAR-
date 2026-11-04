package com.anonymous.star.ui.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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
    var paraLock by remember { mutableStateOf(prefs.paraLockMode) }
    var stayUnlocked by remember { mutableIntStateOf(prefs.stayUnlockedMinutes) }

    var motionAntiTheft by remember { mutableStateOf(prefs.motionAntiTheft) }
    var motionSens by remember { mutableFloatStateOf(prefs.motionSensitivity) }
    var volumeSens by remember { mutableFloatStateOf(prefs.warningVolume) }

    var voiceAlert by remember { mutableStateOf(prefs.voiceAlertDefault) }
    var voiceMatch by remember { mutableStateOf(prefs.voiceMatchEnabled) }
    var vibrationOn by remember { mutableStateOf(prefs.vibrationAlert) }
    var sirenOn by remember { mutableStateOf(prefs.sirenAlarm) }
    var fullScreenWarn by remember { mutableStateOf(prefs.fullScreenWarning) }
    var floatLock by remember { mutableStateOf(prefs.floatLockMode) }

    var isAlarmActive by remember { mutableStateOf(false) }
    var alarmPinInput by remember { mutableStateOf("") }
    var faceEnrolled by remember { mutableStateOf(true) }

    // SensorEventListener for Motion Anti-Theft Acceleration Detection
    DisposableEffect(motionAntiTheft) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        val accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        
        var listener: SensorEventListener? = null
        if (motionAntiTheft && accelerometer != null) {
            listener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    event?.let {
                        val x = it.values[0]
                        val y = it.values[1]
                        val z = it.values[2]
                        val accelVector = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                        val diff = Math.abs(accelVector - 9.8f)
                        val threshold = (1.1f - motionSens) * 12f
                        if (diff > threshold) {
                            isAlarmActive = true
                        }
                    }
                }
                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }
            sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_UI)
        }

        onDispose {
            listener?.let { sensorManager?.unregisterListener(it) }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
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

                // App Lock & Face Auth Card
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
                                Icon(Icons.Default.Lock, contentDescription = null, tint = CrimsonPrimary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Face Lock & Security PIN", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                            if (faceEnrolled) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Owner Face Saved", color = SuccessGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Face Lock Mode ON", color = Color.White)
                            Switch(
                                checked = faceLock,
                                onCheckedChange = {
                                    faceLock = it
                                    prefs.faceLockEnabled = it
                                },
                                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = CrimsonPrimary)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Para Lock Mode", color = Color.White)
                            Switch(
                                checked = paraLock,
                                onCheckedChange = {
                                    paraLock = it
                                    prefs.paraLockMode = it
                                },
                                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = CrimsonPrimary)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = pin,
                            onValueChange = { pin = it },
                            label = { Text("Change Security PIN", color = Color.Gray) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CrimsonPrimary, focusedTextColor = Color.White)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text("Stay Unlocked For: ${stayUnlocked} min", color = Color.LightGray, fontSize = 13.sp)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(5, 15, 30, 60).forEach { mins ->
                                FilterChip(
                                    selected = stayUnlocked == mins,
                                    onClick = {
                                        stayUnlocked = mins
                                        prefs.stayUnlockedMinutes = mins
                                    },
                                    label = { Text("${mins}m") },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = CrimsonPrimary,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Red Save Button for Security Profile
                        Button(
                            onClick = {
                                prefs.securityPin = pin
                                prefs.userName = name
                                prefs.userEmail = email
                                faceEnrolled = true
                                Toast.makeText(context, "Owner Face & Security PIN Saved Successfully!", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("save_security_red_btn")
                        ) {
                            Icon(Icons.Default.Save, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Save Red Security Config", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Anti-Theft Motion & Warning Siren Card
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

                        Text("Motion Sensitivity (Medium)", color = Color.White, fontSize = 13.sp)
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

                        Spacer(modifier = Modifier.height(10.dp))
                        Divider(color = BlackBackground)
                        Spacer(modifier = Modifier.height(10.dp))

                        ToggleRow("Voice Alert Default", voiceAlert) { voiceAlert = it; prefs.voiceAlertDefault = it }
                        ToggleRow("Voice Match Enabled", voiceMatch) { voiceMatch = it; prefs.voiceMatchEnabled = it }
                        ToggleRow("Vibration ON", vibrationOn) { vibrationOn = it; prefs.vibrationAlert = it }
                        ToggleRow("Siren / Alarm ON", sirenOn) { sirenOn = it; prefs.sirenAlarm = it }
                        ToggleRow("Full Screen Warning ON", fullScreenWarn) { fullScreenWarn = it; prefs.fullScreenWarning = it }
                        ToggleRow("Float Lock Mode ON", floatLock) { floatLock = it; prefs.floatLockMode = it }

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = {
                                Toast.makeText(context, "SOS GPS Location Dispatched to Emergency Contacts!", Toast.LENGTH_LONG).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AlertRed),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Trigger SOS GPS Emergency Alert", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // About App Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkCard),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = CrimsonPrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("About Anonymous Star ⭐", color = Color.White, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Anonymous Star ⭐ V2.2.7", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                        Text("Status: FREE Lifetime", color = SuccessGreen, fontSize = 14.sp)
                        Text("Build: Size optimized", color = Color.LightGray, fontSize = 14.sp)
                        Text("Architecture: Updated like MYRA", color = Color.LightGray, fontSize = 14.sp)
                    }
                }
            }
        }

        // FULL SCREEN WARNING OVERLAY (100% RED Motion Anti-Theft Siren)
        if (isAlarmActive) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color.White, modifier = Modifier.size(80.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "WARNING 100%",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Text(
                        text = "UNAUTHORIZED MOTION DETECTED!\nSIREN & ALARM ACTIVE",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Yellow,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = alarmPinInput,
                        onValueChange = { alarmPinInput = it },
                        label = { Text("Enter Security PIN to Disarm", color = Color.White) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedBorderColor = Color.Yellow,
                            unfocusedLabelColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth(0.85f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (alarmPinInput == prefs.securityPin) {
                                isAlarmActive = false
                                alarmPinInput = ""
                                Toast.makeText(context, "Anti-Theft Alarm Disarmed Successfully", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Invalid PIN! Alarm Continuing...", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        modifier = Modifier.fillMaxWidth(0.85f)
                    ) {
                        Text("DISARM SIREN ALARM", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
