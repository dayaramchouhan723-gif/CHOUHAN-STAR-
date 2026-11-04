package com.anonymous.star.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anonymous.star.data.PreferencesManager
import com.anonymous.star.ui.theme.BlackBackground
import com.anonymous.star.ui.theme.CrimsonPrimary
import com.anonymous.star.ui.theme.DarkCard
import com.anonymous.star.ui.theme.DarkSurface

data class ToolItem(val name: String, val icon: ImageVector, val route: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsScreen(
    prefs: PreferencesManager,
    onNavigateTo: (String) -> Unit
) {
    val context = LocalContext.current
    var showYoutubeMini by remember { mutableStateOf(false) }

    val toolsList = listOf(
        ToolItem("Voice Mode", Icons.Default.Mic, "voice_settings"),
        ToolItem("Neural Lens", Icons.Default.CameraAlt, "camera"),
        ToolItem("AMOLED Map", Icons.Default.Map, "map"),
        ToolItem("Aura Control", Icons.Default.Tune, "orb_settings"),
        ToolItem("Quick Directives", Icons.Default.FlashOn, "directives"),
        ToolItem("English Teacher", Icons.Default.School, "english_ai"),
        ToolItem("Deep Research", Icons.Default.TravelExplore, "research"),
        ToolItem("Image Search", Icons.Default.ImageSearch, "image_search")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Apps 8 Tools & Connectors", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { onNavigateTo("home") }, modifier = Modifier.testTag("tools_back")) {
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
            Text("8 Core Tools", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(12.dp))

            // Grid of 8 Tools
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                toolsList.chunked(2).forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        row.forEach { tool ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = DarkCard),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(90.dp)
                                    .clickable { onNavigateTo(tool.route) }
                                    .testTag("tool_${tool.name.lowercase().replace(" ", "_")}")
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize().padding(12.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(tool.icon, contentDescription = tool.name, tint = CrimsonPrimary, modifier = Modifier.size(28.dp))
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(tool.name, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // App Action Trigger Cards
            Text("App Connectors & Automated Actions", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(12.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // YouTube
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.PlayCircle, contentDescription = "YouTube", tint = Color.Red)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("YouTube & YouTube MINI", color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                        Button(
                            onClick = {
                                showYoutubeMini = !showYoutubeMini
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=ql91-ZaMZdM"))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CrimsonPrimary)
                        ) {
                            Text("Open Song")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(color = BlackBackground)
                    Spacer(modifier = Modifier.height(12.dp))

                    // WhatsApp
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Send, contentDescription = "WhatsApp", tint = Color(0xFF25D366))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("WhatsApp Send By Name", color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=&text=Hello%20from%20Anonymous%20Star!"))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366))
                        ) {
                            Text("Send Msg", color = Color.Black)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(color = BlackBackground)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Facebook & Telegram
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Share, contentDescription = "Social", tint = Color(0xFF1877F2))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Facebook & Telegram", color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                        Button(
                            onClick = {
                                Toast.makeText(context, "Telegram Community Watch setup ready!", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1877F2))
                        ) {
                            Text("Connect")
                        }
                    }
                }
            }
        }
    }
}
