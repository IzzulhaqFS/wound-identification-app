package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.R
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.adapters.WoundListAdapter
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.models.Wound
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.viewmodels.HistoryViewModel
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.viewmodels.ViewModelFactory
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.viewmodels.WoundCheckViewModel

class HistoryActivity : AppCompatActivity() {
    private lateinit var rvWound: RecyclerView
    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapter: WoundListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        viewModel = obtainViewModel(this@HistoryActivity)

        rvWound = findViewById(R.id.rv_wound)

        viewModel.getAll().observe(this) {
            showWoundList(it)
        }
    }

    private fun showWoundList(wounds: List<Wound>) {
        adapter = WoundListAdapter(wounds)
        rvWound.layoutManager = LinearLayoutManager(this)
        rvWound.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): HistoryViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[HistoryViewModel::class.java]
    }
}