package com.anonymous.star.data

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import java.util.UUID

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("anonymous_star_secure_vault", Context.MODE_PRIVATE)

    var userName: String
        get() = prefs.getString("user_name", "Vikash") ?: "Vikash"
        set(value) = prefs.edit().putString("user_name", value).apply()

    var userEmail: String
        get() = prefs.getString("user_email", "") ?: ""
        set(value) = prefs.edit().putString("user_email", value).apply()

    var userUid: String
        get() {
            var uid = prefs.getString("user_uid", null)
            if (uid == null) {
                uid = "UID-ASTAR-${UUID.randomUUID().toString().substring(0, 6).uppercase()}"
                prefs.edit().putString("user_uid", uid).apply()
            }
            return uid
        }
        set(value) = prefs.edit().putString("user_uid", value).apply()

    var currentWallpaperIndex: Int
        get() = prefs.getInt("wallpaper_index", 0)
        set(value) = prefs.edit().putInt("wallpaper_index", value).apply()

    var orbSizeScale: Float
        get() = prefs.getFloat("orb_size_scale", 1.0f)
        set(value) = prefs.edit().putFloat("orb_size_scale", value).apply()

    var orbHueShift: Float
        get() = prefs.getFloat("orb_hue_shift", 0f)
        set(value) = prefs.edit().putFloat("orb_hue_shift", value).apply()

    var orbMode: String
        get() = prefs.getString("orb_mode", "Energy") ?: "Energy"
        set(value) = prefs.edit().putString("orb_mode", value).apply()

    var systemPresence: Boolean
        get() = prefs.getBoolean("system_presence", true)
        set(value) = prefs.edit().putBoolean("system_presence", value).apply()

    var auraBorder: Boolean
        get() = prefs.getBoolean("aura_border", true)
        set(value) = prefs.edit().putBoolean("aura_border", value).apply()

    var hapticFeedback: Boolean
        get() = prefs.getBoolean("haptic_feedback", true)
        set(value) = prefs.edit().putBoolean("haptic_feedback", value).apply()

    var themeName: String
        get() = prefs.getString("theme_name", "Crimson Core") ?: "Crimson Core"
        set(value) = prefs.edit().putString("theme_name", value).apply()

    var audioMode: String
        get() = prefs.getString("audio_mode", "Gemini 3.8 Live") ?: "Gemini 3.8 Live"
        set(value) = prefs.edit().putString("audio_mode", value).apply()

    var audioCredits: Int
        get() = prefs.getInt("audio_credits", 10000)
        set(value) = prefs.edit().putInt("audio_credits", value).apply()

    var personality: String
        get() = prefs.getString("personality", "Cyber Sweet") ?: "Cyber Sweet"
        set(value) = prefs.edit().putString("personality", value).apply()

    var wakeWord: String
        get() = prefs.getString("wake_word", "ANONYMOUS STAR") ?: "ANONYMOUS STAR"
        set(value) = prefs.edit().putString("wake_word", value).apply()

    var picovoiceEnabled: Boolean
        get() = prefs.getBoolean("picovoice_enabled", true)
        set(value) = prefs.edit().putBoolean("picovoice_enabled", value).apply()

    var offlineWakeWordBeta: Boolean
        get() = prefs.getBoolean("offline_wakeword_beta", true)
        set(value) = prefs.edit().putBoolean("offline_wakeword_beta", value).apply()

    var heyGoogleVariant: Boolean
        get() = prefs.getBoolean("hey_google_variant", false)
        set(value) = prefs.edit().putBoolean("hey_google_variant", value).apply()

    var auraSignature: Boolean
        get() = prefs.getBoolean("aura_signature", true)
        set(value) = prefs.edit().putBoolean("aura_signature", true).apply()

    var auraCore: Boolean
        get() = prefs.getBoolean("aura_core", true)
        set(value) = prefs.edit().putBoolean("aura_core", value).apply()

    var paraLockMode: Boolean
        get() = prefs.getBoolean("para_lock_mode", true)
        set(value) = prefs.edit().putBoolean("para_lock_mode", value).apply()

    var stayUnlockedMinutes: Int
        get() = prefs.getInt("stay_unlocked_minutes", 15)
        set(value) = prefs.edit().putInt("stay_unlocked_minutes", value).apply()

    var voiceAlertDefault: Boolean
        get() = prefs.getBoolean("voice_alert_default", true)
        set(value) = prefs.edit().putBoolean("voice_alert_default", value).apply()

    var voiceMatchEnabled: Boolean
        get() = prefs.getBoolean("voice_match_enabled", true)
        set(value) = prefs.edit().putBoolean("voice_match_enabled", value).apply()

    var vibrationAlert: Boolean
        get() = prefs.getBoolean("vibration_alert", true)
        set(value) = prefs.edit().putBoolean("vibration_alert", value).apply()

    var sirenAlarm: Boolean
        get() = prefs.getBoolean("siren_alarm", true)
        set(value) = prefs.edit().putBoolean("siren_alarm", value).apply()

    var fullScreenWarning: Boolean
        get() = prefs.getBoolean("full_screen_warning", true)
        set(value) = prefs.edit().putBoolean("full_screen_warning", value).apply()

    var floatLockMode: Boolean
        get() = prefs.getBoolean("float_lock_mode", true)
        set(value) = prefs.edit().putBoolean("float_lock_mode", value).apply()

    var pitch: Float
        get() = prefs.getFloat("voice_pitch", 1.0f)
        set(value) = prefs.edit().putFloat("voice_pitch", value).apply()

    var vadSensitivity: Float
        get() = prefs.getFloat("vad_sensitivity", 0.8f)
        set(value) = prefs.edit().putFloat("vad_sensitivity", value).apply()

    var groqApiKey: String
        get() = prefs.getString("groq_api_key", "") ?: ""
        set(value) = prefs.edit().putString("groq_api_key", value).apply()

    var geminiApiKey: String
        get() = prefs.getString("gemini_api_key", "") ?: ""
        set(value) = prefs.edit().putString("gemini_api_key", value).apply()

    var deepseekApiKey: String
        get() = prefs.getString("deepseek_api_key", "") ?: ""
        set(value) = prefs.edit().putString("deepseek_api_key", value).apply()

    var openrouterApiKey: String
        get() = prefs.getString("openrouter_api_key", "") ?: ""
        set(value) = prefs.edit().putString("openrouter_api_key", value).apply()

    var tavilyApiKey: String
        get() = prefs.getString("tavily_api_key", "") ?: ""
        set(value) = prefs.edit().putString("tavily_api_key", value).apply()

    var customApiUrl: String
        get() = prefs.getString("custom_api_url", "https://api.tavily.com/search") ?: "https://api.tavily.com/search"
        set(value) = prefs.edit().putString("custom_api_url", value).apply()

    var primaryLlmModel: String
        get() = prefs.getString("primary_llm_model", "Gemini Best Quality") ?: "Gemini Best Quality"
        set(value) = prefs.edit().putString("primary_llm_model", value).apply()

    var securityPin: String
        get() = prefs.getString("security_pin", "1234") ?: "1234"
        set(value) = prefs.edit().putString("security_pin", value).apply()

    var faceLockEnabled: Boolean
        get() = prefs.getBoolean("face_lock_enabled", true)
        set(value) = prefs.edit().putBoolean("face_lock_enabled", value).apply()

    var motionAntiTheft: Boolean
        get() = prefs.getBoolean("motion_anti_theft", false)
        set(value) = prefs.edit().putBoolean("motion_anti_theft", value).apply()

    var motionSensitivity: Float
        get() = prefs.getFloat("motion_sensitivity", 0.7f)
        set(value) = prefs.edit().putFloat("motion_sensitivity", value).apply()

    var warningVolume: Float
        get() = prefs.getFloat("warning_volume", 1.0f)
        set(value) = prefs.edit().putFloat("warning_volume", value).apply()

    var chargingTriggerEnabled: Boolean
        get() = prefs.getBoolean("charging_trigger", true)
        set(value) = prefs.edit().putBoolean("charging_trigger", value).apply()

    var batteryTriggerEnabled: Boolean
        get() = prefs.getBoolean("battery_trigger", true)
        set(value) = prefs.edit().putBoolean("battery_trigger", value).apply()

    var screenUnlockTriggerEnabled: Boolean
        get() = prefs.getBoolean("screen_unlock_trigger", true)
        set(value) = prefs.edit().putBoolean("screen_unlock_trigger", value).apply()

    var autoReplyNotifications: Boolean
        get() = prefs.getBoolean("auto_reply_notifications", true)
        set(value) = prefs.edit().putBoolean("auto_reply_notifications", value).apply()

    var speakCallerName: Boolean
        get() = prefs.getBoolean("speak_caller_name", true)
        set(value) = prefs.edit().putBoolean("speak_caller_name", value).apply()

    var isFirstLaunch: Boolean
        get() = prefs.getBoolean("is_first_launch", true)
        set(value) = prefs.edit().putBoolean("is_first_launch", value).apply()

    fun hasAnyApiKey(): Boolean {
        return groqApiKey.isNotBlank() ||
               geminiApiKey.isNotBlank() ||
               deepseekApiKey.isNotBlank() ||
               openrouterApiKey.isNotBlank() ||
               tavilyApiKey.isNotBlank()
    }
}
