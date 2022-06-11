package com.fitverse.app.view.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fitverse.app.R
import com.fitverse.app.ViewModelFactory
import com.fitverse.app.databinding.ActivityDetailFoodBinding
import com.fitverse.app.model.FoodModel
import com.fitverse.app.model.UserPreference
import com.fitverse.app.view.fitness.dataStore
import com.fitverse.app.view.fitness.fitnessScan.ScanFitnessResultViewModel
import com.fitverse.app.view.food.foodScan.ScanFoodResultViewModel
import com.fitverse.app.view.scanFavorite.FavoriteFitnessViewModel
import com.fitverse.app.view.scanFavorite.FavoriteFoodViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailFoodBinding
    private lateinit var viewModel: ScanFoodResultViewModel
    private lateinit var favoriteFoodViewModel: FavoriteFoodViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listFoodModel = intent.getParcelableExtra<FoodModel>("ListStoryModel") as FoodModel
        Glide.with(applicationContext)
            .load(listFoodModel.photoUrl)
            .into(findViewById(R.id.photoFood))
        findViewById<TextView>(R.id.nameFood).text = listFoodModel.name
        findViewById<TextView>(R.id.description).text= listFoodModel.description.replace("\\n", "\n")

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ScanFoodResultViewModel::class.java]
        favoriteFoodViewModel = ViewModelProvider(this)[FavoriteFoodViewModel::class.java]
        viewModel.getUser().observe(this) { user ->
            viewModel.setFoodDetail(user.token, listFoodModel.name)
        }
//        showLoading(true)
        viewModel.getFoodDetail().observe(this) {
//            showLoading(false)
            val name1 = it.name
            val desc1 = it.description
            val id1 = it.id
            val foto1 = it.photoUrl

            binding.apply {
                nameFood.text = it.name
                description.text = it.description.replace("\\n","\n")
                Glide.with(this@DetailFoodActivity)
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

            binding.toggleFavorite.setOnClickListener {
                isChecked = !isChecked
                if (isChecked) {
                    favoriteFoodViewModel.addToFavorite(id1, name1,foto1,desc1 )
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
}