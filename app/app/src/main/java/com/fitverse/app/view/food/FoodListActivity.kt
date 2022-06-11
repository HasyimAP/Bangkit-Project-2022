package com.fitverse.app.view.food

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
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

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class FoodListActivity : AppCompatActivity() {

    private lateinit var listViewModel: FoodListViewModel
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
        )[FoodListViewModel::class.java]

        binding.apply {
            rvFood.layoutManager = LinearLayoutManager(this@FoodListActivity)
            rvFood.setHasFixedSize(true)
            rvFood.adapter = adapter
        }

        showLoading(true)

        listViewModel.getUser().observe(this) { user ->
            listViewModel.setFood(user.token)
        }

        listViewModel.getFood().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
                emptyData(false)
            }
            if (it.isEmpty()) {
                showLoading(false)
                emptyData(true)
            }
        }

        binding.buttonSearch.setOnClickListener {
            searchFood()
        }

        binding.etQuery.setOnKeyListener{ _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                searchFood()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun searchFood(){
        binding.apply {
            val query = etQuery.text.toString()
            if (query.isEmpty()){
                etQuery.error = "Field ini tidak boleh kosong"
                return
            }
            showLoading(true)
            listViewModel.getUser().observe(this@FoodListActivity) { user ->
                listViewModel.setSearchFood(user.token,query)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun emptyData(state: Boolean) {
        binding.emptyData.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}