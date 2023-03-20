package com.fitverse.app.view.fitness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fitverse.app.view.scanFavorite.FavoriteFitnessViewModel
import com.fitverse.app.R
import com.fitverse.app.ViewModelFactory
import com.fitverse.app.databinding.ActivityDetailFitnessBinding

import com.fitverse.app.model.FitnessModel
import com.fitverse.app.model.UserPreference
import com.fitverse.app.view.fitness.fitnessScan.ScanFitnessResultViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFitnessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailFitnessBinding
    private lateinit var viewModel: ScanFitnessResultViewModel
    private lateinit var favoriteFitnessViewModel: FavoriteFitnessViewModel

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

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ScanFitnessResultViewModel::class.java]
        favoriteFitnessViewModel = ViewModelProvider(this)[FavoriteFitnessViewModel::class.java]
        if ( listFitnessModel.name != null) {
            viewModel.getUser().observe(this) { user ->
                viewModel.setFitnessDetail(user.token, listFitnessModel.name)
            }
        }
//        showLoading(true)
        viewModel.getFitnessDetail().observe(this) {
//            showLoading(false)
            var name1 = it.name
            var desc1 = it.description
            var id1 = it.id
            var foto1 = it.photoUrl

            binding.apply {
                nameFitness.text = it.name
                description.text = it.description.replace("\\n","\n")
                Glide.with(this@DetailFitnessActivity)
                    .load(it.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(photoFitness)
            }
            var isChecked = false
            CoroutineScope(Dispatchers.IO).launch {
                val count = favoriteFitnessViewModel.checkFavorite(it.id)
                withContext(Dispatchers.Main) {
                    if (count != null) {
                        if (count > 0) {
                            binding.toggleFavorite.isChecked = true
                            isChecked = true
                        } else {
                            binding.toggleFavorite.isChecked = false
                            isChecked = false
                        }
                    }
                }
            }
            binding.toggleFavorite.setOnClickListener {
                isChecked = !isChecked
                if (isChecked) {
                    favoriteFitnessViewModel.addToFavorite(id1, name1,foto1,desc1 )
                    Toast.makeText(this, "Add $name1 to Favorite", Toast.LENGTH_LONG).show()
                } else {
                    favoriteFitnessViewModel.deleteFromFavorite(id1)
                    Toast.makeText(this, "Remove $name1 from Favorite", Toast.LENGTH_LONG).show()
                }
                binding.toggleFavorite.isChecked = isChecked
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}