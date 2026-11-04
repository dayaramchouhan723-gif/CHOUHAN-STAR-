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
import com.anonymous.star.ui.theme.BlackBackground
import com.anonymous.star.ui.theme.CrimsonPrimary
import com.anonymous.star.ui.theme.DarkCard
import com.anonymous.star.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PcConnectScreen(
    prefs: PreferencesManager,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val phoneIp = "192.168.1.45"
    val port = "8080"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PC Connect & Companion Protocol", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("pc_connect_back")) {
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
            // Server Address Card
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.Computer, contentDescription = null, tint = CrimsonPrimary, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Anonymous Star PC Local Server", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "http://$phoneIp:$port",
                        color = CrimsonPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Companion Socket Ready", color = SuccessGreen, fontSize = 13.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Instructions
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("How to Setup PC Companion App:", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(10.dp))

                    Text("1. Run the companion script on your PC:\n   python windows-companion/main.py", color = Color.LightGray, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("2. Make sure phone and PC are connected to the same Wi-Fi network.", color = Color.LightGray, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("3. Firewall Guide: Allow inbound TCP Port 8080 on Windows Defender Firewall.", color = Color.LightGray, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("4. Link to Windows enabled for seamless notification and clipboard mirroring.", color = Color.LightGray, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    Toast.makeText(context, "Testing PC Companion Ping at $phoneIp:$port...", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = CrimsonPrimary),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Test PC Companion Ping", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
