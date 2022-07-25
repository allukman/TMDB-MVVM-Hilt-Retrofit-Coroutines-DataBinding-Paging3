package com.example.tmdb.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityMainBinding
import com.example.tmdb.ui.genre.GenreFragment
import com.example.tmdb.ui.genre.ListGenreFragment
import com.example.tmdb.ui.home.HomeFragment
import com.example.tmdb.util.SharedPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding
    private lateinit var sharedPref: SharedPreference
    private val homeFragment = HomeFragment()
    private val genreFragment = GenreFragment()
    private val listGenreFragment = ListGenreFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        sharedPref = SharedPreference(this)

        navigate()
        initBottomNav()
    }

    private fun initBottomNav() {
        bind.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> {
                    currentFragment(homeFragment)
                    bind.toolbarTitle.text = "Home"
                    bind.filter.visibility = View.GONE
                    bind.check.visibility = View.GONE
                }
                R.id.nav_genre -> {
                    currentFragment(genreFragment)
                    bind.toolbarTitle.text = "Genre"
                    bind.filter.visibility = View.VISIBLE
                    bind.check.visibility = View.GONE
                }
                else -> currentFragment(homeFragment)
            }
            true
        }

        bind.filter.setOnClickListener {
            currentFragment(listGenreFragment)
            bind.toolbarTitle.text = "Select Genre"
            bind.filter.visibility = View.GONE
            bind.check.visibility = View.VISIBLE
        }

        bind.check.setOnClickListener {
            sharedPref.saveNavigateGenre(true)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigate() {
        if (sharedPref.getNavigateGenre()) {
            sharedPref.saveNavigateGenre(false)
            currentFragment(genreFragment)
            bind.toolbarTitle.text = "Genre"
            bind.filter.visibility = View.VISIBLE
            bind.check.visibility = View.GONE
            bind.bottomNavigation.selectedItemId = R.id.nav_genre
        } else {
            currentFragment(homeFragment)
            bind.toolbarTitle.text = "Home"
            bind.filter.visibility = View.GONE
            bind.check.visibility = View.GONE
            bind.bottomNavigation.selectedItemId = R.id.nav_home
        }
    }

    private fun currentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment).commit()
        }
}