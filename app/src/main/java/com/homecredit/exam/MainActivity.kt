package com.homecredit.exam

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.homecredit.exam.ui.main.DetailsFragment
import com.homecredit.exam.ui.main.MainFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        val f = supportFragmentManager.findFragmentById(R.id.container)
        when(f){
            is MainFragment -> {
                    finish()
                this.doubleBackToExitPressedOnce = true
            }

            is DetailsFragment ->{ supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment()).addToBackStack("tag").commit()}
            else ->{ super.onBackPressed() }
        }
    }
}