package com.fitverse.app.view.scanResult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fitverse.app.databinding.ActivityScanResultBinding
import com.fitverse.app.view.food.ScanFoodActivity

class ScanResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Testing
        binding.apply {
            resultName.text = intent.getStringExtra("result")
            listAkurasi.text = intent.getStringExtra("list_akurasi")

            rescanButton.setOnClickListener{
                onBackPressed()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}