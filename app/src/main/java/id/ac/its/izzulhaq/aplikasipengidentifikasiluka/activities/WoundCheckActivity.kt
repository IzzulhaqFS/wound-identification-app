package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.R
import kotlinx.coroutines.delay
import java.io.File

class WoundCheckActivity : AppCompatActivity() {
    private lateinit var btnCamera: Button
    private lateinit var btnGallery: Button
    private lateinit var btnProcess: Button
    private lateinit var btnSave: Button
    private lateinit var btnReset: Button
    private lateinit var imgWound: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var currentPhotoPath: String

    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wound_check)

        supportActionBar?.hide()

        if (!allPermissionGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        btnCamera = findViewById(R.id.btn_camera)
        btnGallery = findViewById(R.id.btn_gallery)
        btnProcess = findViewById(R.id.btn_process)
        btnSave = findViewById(R.id.btn_save)
        btnReset = findViewById(R.id.btn_reset)
        imgWound = findViewById(R.id.img_wound)
        progressBar = findViewById(R.id.progress_bar)

        btnCamera.setOnClickListener {
            startCamera()
        }

        btnProcess.setOnClickListener {
            processImage()
        }
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile

            val result = BitmapFactory.decodeFile(myFile.path)
            imgWound.setImageBitmap(result)
        }
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        id.ac.its.izzulhaq.aplikasipengidentifikasiluka.createTempFile(application).also {
            val photoUri: Uri = FileProvider.getUriForFile(
                this@WoundCheckActivity,
                "id.ac.its.izzulhaq.aplikasipengidentifikasiluka",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun processImage() {
        btnProcess.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
        Thread.sleep(5000)
        progressBar.visibility = View.GONE
        btnSave.visibility = View.VISIBLE
        btnReset.visibility = View.VISIBLE
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}