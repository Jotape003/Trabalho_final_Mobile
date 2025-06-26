package com.example.edumi.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.edumi.models.Evento
import java.time.LocalDateTime
import java.time.ZoneId
import android.os.Build
import android.provider.Settings
import android.net.Uri

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
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if ((context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).canScheduleExactAlarms()) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTimeMillis,
                pendingIntent
            )
        } else {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    } else {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTimeMillis,
            pendingIntent
        )
    }
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
