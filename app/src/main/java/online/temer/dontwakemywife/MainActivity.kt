package online.temer.dontwakemywife

import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    lateinit var preferences: Preferences

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = Preferences(getPreferences(Context.MODE_PRIVATE))

        initTimeButton(findViewById<Button>(R.id.buttonBedTime), preferences.loadBedTime()) {
            preferences.saveBedTime(it)
        }

        initTimeButton(findViewById<Button>(R.id.buttonWakeUpTime), preferences.loadWakeUpTime()) {
            preferences.saveWakeUpTime(it)
        }

        scheduleAlarm(this, 5, 0)
    }

    private fun initTimeButton(
        timeButton: Button,
        time: Preferences.TimeOfDay,
        listener: (time: Preferences.TimeOfDay) -> Unit
    ) {
        var actualTime = time
        timeButton.setText(actualTime.format())

        timeButton.setOnClickListener {
            TimePickerDialog(
                this,
                { timePicker: TimePicker, hour: Int, minute: Int ->
                    actualTime = Preferences.TimeOfDay(hour, minute)
                    timeButton.setText(actualTime.format())
                    listener(actualTime)
                }, actualTime.hour, actualTime.minute, true
            ).show()
        }
    }

}