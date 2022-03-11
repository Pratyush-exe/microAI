package com.adreal.mldroid.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import com.adreal.mldroid.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class RegisterActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    var textName: EditText?= null
    var email: EditText? = null
    var password: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        auth = FirebaseAuth.getInstance()

        textName = findViewById(R.id.enterName)
        email= findViewById(R.id.enterEmail)
        password = findViewById(R.id.enterPassword)
        val login: TextView = findViewById(R.id.textView6)
        login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        val back: ImageButton = findViewById(R.id.imageButton)
        back.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        val btnRegister: Button = findViewById(R.id.button2)
        btnRegister.setOnClickListener {
//            registerUser()
//            Toast.makeText(this, "Hi", Toast.LENGTH_LONG).show()
            registerUser()


        }
    }

    private fun validateRegisterDetails(): Boolean{

        return when{
            textName?.text.toString().isEmpty() ->{
                showErrorSnackBar(resources.getString(R.string.error_msg_enter_first_name), true)
                false
            }
            else -> {
                showErrorSnackBar(resources.getString(R.string.registerSuccess), false)
                true
            }
        }
    }
//
//    private fun registerUser(){
//
//        // Check with validate function if the entries are valid or not.
//        if(validateRegisterDetails())
//        {
//            val emailText: String =  email?.text.toString().trim(){
//                it <= ' '
//            }
//            val passwordText: String = password?.text.toString().trim{
//                it <= ' '
//            }
//
//            // Create an instance and create a register a user with email and password.
//            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailText, passwordText)
//                .addOnCompleteListener(
//                    OnCompleteListener<AuthResult> { task ->
//                        // If the registration is successfully done
//                        if(task.isSuccessful){
//                            //Firebase registered user
//                            val firebaseUser: FirebaseUser = task.result!!.user!!
//
//                            showErrorSnackBar(
//                                "You are registered successfully. Your user id is ${firebaseUser.uid}",
//                                false
//                            )
//                        }
//                        else{
//                            // If the registering is not successful then show error message.
//                            showErrorSnackBar(task.exception!!.message.toString(), true)
//                        }
//                    }
//
//                )
//        }
//    }

    private fun registerUser(){
        val emailTxt= email?.text.toString()
        val name = textName?.text.toString()
        val pass = password?.text.toString()
        validateRegisterDetails()
        if(emailTxt.isNotEmpty() && pass.isNotEmpty() && name.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(emailTxt, pass).await()
                    withContext(Dispatchers.Main) {
                        validateRegisterDetails()
                    }
                }
                catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
}