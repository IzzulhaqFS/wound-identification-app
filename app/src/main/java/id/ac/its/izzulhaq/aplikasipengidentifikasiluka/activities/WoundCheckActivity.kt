package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.activities

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.R
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.models.Wound
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.uriToFile
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.viewmodels.ViewModelFactory
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.viewmodels.WoundCheckViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WoundCheckActivity : AppCompatActivity() {
    private lateinit var btnCamera: Button
    private lateinit var btnGallery: Button
    private lateinit var btnProcess: Button
    private lateinit var btnSave: Button
    private lateinit var btnReset: Button
    private lateinit var imgWound: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvWoundType: TextView
    private lateinit var tvWoundTypeValue: TextView
    private lateinit var currentPhotoPath: String

    private lateinit var viewModel: WoundCheckViewModel

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

        viewModel = obtainViewModel(this@WoundCheckActivity)

        btnCamera = findViewById(R.id.btn_camera)
        btnGallery = findViewById(R.id.btn_gallery)
        btnProcess = findViewById(R.id.btn_process)
        btnSave = findViewById(R.id.btn_save)
        btnReset = findViewById(R.id.btn_reset)
        imgWound = findViewById(R.id.img_wound)
        progressBar = findViewById(R.id.progress_bar)
        tvWoundType = findViewById(R.id.tv_wound_type)
        tvWoundTypeValue = findViewById(R.id.tv_wound_type_value)

        btnCamera.setOnClickListener {
            startCamera()
        }

        btnGallery.setOnClickListener {
            startGallery()
        }

        btnProcess.setOnClickListener {
            processImage()
        }

        btnSave.setOnClickListener {
            saveWoundCheck()
        }

        btnReset.setOnClickListener {
            resetUI()
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

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val selectedImg: Uri = it.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            getFile = myFile
            imgWound.setImageURI(selectedImg)
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

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun processImage() {
//        tvWoundTypeValue.text = getText(R.string.laserisasi)
        btnProcess.visibility = View.INVISIBLE
//        progressBar.visibility = View.VISIBLE
//        Thread.sleep(5000)
//        progressBar.visibility = View.GONE

        val response = uploadImage()
        tvWoundTypeValue.text = response

        tvWoundType.visibility = View.VISIBLE
        tvWoundTypeValue.visibility = View.VISIBLE
        btnSave.visibility = View.VISIBLE
        btnReset.visibility = View.VISIBLE
    }

    private fun saveWoundCheck() {
        val formatter = SimpleDateFormat("dd-MMM-yyyy", Locale.US)
        val date = Date()
        val dateFormat = formatter.format(date)
        val woundType = tvWoundTypeValue.text.toString()

        val wound = Wound(0,
            currentPhotoPath,
            dateFormat,
            woundType
        )

        viewModel.insert(wound)
    }

    private fun resetUI() {
        imgWound.setImageBitmap(null)
        tvWoundType.visibility = View.INVISIBLE
        tvWoundTypeValue.visibility = View.INVISIBLE
        btnSave.visibility = View.INVISIBLE
        btnReset.visibility = View.INVISIBLE
        btnProcess.visibility = View.VISIBLE
    }

    private fun obtainViewModel(activity: AppCompatActivity): WoundCheckViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[WoundCheckViewModel::class.java]
    }

    private fun uploadImage(): String {
        if (getFile != null) {
            val file = reduceImageFile(getFile as File)

            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )

            return viewModel.predict(imageMultipart)
        }
        return "Image not found"
    }

    private fun reduceImageFile(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int
        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > 1000000)
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}