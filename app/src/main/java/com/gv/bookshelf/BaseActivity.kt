package com.gv.bookshelf


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gv.bookshelf.databinding.ActivityBaseBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BaseActivity : AppCompatActivity(){
    val TAG = "BASEACTIVITY"

    lateinit var navigation: NavController

    private lateinit var binding: ActivityBaseBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //find fragment by Tag
        navigation =
            (supportFragmentManager.findFragmentByTag("fragment_sheet_home") as NavHostFragment).findNavController()
        binding.bottomNavigationView.setupWithNavController(navigation)
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navigation)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navigation.navigateUp() || super.onSupportNavigateUp()
    }
    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }

}

