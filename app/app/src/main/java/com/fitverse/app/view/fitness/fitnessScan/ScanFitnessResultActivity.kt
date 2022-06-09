package com.fitverse.app.view.fitness.fitnessScan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitverse.app.ViewModelFactory
import com.fitverse.app.databinding.ActivityScanFitnessResultBinding
import com.fitverse.app.model.UserPreference
import com.fitverse.app.view.fitness.dataStore
import com.fitverse.app.view.fitness.fitnessScan.AdapterScanFitnessResult
import com.fitverse.app.view.fitness.fitnessScan.ScanFitnessResultViewModel

class ScanFitnessResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanFitnessResultBinding
    private lateinit var viewModel: ScanFitnessResultViewModel
    private lateinit var adapter: AdapterScanFitnessResult


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanFitnessResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = AdapterScanFitnessResult()

        val name = intent.getStringExtra("result")

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ScanFitnessResultViewModel::class.java]

        binding.apply {
            rvFitnessResult.layoutManager = LinearLayoutManager(this@ScanFitnessResultActivity)
            rvFitnessResult.setHasFixedSize(true)
            rvFitnessResult.adapter = adapter
            rescanButton.setOnClickListener{
                onSupportNavigateUp()
            }
        }

        showLoading(true)

        viewModel.getUser().observe(this) { user ->
            if (name != null) {
                viewModel.setFitnessDetail(user.token,name)
            }
        }

        viewModel.getFitnessDetail().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            } else {
                showLoading(false)
                Toast.makeText(applicationContext, ("The Data is Empty"), Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}