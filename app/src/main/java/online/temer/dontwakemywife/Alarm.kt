package online.temer.dontwakemywife

import android.app.Activity
import android.os.*
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.annotation.RequiresApi

class Alarm : Activity() {

    var vibrator: Vibrator? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val flags = WindowManager.LayoutParams.FLAG_FULLSCREEN
            .or(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
            .or(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
            .or(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
            .or(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.setFlags(flags, flags)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_alarm)

        findViewById<Button>(R.id.buttonDismiss).setOnClickListener {
            finish()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            vibrate()
        }, 1000)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun vibrate() {
        val normal = VibrationEffect.DEFAULT_AMPLITUDE
        val full = 255

        val pattern = Pattern()
            .add(Pattern().burst(140, 200, normal, 2).wait(8000).repeat(2))
            .add(Pattern().burst(160, 200, normal, 2).wait(5000).repeat(2))
            .add(Pattern().burst(180, 200, normal, 2).wait(5000).repeat(2))
            .add(Pattern().burst(200, 200, normal, 2).wait(5000).repeat(2))
            .add(Pattern().burst(200, 200, normal, 4).wait(5000).repeat(2))
            .add(Pattern().burst(200, 200, full, 4).wait(2500).repeat(10))

        val shortPulse =
            VibrationEffect.createWaveform(pattern.getTimings(), pattern.getAmplitudes(), 0)
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

    class Pattern {
        val timings: MutableList<Long> = arrayListOf()
        val amplitudes: MutableList<Int> = arrayListOf()

        fun add(theOther: Pattern): Pattern {
            timings.addAll(theOther.timings)
            amplitudes.addAll(theOther.amplitudes)

            return this
        }

        fun burst(timeOn: Long, timeOff: Long, amplitude: Int, numberOfPulses: Int): Pattern {
            for (i in 0 until numberOfPulses) {
                if (i > 0) {
                    wait(timeOff)
                }

                timings.add(timeOn)
                amplitudes.add(amplitude)
            }

            return this
        }

        fun wait(timeOff: Long): Pattern {
            timings.add(timeOff)
            amplitudes.add(0)

            return this
        }

        fun repeat(times: Int): Pattern {
            val oldTimings = ArrayList<Long>(timings)
            val oldAmplitudes = ArrayList<Int>(amplitudes)

            for (i in 0 until times) {
                timings.addAll(oldTimings)
                amplitudes.addAll(oldAmplitudes)
            }

            return this
        }

        fun getTimings(): LongArray {
            return timings.toLongArray()
        }

        fun getAmplitudes(): IntArray {
            return amplitudes.toIntArray()
        }
    }
}