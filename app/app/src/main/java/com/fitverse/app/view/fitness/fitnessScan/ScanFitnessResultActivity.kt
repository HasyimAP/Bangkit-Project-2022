package com.fitverse.app.view.fitness.fitnessScan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fitverse.app.ViewModelFactory
import com.fitverse.app.databinding.ActivityScanFitnessResultBinding
import com.fitverse.app.model.UserPreference
import com.fitverse.app.view.fitness.dataStore
import com.fitverse.app.view.fitness.dataStore
import com.fitverse.app.view.fitness.fitnessScan.ScanFitnessResultViewModel

class ScanFitnessResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanFitnessResultBinding
    private lateinit var viewModel: ScanFitnessResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanFitnessResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra("result")

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ScanFitnessResultViewModel::class.java]

        if (name != null) {
            viewModel.getUser().observe(this) { user ->
                viewModel.setFitnessDetail(user.token,name)
            }
            Toast.makeText(this, "${name}", Toast.LENGTH_SHORT).show()
        }
        showLoading(true)
        viewModel.getFitnessDetail().observe(this) {
            showLoading(false)
            binding.apply {
                fitnessName.text = it.name
                description.text = it.description.replace("\\n","\n")
                Glide.with(this@ScanFitnessResultActivity)
                    .load(it.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(photoFitness)
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