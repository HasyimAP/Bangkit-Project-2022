package com.fitverse.app.view.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bumptech.glide.Glide
import com.fitverse.app.R
import com.fitverse.app.databinding.ActivityDetailFoodBinding
import com.fitverse.app.databinding.ActivityScanResultBinding
import com.fitverse.app.model.ListFoodModel

class DetailFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailFoodBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listStoryModel = intent.getParcelableExtra<ListFoodModel>("ListStoryModel") as ListFoodModel
        Glide.with(applicationContext)
            .load(listStoryModel.photoUrl)
            .into(findViewById(R.id.photoFood))
        findViewById<TextView>(R.id.nameFood).text = listStoryModel.nama
        findViewById<TextView>(R.id.description).text= listStoryModel.description.replace("\\n", "\n")

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}