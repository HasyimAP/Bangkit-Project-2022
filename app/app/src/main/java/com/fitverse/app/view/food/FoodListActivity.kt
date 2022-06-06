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
import com.fitverse.app.adapter.AdapterFood
import com.fitverse.app.databinding.ActivityFoodListBinding
import com.fitverse.app.model.UserPreference
import com.fitverse.app.ListViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class FoodListActivity : AppCompatActivity() {

    private lateinit var listViewModel: ListViewModel
    private lateinit var binding: ActivityFoodListBinding
    private lateinit var adapter: AdapterFood


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = ("List Food")

        listViewModel = ViewModelProvider(this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ListViewModel::class.java]
        adapter = AdapterFood()
        listViewModel.setFood()
        showLoading(true)
        listViewModel.getFood().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
                Toast.makeText(applicationContext,("${it[1]}"), Toast.LENGTH_SHORT).show()

            }
            else {
                showLoading(false)
                Toast.makeText(applicationContext,("The Data is Empty"), Toast.LENGTH_SHORT).show()

            }
        }

        binding.apply {
            recyleView.layoutManager = LinearLayoutManager(this@FoodListActivity)
            recyleView.setHasFixedSize(true)
            recyleView.adapter = adapter
        }

//        binding.addFab.setOnClickListener{
//            val intent = Intent(this@MainActivity, PostStoryActivity::class.java)
//            startActivity(intent)
//        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.option_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.maps_menu -> {
//                Toast.makeText(applicationContext,("The Fiture is Not Ready"), Toast.LENGTH_SHORT).show()
//                true
//            }
//            R.id.settings_menu -> {
//                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
//                true
//            }
//            R.id.logout_menu -> {
//                mainViewModel.logout()
//                finish()
//                true
//            }
//            else -> true
//
//        }
//        return super.onOptionsItemSelected(item)
//    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}