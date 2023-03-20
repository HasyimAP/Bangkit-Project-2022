package com.fitverse.app.view.fitness

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
import com.fitverse.app.databinding.ActivityFitnessListBinding
import com.fitverse.app.model.UserPreference

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class FitnessListActivity : AppCompatActivity() {

    private lateinit var fitnessListViewModel: FitnessListViewModel
    private lateinit var binding: ActivityFitnessListBinding
    private lateinit var adapter: AdapterFitness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFitnessListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val actionBar = supportActionBar
        actionBar?.title = ("Exercise List")

        adapter = AdapterFitness()

        fitnessListViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[FitnessListViewModel::class.java]

        binding.apply {
            rvFitness.layoutManager = LinearLayoutManager(this@FitnessListActivity)
            rvFitness.setHasFixedSize(true)
            rvFitness.adapter = adapter
        }

        showLoading(true)

        fitnessListViewModel.getUser().observe(this) { user ->
            fitnessListViewModel.setFitness(user.token)
        }
        fitnessListViewModel.getFitness().observe(this) {
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
            searchFitness()
        }

        binding.etQuery.setOnKeyListener{ _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                searchFitness()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun searchFitness(){
        binding.apply {
            val query = etQuery.text.toString()
            if (query.isEmpty()){
                etQuery.error = "Field ini tidak boleh kosong"
                return
            }
            showLoading(true)
            fitnessListViewModel.getUser().observe(this@FitnessListActivity) { user ->
                fitnessListViewModel.setSearchFitness(user.token,query)
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