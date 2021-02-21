package online.temer.dontwakemywife

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Button
import androidx.annotation.RequiresApi

class Alarm : AppCompatActivity() {

    var vibrator : Vibrator? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_alarm)

        findViewById<Button>(R.id.buttonDismiss).setOnClickListener {
            finish()
        }

        vibrate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun vibrate() {
        val amp = VibrationEffect.DEFAULT_AMPLITUDE
        val timings    = longArrayOf(100, 200, 100, 200, 100, 5000)
        val amplitudes =  intArrayOf(amp,   0, amp,   0, amp, 0)

        val shortPulse = VibrationEffect.createWaveform(timings, amplitudes, 0)
        vibrator = getSystemService<Vibrator>(Vibrator::class.java)
        vibrator?.vibrate(shortPulse)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        vibrator?.cancel()
        super.onSaveInstanceState(outState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroy() {
        vibrator?.cancel()
        super.onDestroy()
    }
}