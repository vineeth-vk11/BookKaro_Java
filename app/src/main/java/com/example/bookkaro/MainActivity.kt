package com.example.bookkaro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object {
        var FRAGMENT_HOME = 10
        var FRAGMENT_BOOKINGS = 11
        var FRAGMENT_HELP = 12
        var FRAGMENT_PROFILE = 13
    }

    private var currentFragment = FRAGMENT_HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment)

        currentFragment = FRAGMENT_HOME

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val listener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_bookings -> {
                    if (currentFragment != FRAGMENT_BOOKINGS)
                        navController.navigate(R.id.bookingsFragment)
                    currentFragment = FRAGMENT_BOOKINGS
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_help -> {
                    if (currentFragment != FRAGMENT_HELP)
                        navController.navigate(R.id.helpFragment)
                    currentFragment = FRAGMENT_HELP
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_profile -> {
                    if (currentFragment != FRAGMENT_PROFILE)
                        navController.navigate(R.id.profileFragment)
                    currentFragment = FRAGMENT_PROFILE
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_home -> {
                    if (currentFragment != FRAGMENT_HOME)
                        navController.navigate(R.id.homeFragment)
                    currentFragment = FRAGMENT_HOME
                    return@OnNavigationItemSelectedListener true
                }
                else -> {
                    navController.navigate(R.id.homeFragment)
                    currentFragment = FRAGMENT_HOME
                    return@OnNavigationItemSelectedListener true
                }
            }
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(listener)
    }
}