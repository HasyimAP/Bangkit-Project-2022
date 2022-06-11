package com.fitverse.app.view.food.foodScan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fitverse.app.ViewModelFactory
import com.fitverse.app.databinding.ActivityScanFoodResultBinding
import com.fitverse.app.model.UserPreference
import com.fitverse.app.view.food.dataStore
import com.fitverse.app.view.history.food.RecentFoodViewModel
import com.fitverse.app.view.scanFavorite.FavoriteFoodViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ScanFoodResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanFoodResultBinding
    private lateinit var viewModel: ScanFoodResultViewModel
    private lateinit var favoriteFoodViewModel: FavoriteFoodViewModel
    private lateinit var recentFoodViewModel: RecentFoodViewModel

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
        favoriteFoodViewModel = ViewModelProvider(this)[FavoriteFoodViewModel::class.java]
        recentFoodViewModel = ViewModelProvider(this)[RecentFoodViewModel::class.java]

        if (name != null) {
            viewModel.getUser().observe(this) { user ->
                    viewModel.setFoodDetail(user.token,name)
            }
            Toast.makeText(this, "${name}", Toast.LENGTH_SHORT).show()
        }
        showLoading(true)
        viewModel.getFoodDetail().observe(this) {
            showLoading(false)
            var name1 = it.name
            var desc = it.description
            var id1 = it.id
            var foto = it.photoUrl
            binding.apply {
                resultname.text = it.name
                description.text = it.description.replace("\\n","\n")
                Glide.with(this@ScanFoodResultActivity)
                    .load(it.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(photoFood)
            }
            var isChecked = false
            CoroutineScope(Dispatchers.IO).launch {
                val count = favoriteFoodViewModel.checkFavorite(it.id)
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
            recentFoodViewModel.addRecentFood(id1, name1,foto,desc )

            binding.toggleFavorite.setOnClickListener {
                isChecked = !isChecked
                if (isChecked) {
                    favoriteFoodViewModel.addToFavorite(id1, name1,foto,desc )
                    Toast.makeText(this, "Add $name1 to Favorite", Toast.LENGTH_LONG).show()
                } else {
                    favoriteFoodViewModel.deleteFromFavorite(id1)
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