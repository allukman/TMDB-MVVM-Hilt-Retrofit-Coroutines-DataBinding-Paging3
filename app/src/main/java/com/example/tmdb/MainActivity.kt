package com.example.tmdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tmdb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private
//    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
//        navController = navHostFragment.findNavController()
//
//        val appBarConfiguration = AppBarConfiguration.Builder(
//            R.id.nav_home, R.id.nav_genre
//        ).build()
//
//        actionBar?.setDisplayHomeAsUpEnabled(true)
//        actionBar?.setHomeButtonEnabled(true)
//
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        binding.navBottom.setupWithNavController(navController)
    }
}