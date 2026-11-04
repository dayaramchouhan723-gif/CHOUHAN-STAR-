package com.anonymous.star.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anonymous.star.data.PreferencesManager
import com.anonymous.star.ui.components.PulsingOrb
import com.anonymous.star.ui.theme.BlackBackground
import com.anonymous.star.ui.theme.CrimsonPrimary
import com.anonymous.star.ui.theme.DarkCard
import com.anonymous.star.ui.theme.TextGray

@Composable
fun SplashScreen(
    prefs: PreferencesManager,
    onComplete: () -> Unit
) {
    var nameInput by remember { mutableStateOf(prefs.userName) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackBackground)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            PulsingOrb(
                baseSize = 200.dp,
                sizeScale = prefs.orbSizeScale,
                hueShift = prefs.orbHueShift,
                orbMode = prefs.orbMode
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Hello! How can I help",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Anonymous Star ⭐ V2.2.7 Cyber Assistant",
                style = MaterialTheme.typography.bodyMedium,
                color = CrimsonPrimary,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = nameInput,
                onValueChange = { nameInput = it },
                label = { Text("Enter Your Name", color = TextGray) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .testTag("name_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CrimsonPrimary,
                    unfocusedBorderColor = DarkCard,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Google Sign in Simulated Button
                Button(
                    onClick = {
                        prefs.userName = nameInput.ifEmpty { "Vikash" }
                        prefs.isFirstLaunch = false
                        onComplete()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CrimsonPrimary),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.testTag("google_login_btn")
                ) {
                    Text("Google Login", color = Color.White)
                }

                // Share Button
                IconButton(
                    onClick = {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Check out Anonymous Star ⭐ Cyber AI Assistant V2.2.7!")
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(sendIntent, null))
                    },
                    modifier = Modifier
                        .background(DarkCard, CircleShape)
                        .testTag("share_app_btn")
                ) {
                    Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
                }

                // Skip Button
                TextButton(
                    onClick = {
                        prefs.userName = nameInput.ifEmpty { "Vikash" }
                        prefs.isFirstLaunch = false
                        onComplete()
                    },
                    modifier = Modifier.testTag("skip_btn")
                ) {
                    Text("Skip", color = TextGray)
                    Icon(Icons.Default.ArrowForward, contentDescription = "Skip", tint = TextGray, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}
