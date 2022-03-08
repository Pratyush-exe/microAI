package com.adreal.mldroid.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.adreal.mldroid.MainActivity
import com.adreal.mldroid.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class LoginActivity : BaseActivity() {


    private lateinit var auth: FirebaseAuth
    //    var textName: EditText?= null
    var email: EditText? = null
    var password: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        auth = FirebaseAuth.getInstance()
        email= findViewById(R.id.emailLogin)
        password = findViewById(R.id.passLogin)


        val login: Button = findViewById<Button>(R.id.loginBtn)
        login.setOnClickListener {

            loginUser()
        }
        val register: TextView = findViewById(R.id.textView4)
        register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

    }

    private fun validateRegisterDetails(): Boolean{

        return when{
            email?.text.toString().isEmpty() ->{
                showErrorSnackBar(resources.getString(R.string.error_msg_enter_first_name), true)

                false
            }
            else -> {
                showErrorSnackBar(resources.getString(R.string.registerSuccess), false)

                Toast.makeText(this, "You are successfully logged in", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
        }
    }

    private fun loginUser() {
        val emailTxt= email?.text.toString()
        val pass = password?.text.toString()
        if (emailTxt.isNotEmpty() && pass.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(emailTxt, pass).await()
                    withContext(Dispatchers.Main) {
                        validateRegisterDetails()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}