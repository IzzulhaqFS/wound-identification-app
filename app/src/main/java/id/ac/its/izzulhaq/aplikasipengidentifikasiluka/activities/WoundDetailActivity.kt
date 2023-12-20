package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.activities

import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.R
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.models.Wound
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.viewmodels.ViewModelFactory
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.viewmodels.WoundDetailViewModel
import java.io.File

class WoundDetailActivity : AppCompatActivity() {
    private lateinit var imgWound: ImageView
    private lateinit var tvWoundType: TextView
    private lateinit var tvCheckDate: TextView
    private lateinit var btnDelete: Button

    private lateinit var viewModel: WoundDetailViewModel
    private var wound: Wound? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wound_detail)

        viewModel = obtainViewModel(this)

        imgWound = findViewById(R.id.img_wound_detail)
        tvWoundType = findViewById(R.id.tv_wound_type_detail)
        tvCheckDate  = findViewById(R.id.tv_check_date_detail)
        btnDelete = findViewById(R.id.btn_delete)

        wound = when {
            Build.VERSION.SDK_INT >= 33 -> intent.getParcelableExtra(EXTRA_WOUND, Wound::class.java)
            Build.VERSION.SDK_INT < 33 -> @Suppress("DEPRECATION") intent.getParcelableExtra(
                EXTRA_WOUND)
            else -> null
        }

        wound?.let { showDetail(it) }

        btnDelete.setOnClickListener {
            wound?.let { it1 -> viewModel.delete(it1) }
        }

    }

    private fun showDetail(wound: Wound) {
        val photoPath = wound.photoUri.toString()
        val photoFile = File(photoPath)
        val photo = BitmapFactory.decodeFile(photoFile.path)
        imgWound.setImageBitmap(photo)

        tvWoundType.text = wound.woundType
        tvCheckDate.text = wound.checkDate
    }

    private fun obtainViewModel(activity: AppCompatActivity): WoundDetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[WoundDetailViewModel::class.java]
    }

    companion object {
        const val EXTRA_WOUND = "extra_wound"
    }
}