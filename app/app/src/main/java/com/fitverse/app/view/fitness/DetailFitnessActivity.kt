package com.fitverse.app.view.fitness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bumptech.glide.Glide
import com.fitverse.app.R
import com.fitverse.app.databinding.ActivityDetailFitnessBinding

import com.fitverse.app.model.FitnessModel

class DetailFitnessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailFitnessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFitnessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listFitnessModel = intent.getParcelableExtra<FitnessModel>("ListFitnessModel") as FitnessModel
        Glide.with(applicationContext)
            .load(listFitnessModel.photoUrl)
            .into(findViewById(R.id.photoFitness))
        findViewById<TextView>(R.id.nameFitness).text = listFitnessModel.name
        findViewById<TextView>(R.id.description).text= listFitnessModel.description.replace("\\n", "\n")

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}