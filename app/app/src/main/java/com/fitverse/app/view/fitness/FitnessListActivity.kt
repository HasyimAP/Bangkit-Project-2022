package com.fitverse.app.view.fitness

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

        fitnessListViewModel.setFitness()
        showLoading(true)
        fitnessListViewModel.getFitness().observe(this) {
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