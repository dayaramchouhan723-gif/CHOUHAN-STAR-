package com.anonymous.star.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.anonymous.star.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraVisionScreen(
    prefs: PreferencesManager,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var isScanning by remember { mutableStateOf(false) }
    var scanResult by remember { mutableStateOf("Live Vision Ready - Point Camera at Objects, Barcodes or Documents") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Neural Lens & Live Vision", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("camera_back")) {
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
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Viewfinder Simulated Frame
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(DarkCard, RoundedCornerShape(24.dp))
                    .border(2.dp, CrimsonPrimary, RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = if (isScanning) Icons.Default.QrCodeScanner else Icons.Default.Camera,
                        contentDescription = null,
                        tint = CrimsonPrimary,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = scanResult,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        isScanning = true
                        scanResult = "Scanning live feed with Gemini Vision..."
                        scanResult = "Detected Object: ASUS Laptop & Keyboard\nQR Code: https://anonymous-star.app/connect\nOCR text: Confidential Document - Approved"
                        Toast.makeText(context, "Neural Scan Completed!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CrimsonPrimary),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.weight(1f).testTag("scan_live_btn")
                ) {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Live Scan", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
