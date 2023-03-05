package com.fitpeo.task.base

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.fitpeo.task.R
import com.fitpeo.task.utils.applyTheme
import com.fitpeo.task.utils.isDarkTheme

abstract class BaseActivity(): AppCompatActivity() {

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_uimode -> {
                val uiMode = if (isDarkTheme()) {
                    AppCompatDelegate.MODE_NIGHT_NO
                } else {
                    AppCompatDelegate.MODE_NIGHT_YES
                }
                applyTheme(uiMode)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

}