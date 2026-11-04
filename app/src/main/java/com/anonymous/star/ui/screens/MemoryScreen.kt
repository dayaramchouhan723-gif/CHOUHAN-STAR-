package com.anonymous.star.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anonymous.star.data.MemoryItem
import com.anonymous.star.data.MemoryManager
import com.anonymous.star.ui.theme.BlackBackground
import com.anonymous.star.ui.theme.CrimsonPrimary
import com.anonymous.star.ui.theme.DarkCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoryScreen(
    memoryManager: MemoryManager,
    onBack: () -> Unit
) {
    val memories = remember { memoryManager.getMemories() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Memory Vault (${memories.size} Items)", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("memory_back")) {
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
                .padding(horizontal = 20.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(memories) { item ->
                    MemoryCard(item)
                }
            }
        }
    }
}

@Composable
fun MemoryCard(item: MemoryItem) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkCard),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(item.title, color = CrimsonPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(item.timestamp, color = Color.Gray, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(item.content, color = Color.White, fontSize = 13.sp)
        }
    }
}
