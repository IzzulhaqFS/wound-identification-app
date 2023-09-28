package id.ac.its.izzulhaq.aplikasipengidentifikasiluka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    private lateinit var btnCamera: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = TITLE

        btnCamera = findViewById(R.id.btn_camera)

        btnCamera.setOnClickListener {
            startCamera()
        }
    }

    private fun startCamera() {

    }

    companion object {
        private const val TITLE = "Home"
    }
}