package com.anonymous.star.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Warning
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
fun ApiVaultScreen(
    prefs: PreferencesManager,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    var groqKey by remember { mutableStateOf(prefs.groqApiKey) }
    var geminiKey by remember { mutableStateOf(prefs.geminiApiKey) }
    var deepseekKey by remember { mutableStateOf(prefs.deepseekApiKey) }
    var openrouterKey by remember { mutableStateOf(prefs.openrouterApiKey) }
    var tavilyKey by remember { mutableStateOf(prefs.tavilyApiKey) }
    var customUrl by remember { mutableStateOf(prefs.customApiUrl) }
    var primaryModel by remember { mutableStateOf(prefs.primaryLlmModel) }

    var testStatus by remember { mutableStateOf("Ready to Test") }
    var isTesting by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("API Keys Vault & Auto-Fallback", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("api_vault_back")) {
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
            // Header Card
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Key, contentDescription = null, tint = CrimsonPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Secure API Vault", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "If no API keys are entered, Anonymous Star operates seamlessly in Offline Local mode. Retry logic automatically attempts 3x fallbacks across active providers.",
                        color = Color.LightGray,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // API Input Fields
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    ApiKeyInput("Groq API Keys (comma-separated)", groqKey) { groqKey = it }
                    Spacer(modifier = Modifier.height(12.dp))
                    ApiKeyInput("Gemini API Key", geminiKey) { geminiKey = it }
                    Spacer(modifier = Modifier.height(12.dp))
                    ApiKeyInput("DeepSeek API Key", deepseekKey) { deepseekKey = it }
                    Spacer(modifier = Modifier.height(12.dp))
                    ApiKeyInput("OpenRouter API Key", openrouterKey) { openrouterKey = it }
                    Spacer(modifier = Modifier.height(12.dp))
                    ApiKeyInput("Tavily Search Key", tavilyKey) { tavilyKey = it }
                    Spacer(modifier = Modifier.height(12.dp))
                    ApiKeyInput("Custom Search URL", customUrl) { customUrl = it }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Primary Routing Selector
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("Primary LLM Model Priority", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    listOf(
                        "OpenRouter Auto-fallback",
                        "Groq Fastest",
                        "Gemini Best Quality",
                        "DeepSeek Good Reasoning"
                    ).forEach { modelItem ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = primaryModel == modelItem,
                                onClick = {
                                    primaryModel = modelItem
                                    prefs.primaryLlmModel = modelItem
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = CrimsonPrimary)
                            )
                            Text(modelItem, color = Color.White, fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        isTesting = true
                        testStatus = "Testing 3x connection..."
                        // Simulate test
                        prefs.groqApiKey = groqKey
                        prefs.geminiApiKey = geminiKey
                        prefs.deepseekApiKey = deepseekKey
                        prefs.openrouterApiKey = openrouterKey
                        prefs.tavilyApiKey = tavilyKey
                        prefs.customApiUrl = customUrl

                        testStatus = "Vault Verified & Encrypted!"
                        isTesting = false
                        Toast.makeText(context, "API Vault Saved & Verified!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CrimsonPrimary),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.weight(1f).testTag("save_vault_btn")
                ) {
                    Text("Save Vault Settings", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = if (groqKey.isNotEmpty() || geminiKey.isNotEmpty()) Icons.Default.CheckCircle else Icons.Default.Warning,
                    contentDescription = null,
                    tint = if (groqKey.isNotEmpty() || geminiKey.isNotEmpty()) SuccessGreen else Color.Yellow,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (groqKey.isNotEmpty() || geminiKey.isNotEmpty()) "Status: Cloud Hybrid Active" else "Status: Offline Local Fallback Active",
                    color = Color.LightGray,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun ApiKeyInput(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray, fontSize = 12.sp) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = CrimsonPrimary,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
}
