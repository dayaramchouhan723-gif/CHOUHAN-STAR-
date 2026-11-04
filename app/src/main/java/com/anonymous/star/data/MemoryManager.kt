package com.anonymous.star.data

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

data class MemoryItem(
    val id: String,
    val title: String,
    val content: String,
    val timestamp: String,
    val category: String = "Chat Memory"
)

data class ChatMessage(
    val id: String,
    val sender: String, // "user" or "assistant"
    val message: String,
    val timestamp: String
)

class MemoryManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("anonymous_star_memories", Context.MODE_PRIVATE)
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val memoryListType = Types.newParameterizedType(List::class.java, MemoryItem::class.java)
    private val memoryAdapter = moshi.adapter<List<MemoryItem>>(memoryListType)

    private val chatListType = Types.newParameterizedType(List::class.java, ChatMessage::class.java)
    private val chatAdapter = moshi.adapter<List<ChatMessage>>(chatListType)

    fun getMemories(): List<MemoryItem> {
        val json = prefs.getString("memory_items", null)
        if (json.isNullOrEmpty()) {
            val initialList = createInitialMemories()
            saveMemories(initialList)
            return initialList
        }
        return try {
            memoryAdapter.fromJson(json) ?: createInitialMemories()
        } catch (e: Exception) {
            createInitialMemories()
        }
    }

    fun saveMemories(list: List<MemoryItem>) {
        val json = memoryAdapter.toJson(list)
        prefs.edit().putString("memory_items", json).apply()
    }

    fun addMemory(title: String, content: String, category: String = "User Preference") {
        val current = getMemories().toMutableList()
        current.add(
            0,
            MemoryItem(
                id = System.currentTimeMillis().toString(),
                title = title,
                content = content,
                timestamp = "05 Sep 2026",
                category = category
            )
        )
        saveMemories(current)
    }

    fun getChatMessages(): List<ChatMessage> {
        val json = prefs.getString("chat_messages", null)
        if (json.isNullOrEmpty()) {
            val initialChats = listOf(
                ChatMessage("1", "user", "Arre, tuh... Anonymous Star AI open kar", "10:14 AM"),
                ChatMessage("2", "assistant", "Hello Vikash! Anonymous Star ready hai. Main YouTube aur PC WhatsApp connect kar rahi hu...", "10:15 AM"),
                ChatMessage("3", "assistant", "One sec... YouTube khul gaya... YouTube MINI preview activate ho gaya hai!", "10:16 AM"),
                ChatMessage("4", "user", "PC mein WhatsApp khol...", "10:17 AM"),
                ChatMessage("5", "assistant", "PC Companion active at 192.168.1.45:8080! WhatsApp command dispatched smoothly, boss!", "10:18 AM")
            )
            saveChatMessages(initialChats)
            return initialChats
        }
        return try {
            chatAdapter.fromJson(json) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun saveChatMessages(messages: List<ChatMessage>) {
        val json = chatAdapter.toJson(messages)
        prefs.edit().putString("chat_messages", json).apply()
    }

    fun addChatMessage(sender: String, text: String) {
        val current = getChatMessages().toMutableList()
        current.add(
            ChatMessage(
                id = System.currentTimeMillis().toString(),
                sender = sender,
                message = text,
                timestamp = "Just now"
            )
        )
        saveChatMessages(current)
    }

    private fun createInitialMemories(): List<MemoryItem> {
        val list = mutableListOf<MemoryItem>()
        list.add(MemoryItem("m1", "User Preference", "User name is set to Vikash. Preferred language Hinglish.", "05 Sep 2026", "Identity"))
        list.add(MemoryItem("m2", "App Theme Preference", "Favorite theme is Crimson Core with Neon Red pulsing orb.", "05 Sep 2026", "Preferences"))
        list.add(MemoryItem("m3", "Voice Mode", "Audio engine configured to Gemini 3.8 Live with 10,000 credit limit.", "05 Sep 2026", "Voice"))
        list.add(MemoryItem("m4", "PC Companion Node", "Connected PC IP 192.168.1.45 running Anonymous Star Companion v2.2.7", "05 Sep 2026", "Network"))
        list.add(MemoryItem("m5", "Personality Mirror", "Cyber Sweet / Sweet Rude dynamic response curve enabled.", "05 Sep 2026", "AI Core"))

        for (i in 6..113) {
            list.add(
                MemoryItem(
                    id = "m$i",
                    title = "System Memory Log #$i",
                    content = "Verified secure encrypted memory cluster node $i. Lifetime status active.",
                    timestamp = "05 Sep 2026",
                    category = if (i % 3 == 0) "Automations" else if (i % 2 == 0) "Voice Logs" else "Security"
                )
            )
        }
        return list
    }
}
