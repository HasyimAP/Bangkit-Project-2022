package com.fitverse.app.view.comingSoon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fitverse.app.R

class ComingSoonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coming_soon)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}