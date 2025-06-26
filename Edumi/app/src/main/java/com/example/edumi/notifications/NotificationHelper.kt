package com.example.edumi.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.edumi.models.Evento
import java.time.LocalDateTime
import java.time.ZoneId

fun agendarNotificacaoEvento(context: Context, evento: Evento) {
    val zonedDateTime = LocalDateTime.of(
        evento.data,
        evento.horaInicio.minusMinutes(1)
    ).atZone(ZoneId.systemDefault())


    val triggerTimeMillis = zonedDateTime.toInstant().toEpochMilli()

    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("TITULO", evento.titulo)
        putExtra("ID_EVENTO", evento.idFilho)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        evento.idFilho,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        triggerTimeMillis,
        pendingIntent
    )
}

fun cancelarNotificacaoEvento(context: Context, evento: Evento) {
    val intent = Intent(context, NotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        evento.idFilho,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE
    )
    if (pendingIntent != null) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}
