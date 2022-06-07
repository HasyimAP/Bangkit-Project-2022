package com.fitverse.app.view.scanResult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fitverse.app.R
import com.fitverse.app.databinding.ActivityScanResultBinding
import com.fitverse.app.model.ListFoodModel
import com.fitverse.app.view.food.ScanFoodActivity

class ScanResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanResultBinding
    private lateinit var viewModel: ScanResultViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val hasil = intent.getStringExtra("list_akurasi")
        val id = intent.getStringExtra("result")



//        binding.apply {
//            resultname.text = intent.getStringExtra("result")
//            listakurasi.text = intent.getStringExtra("list_akurasi")
//
//            rescanButton.setOnClickListener{
//                onBackPressed()
//            }
//        }

        viewModel = ViewModelProvider(this)[ScanResultViewModel::class.java]

        if (id != null) {
            viewModel.setFoodDetail(id)
            Toast.makeText(this, "${id}", Toast.LENGTH_SHORT).show()
        }
        showLoading(true)
        viewModel.getFoodDetail().observe(this) {
            showLoading(false)
            binding.apply {
                resultname.text = it.nama
                description.text = it.description
                Glide.with(this@ScanResultActivity)
                    .load(it.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
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