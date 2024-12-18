package com.example.sceneformar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    lateinit var appBar: AppBarLayout
    var prev: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // Set status bar color to white
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        super.onCreate(savedInstanceState)
        // Inflating the activity_main layout
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.lytDrawer)
        coordinatorLayout = findViewById(R.id.lytCoordinator)
        toolbar = findViewById(R.id.wdgToolbar)
        frameLayout = findViewById(R.id.lytFrame)
        navigationView = findViewById(R.id.vwNavigation)

        // Setting up the custom toolbar
        setUpToolbar()

        appBar = findViewById(R.id.lytAppBar)

        // Setting up the navigation drawer toggle
        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        // Syncing the hamburger and back button during drawer motion
        actionBarDrawerToggle.syncState()

        // Replacing the home fragment during app launch
        replaceFragment(HomeFragment(), "Home")

        // Listener for navigationView items
        navigationView.setNavigationItemSelectedListener {
            appBar.setExpanded(true)

            prev?.isChecked = false
            it.isCheckable = true
            it.isChecked = true
            prev = it

            when (it.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment(), "Home")
                    drawerLayout.closeDrawers()
                }
                R.id.website -> {
                    openMetamaskApp()
                }
                R.id.scanner -> {
                    replaceFragment(ScanFragment(), "AR-QR Scanner")
                    drawerLayout.closeDrawers()
                }
                R.id.order -> {
                    val intent = Intent(this, Order::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }

    // Function to replace fragments dynamically
    private fun replaceFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.lytFrame, fragment)
            .addToBackStack(null)
            .commit()
        supportActionBar?.title = title
    }

    // Function to set up the toolbar
    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Study Bear"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Handling website item click and checking for Metamask app
    private fun openMetamaskApp() {
        val appName = "Metamask"
        val packageName = "io.metamask"
        var intent = packageManager.getLaunchIntentForPackage(packageName)

        if (intent != null) {
            // Metamask is installed, open the URL
            Toast.makeText(this, "Set Metamask app as your default browser for transactions.", Toast.LENGTH_LONG).show()
            intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://flipkartgrid.vercel.app"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(Intent.createChooser(intent, "USE METAMASK ONLY"))
        } else {
            // Metamask not found, redirect to the Play Store
            intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            Toast.makeText(this, "$appName app is not found.", Toast.LENGTH_SHORT).show()
            intent.data = Uri.parse("market://details?id=$packageName")
            startActivity(intent)
        }
    }

    // Handling options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Handling actions from the options menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            R.id.shareButton -> {
                replaceFragment(ScanFragment(), "AR-QR Scanner")
                supportActionBar?.title = "AR-QR Scanner"
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
