package online.temer.dontwakemywife

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun scheduleAlarm(context: Context, hours: Int, minutes: Int) {
    val time = getNextOccurrence(hours, minutes)
    scheduleAlarm(context, time)
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun scheduleAlarm(context: Context, time: Calendar) {
    val mainActivityIntent = createPendingIntent(
        context,
        MainActivity::class.java
    )
    val alarmClockInfo = AlarmManager.AlarmClockInfo(
        time.timeInMillis,
        mainActivityIntent
    )

    val alarmIntent = createPendingIntent(
        context,
        AlarmBroadcastReceiver::class.java
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.setAlarmClock(alarmClockInfo, alarmIntent)
}

fun cancelAlarm(context: Context) {
    val alarmIntent = createPendingIntent(
        context,
        AlarmBroadcastReceiver::class.java
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.cancel(alarmIntent)
}

private fun getNextOccurrence(hours: Int, minutes: Int): Calendar {
    val alarm = GregorianCalendar()
    alarm.timeInMillis = System.currentTimeMillis()
    alarm.set(Calendar.HOUR_OF_DAY, hours)
    alarm.set(Calendar.MINUTE, minutes)
    alarm.set(Calendar.SECOND, 0)
    alarm.set(Calendar.MILLISECOND, 0)

    if (alarm.timeInMillis <= System.currentTimeMillis()) // The alarm already occurred today
        alarm.add(Calendar.DATE, 1) // schedule it for tomorrow

    return alarm
}

private fun createPendingIntent(context: Context, target: Class<*>): PendingIntent? {
    return PendingIntent.getBroadcast(
        context,
        0,
        Intent(context, target),
        PendingIntent.FLAG_CANCEL_CURRENT
    )
}