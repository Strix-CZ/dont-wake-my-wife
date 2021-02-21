package online.temer.dontwakemywife

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.buttonTestAlarm).setOnClickListener {
            val intent = Intent(this, Alarm::class.java)
            startActivity(intent)
        }

        scheduleAlarm(this, 5, 0)
    }
}