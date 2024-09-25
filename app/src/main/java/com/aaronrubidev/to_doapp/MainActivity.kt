package com.aaronrubidev.to_doapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.aaronrubidev.to_doapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener() { _, destination, _ ->
            when (destination.id) {
                R.id.addFragment -> {
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
                }
                R.id.updateFragment -> {
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
                }
                else -> {
                }
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}