package com.example.edumi.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.edumi.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        val titulo = intent?.getStringExtra("TITULO") ?: "Evento: "
        val id = intent?.getIntExtra("ID_EVENTO", 0) ?: 0

        val notification = NotificationCompat.Builder(context, "EVENTOS_CHANNEL")
            .setSmallIcon(R.drawable.responsavel)
            .setContentTitle("Lembrete de Evento!")
            .setContentText(titulo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(context).notify(id, notification)
    }
}