package com.fitverse.app.view.food

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitverse.app.ViewModelFactory
import com.fitverse.app.databinding.ActivityFoodListBinding
import com.fitverse.app.model.UserPreference

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class FoodListActivity : AppCompatActivity() {

    private lateinit var listViewModel: ListViewModel
    private lateinit var binding: ActivityFoodListBinding
    private lateinit var adapter: AdapterFood

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val actionBar = supportActionBar
        actionBar?.title = ("List Food")

        adapter = AdapterFood()

        listViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ListViewModel::class.java]

        binding.apply {
            rvFood.layoutManager = LinearLayoutManager(this@FoodListActivity)
            rvFood.setHasFixedSize(true)
            rvFood.adapter = adapter
        }

        listViewModel.setFood()
        showLoading(true)
        listViewModel.getFood().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
//                Toast.makeText(applicationContext,("${it[1]}"), Toast.LENGTH_SHORT).show()
            } else {
                showLoading(false)
                Toast.makeText(applicationContext, ("The Data is Empty"), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}