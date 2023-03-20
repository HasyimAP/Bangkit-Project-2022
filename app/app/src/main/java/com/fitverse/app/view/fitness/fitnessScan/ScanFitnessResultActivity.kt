package com.fitverse.app.view.fitness.fitnessScan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fitverse.app.view.scanFavorite.FavoriteFitnessViewModel
import com.fitverse.app.ViewModelFactory
import com.fitverse.app.databinding.ActivityScanFitnessResultBinding
import com.fitverse.app.model.UserPreference
import com.fitverse.app.view.fitness.dataStore
import com.fitverse.app.view.history.fitness.RecentFitnessViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScanFitnessResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanFitnessResultBinding
    private lateinit var viewModel: ScanFitnessResultViewModel
    private lateinit var favoriteFitnessViewModel: FavoriteFitnessViewModel
    private lateinit var recentFitnessViewModel: RecentFitnessViewModel

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
        favoriteFitnessViewModel = ViewModelProvider(this)[FavoriteFitnessViewModel::class.java]
        recentFitnessViewModel = ViewModelProvider(this)[RecentFitnessViewModel::class.java]
        if (name != null) {
            viewModel.getUser().observe(this) { user ->
                viewModel.setFitnessDetail(user.token,name)
            }

            Toast.makeText(this, "${name}", Toast.LENGTH_SHORT).show()
        }
        showLoading(true)
        viewModel.getFitnessDetail().observe(this) {
            showLoading(false)
            var name1 = it.name
            var desc = it.description
            var id1 = it.id
            var foto = it.photoUrl

            binding.apply {
                fitnessName.text = it.name
                description.text = it.description.replace("\\n","\n")
                Glide.with(this@ScanFitnessResultActivity)
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
            recentFitnessViewModel.addRecentFitness(id1, name1,foto,desc )

            binding.toggleFavorite.setOnClickListener {
                isChecked = !isChecked
                if (isChecked) {
                    favoriteFitnessViewModel.addToFavorite(id1, name1,foto,desc )
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}