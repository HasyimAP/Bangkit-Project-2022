package com.fitverse.app.view.food.foodScan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitverse.app.ViewModelFactory
import com.fitverse.app.databinding.ActivityScanFoodResultBinding
import com.fitverse.app.model.UserPreference
import com.fitverse.app.view.food.dataStore


class ScanFoodResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanFoodResultBinding
    private lateinit var viewModel: ScanFoodResultViewModel
    private lateinit var adapter: AdapterScanFoodResult


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanFoodResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = AdapterScanFoodResult()

        val name = intent.getStringExtra("result")

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ScanFoodResultViewModel::class.java]

        binding.apply {
            rvFoodResult.layoutManager = LinearLayoutManager(this@ScanFoodResultActivity)
            rvFoodResult.setHasFixedSize(true)
            rvFoodResult.adapter = adapter
            rescanButton.setOnClickListener{
                onSupportNavigateUp()
            }
        }

        showLoading(true)

        viewModel.getUser().observe(this) { user ->
            if (name != null) {
                viewModel.setFoodDetail(user.token,name)
            }
        }

        viewModel.getFoodDetail().observe(this) {
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