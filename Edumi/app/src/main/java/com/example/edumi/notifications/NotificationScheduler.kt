package com.example.edumi.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.edumi.models.Comunicado

fun agendarNotificacaoComunicado(context: Context, comunicado: Comunicado) {
    Log.d("ComunicadoDebug", "Função 'agendarNotificacaoComunicado' foi chamada para o comunicado: ${comunicado.tipo}")

    val triggerTimeMillis = System.currentTimeMillis() + 5000

    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("TITULO", "Novo Comunicado: ${comunicado.tipo}")
        putExtra("ID_EVENTO", comunicado.id.hashCode())
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        comunicado.id.hashCode(),
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (alarmManager.canScheduleExactAlarms()) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTimeMillis, pendingIntent)
        }
    } else {
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTimeMillis, pendingIntent)
    }
}

fun cancelarNotificacaoComunicado(context: Context, comunicado: Comunicado) {
    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("TIPO", "COMUNICADO")
        putExtra("TITULO", comunicado.tipo)
        putExtra("ID_EVENTO", comunicado.id.hashCode())
    }
        val pendingIntent = PendingIntent.getBroadcast(
        context,
        comunicado.id.hashCode(),
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE
    )

    if (pendingIntent != null) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }
}