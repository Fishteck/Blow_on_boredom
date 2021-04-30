package android.example.blowonboredom.ui


import android.example.blowonboredom.R
import android.example.blowonboredom.ui.fragments.FavoriteActivitiesFragment
import android.example.blowonboredom.ui.fragments.HomeFragment
import android.example.blowonboredom.ui.fragments.RandomActivitiesFragment
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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




}