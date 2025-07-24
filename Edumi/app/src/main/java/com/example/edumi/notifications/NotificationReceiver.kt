package com.example.edumi.notifications

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.edumi.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val tipo = intent?.getStringExtra("TIPO") ?: return
        val titulo = intent?.getStringExtra("TITULO") ?: ""
        val id = intent?.getIntExtra("ID_EVENTO", 0) ?: 0

        val tituloDaNotificacao = when (tipo) {
            "EVENTO" -> "Lembrete de Evento!"
            "COMUNICADO" -> "Novo Comunicado!"
            else -> "Aviso Edumi"
        }

        // 3. Constrói a notificação
        val notification = NotificationCompat.Builder(context, "EVENTOS_CHANNEL")
            .setSmallIcon(R.drawable.edumi_icon) // Use um ícone branco e transparente
            .setContentTitle(tituloDaNotificacao)
            .setContentText(titulo) // O 'titulo' do evento/comunicado vai aqui
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        // 4. Exibe a notificação (com verificação de permissão)
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(context).notify(id, notification)
        }
    }
}