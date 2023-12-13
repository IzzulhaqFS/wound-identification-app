package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.adapters

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.R
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.activities.WoundDetailActivity
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.models.Wound
import java.io.File

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

            tvWoundType.text = wound.woundType.toString()

            tvCheckDate.text = wound.checkDate

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, WoundDetailActivity::class.java)
                intent.putExtra(WoundDetailActivity.EXTRA_ID, wound.id)
                itemView.context.startActivity(intent)
            }
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