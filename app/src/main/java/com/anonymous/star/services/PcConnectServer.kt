package com.anonymous.star.services

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

class PcConnectServer(private val context: Context, private val port: Int = 8080) {
    private var isRunning = false
    private var serverSocket: ServerSocket? = null

    fun start() {
        if (isRunning) return
        isRunning = true
        thread {
            try {
                serverSocket = ServerSocket(port)
                Log.d("PcConnectServer", "Server listening on port $port")
                while (isRunning) {
                    val clientSocket = serverSocket?.accept() ?: break
                    handleClient(clientSocket)
                }
            } catch (e: Exception) {
                Log.e("PcConnectServer", "Error running server", e)
            }
        }
    }

    private fun handleClient(socket: Socket) {
        thread {
            try {
                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                val writer = PrintWriter(socket.getOutputStream(), true)
                val line = reader.readLine()

                val jsonResponse = """
                    {
                        "status": "connected",
                        "app": "Anonymous Star ⭐ V2.2.7",
                        "uid": "UID-ASTAR-ONLINE",
                        "message": "PC Companion Connection Established"
                    }
                """.trimIndent()

                writer.println("HTTP/1.1 200 OK")
                writer.println("Content-Type: application/json")
                writer.println("Content-Length: ${jsonResponse.length}")
                writer.println("Access-Control-Allow-Origin: *")
                writer.println()
                writer.println(jsonResponse)

                socket.close()
            } catch (e: Exception) {
                Log.e("PcConnectServer", "Error handling client socket", e)
            }
        }
    }

    fun stop() {
        isRunning = false
        try {
            serverSocket?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
