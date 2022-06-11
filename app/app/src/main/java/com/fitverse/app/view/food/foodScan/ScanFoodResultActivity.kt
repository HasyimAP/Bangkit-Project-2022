package com.fitverse.app.view.food.foodScan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fitverse.app.ViewModelFactory
import com.fitverse.app.databinding.ActivityScanFoodResultBinding
import com.fitverse.app.model.UserPreference
import com.fitverse.app.view.food.dataStore


class ScanFoodResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanFoodResultBinding
    private lateinit var viewModel: ScanFoodResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanFoodResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra("result")

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ScanFoodResultViewModel::class.java]

        if (name != null) {
            viewModel.getUser().observe(this) { user ->
                    viewModel.setFoodDetail(user.token,name)
            }
            Toast.makeText(this, "${name}", Toast.LENGTH_SHORT).show()
        }
        showLoading(true)
        viewModel.getFoodDetail().observe(this) {
            showLoading(false)
            binding.apply {
                resultname.text = it.name
                description.text = it.description.replace("\\n","\n")
                Glide.with(this@ScanFoodResultActivity)
                    .load(it.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(photoFood)
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