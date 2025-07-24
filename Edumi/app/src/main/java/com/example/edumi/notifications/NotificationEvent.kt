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
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun agendarNotificacaoEvento(context: Context, evento: Evento) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val localDate = LocalDate.parse(evento.data, formatter)
    val localTime = LocalTime.parse(evento.horaInicio)

    val triggerDateTime = LocalDateTime.of(
        localDate.minusDays(1),
        localTime
    ).atZone(ZoneId.systemDefault())

    val triggerTimeMillis = triggerDateTime.toInstant().toEpochMilli()

    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("TIPO", "EVENTO")
        putExtra("TITULO", evento.titulo)
        putExtra("ID_EVENTO", evento.id.hashCode())
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        evento.id.hashCode(),
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (alarmManager.canScheduleExactAlarms()) {
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
        evento.id.hashCode(),
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE
    )
    if (pendingIntent != null) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}
