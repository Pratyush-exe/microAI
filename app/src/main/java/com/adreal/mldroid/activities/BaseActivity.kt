package com.adreal.mldroid.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.adreal.mldroid.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

    fun showErrorSnackBar(message: String, errorMessage: Boolean)
    {
        val snackbar= Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view

        if(errorMessage)
        {
            snackbarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.purple_200
                )
            )
        }
        else
        {
            snackbarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.teal_200
                )
            )
        }
        snackbar.show()
    }
}