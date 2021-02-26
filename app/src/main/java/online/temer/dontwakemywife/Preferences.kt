package online.temer.dontwakemywife

import android.content.SharedPreferences

class Preferences constructor(val preferences: SharedPreferences) {

    fun saveWakeUpTime(time: TimeOfDay) {
        preferences.edit()
            .putInt("wakeUpHour", time.hour)
            .putInt("wakeUpMinute", time.minute)
            .apply()
    }

    fun loadWakeUpTime(): TimeOfDay {
        return TimeOfDay(preferences.getInt("wakeUpHour", 7), preferences.getInt("wakeUpMinute", 0))
    }

    fun saveBedTime(time: TimeOfDay) {
        preferences.edit()
            .putInt("bedHour", time.hour)
            .putInt("bedMinute", time.minute)
            .apply()
    }

    fun loadBedTime(): TimeOfDay {
        return TimeOfDay(preferences.getInt("bedHour", 21), preferences.getInt("bedMinute", 0))
    }

    class TimeOfDay constructor(var hour: Int, var minute: Int) {
        fun format(): String {
            return "$hour:" + "$minute".padStart(2, '0')
        }
    }

}