package com.anonymous.star

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.anonymous.star.data.MemoryManager
import com.anonymous.star.data.PreferencesManager
import com.anonymous.star.services.PcConnectServer
import com.anonymous.star.ui.screens.*
import com.anonymous.star.ui.theme.AnonymousStarTheme

class MainActivity : ComponentActivity() {

    private lateinit var prefs: PreferencesManager
    private lateinit var memoryManager: MemoryManager
    private var pcServer: PcConnectServer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        prefs = PreferencesManager(this)
        memoryManager = MemoryManager(this)

        pcServer = PcConnectServer(this, 8080)
        pcServer?.start()

        setContent {
            var currentScreen by remember {
                mutableStateOf(if (prefs.isFirstLaunch) "splash" else "home")
            }

            AnonymousStarTheme(themeName = prefs.themeName) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (currentScreen) {
                        "splash" -> SplashScreen(prefs = prefs, onComplete = { currentScreen = "home" })
                        "home" -> HomeScreen(prefs = prefs, onNavigateTo = { currentScreen = it })
                        "orb_settings" -> {
                            BackHandler { currentScreen = "home" }
                            OrbSettingsScreen(prefs = prefs, onBack = { currentScreen = "home" })
                        }
                        "voice_settings" -> {
                            BackHandler { currentScreen = "home" }
                            VoiceSettingsScreen(prefs = prefs, onBack = { currentScreen = "home" })
                        }
                        "api_vault" -> {
                            BackHandler { currentScreen = "home" }
                            ApiVaultScreen(prefs = prefs, onBack = { currentScreen = "home" })
                        }
                        "tools" -> {
                            BackHandler { currentScreen = "home" }
                            ToolsScreen(prefs = prefs, onNavigateTo = { currentScreen = it })
                        }
                        "triggers" -> {
                            BackHandler { currentScreen = "home" }
                            TriggersScreen(prefs = prefs, onBack = { currentScreen = "home" })
                        }
                        "pc_connect" -> {
                            BackHandler { currentScreen = "home" }
                            PcConnectScreen(prefs = prefs, onBack = { currentScreen = "home" })
                        }
                        "camera" -> {
                            BackHandler { currentScreen = "home" }
                            CameraVisionScreen(prefs = prefs, onBack = { currentScreen = "home" })
                        }
                        "memory" -> {
                            BackHandler { currentScreen = "home" }
                            MemoryScreen(memoryManager = memoryManager, onBack = { currentScreen = "home" })
                        }
                        "security" -> {
                            BackHandler { currentScreen = "home" }
                            SecurityProfileScreen(prefs = prefs, onBack = { currentScreen = "home" })
                        }
                        else -> {
                            BackHandler { currentScreen = "home" }
                            HomeScreen(prefs = prefs, onNavigateTo = { currentScreen = it })
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pcServer?.stop()
    }
}
