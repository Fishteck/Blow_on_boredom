package android.example.blowonboredom.ui


import android.example.blowonboredom.R
import android.example.blowonboredom.ui.fragments.FavoriteActivitiesFragment
import android.example.blowonboredom.ui.fragments.HomeFragment
import android.example.blowonboredom.ui.fragments.RandomActivitiesFragment
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomNavigation : ChipNavigationBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMembers()
        initNavigation()


       if (savedInstanceState == null) {
           bottomNavigation.setItemSelected(R.id.homeFragment, true)
           supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
       }


    }

    private fun initNavigation() {
        bottomNavigation.setOnItemSelectedListener {
            var fragment : Fragment? = null
            fragment = when(it) {
                R.id.homeFragment -> {
                    HomeFragment()
                }
                R.id.favoriteActivitiesFragment -> {
                    FavoriteActivitiesFragment()
                }
                R.id.randomActivitiesFragment -> {
                    RandomActivitiesFragment()
                }
                else -> {
                    HomeFragment()
                }
            }

            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        }
    }

    private fun initMembers() {
        bottomNavigation = findViewById(R.id.bottom_navigation_view)
    }
    /*private fun initNavigation() {
        val host = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
        navController = host.navController
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment))
         bottomNavigation  = findViewById(R.id.bottom_navigation_view)
             bottomNavigation.setupWithNavController( navController )
        setupActionBarWithNavController(navController, appBarConfiguration)

    }*/

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

}