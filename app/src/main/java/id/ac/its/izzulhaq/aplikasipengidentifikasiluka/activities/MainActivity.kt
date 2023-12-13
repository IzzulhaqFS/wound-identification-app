package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.R

class MainActivity : AppCompatActivity() {
    private lateinit var btnWoundCheck: Button
    private lateinit var btnHistory: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = TITLE

        btnWoundCheck = findViewById(R.id.btn_wound_check)
        btnHistory = findViewById(R.id.btn_history)

        btnWoundCheck.setOnClickListener {
            goToWoundCheck()
        }

        btnHistory.setOnClickListener {
            goToHistory()
        }
    }

    private fun goToWoundCheck() {
        val intent = Intent(this@MainActivity, WoundCheckActivity::class.java)
        startActivity(intent)
    }

    private fun goToHistory() {
        val intent = Intent(this@MainActivity, HistoryActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val TITLE = "Home"
    }
}