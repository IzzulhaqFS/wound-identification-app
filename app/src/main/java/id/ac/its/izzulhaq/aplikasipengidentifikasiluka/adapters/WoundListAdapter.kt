package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.R
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.models.Wound
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class WoundListAdapter(private val wounds: List<Wound>) : RecyclerView.Adapter<WoundListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgWound: ImageView
        private val tvWoundType: TextView
        private val tvCheckDate: TextView

        init {
            imgWound = view.findViewById(R.id.img_wound_item)
            tvWoundType = view.findViewById(R.id.tv_wound_type_item)
            tvCheckDate = view.findViewById(R.id.tv_check_date_item)
        }

        fun bind(wound: Wound) {
            val photoPath = wound.photoUri.toString()
            val photoFile = File(photoPath)
            val photo = BitmapFactory.decodeFile(photoFile.path)
            imgWound.setImageBitmap(photo)

            tvWoundType.text = wound.woundType

            val formatter = SimpleDateFormat("dd-MMM-yyyy", Locale.US)
            val date = wound.checkDate
            val checkDate = formatter.format(date)
            tvCheckDate.text = checkDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_wound, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = wounds.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(wounds[position])
    }
}