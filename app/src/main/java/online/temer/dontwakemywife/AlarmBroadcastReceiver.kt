package online.temer.dontwakemywife

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.widget.Toast

class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val powerManager = context!!.getSystemService(Context.POWER_SERVICE) as PowerManager

        val flags = PowerManager.FULL_WAKE_LOCK
            .or(PowerManager.ACQUIRE_CAUSES_WAKEUP)
            .or(PowerManager.ON_AFTER_RELEASE)

        val wakeLock =
            powerManager.newWakeLock(flags, "dont-wake-my-wife:alarm")
        wakeLock.acquire(30 * 1000L /* 30 seconds*/)

        Toast.makeText(context, "alarm!", Toast.LENGTH_LONG).show()

        val intent = Intent(context, Alarm::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)

        //wakeLock.release()
    }
}
