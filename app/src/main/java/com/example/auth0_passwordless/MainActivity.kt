package com.example.auth0_passwordless

//import android.app.Dialog

//import com.auth0.android.provider.AuthCallback

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.volley.toolbox.Volley
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.PasswordlessType
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.provider.WebAuthProvider.login
import com.auth0.android.request.Request
import com.auth0.android.result.Authentication
import com.auth0.android.result.Credentials
import com.example.auth0_passwordless.databinding.ActivityMainBinding
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    lateinit var loginButton: Button
    lateinit var loginButtonAfterOtp: Button
    lateinit var editText: EditText
    lateinit var textView: TextView
    var emailValue: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        // setting up a Volley RequestQueue
//        val queue = Volley.newRequestQueue(this)

        // referencing the binding object of the view
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // loggedIn should be false by default to show the button
//        binding?.loggedIn = false


        // triggering the login method when the button is clicked
        loginButton = findViewById<Button>(R.id.login_button)
        textView = findViewById(R.id.tv)
        loginButtonAfterOtp = findViewById(R.id.login_button_after_otp)
        loginButtonAfterOtp.visibility = View.GONE

        editText = findViewById<EditText>(R.id.item)

        textView.visibility = View.GONE
        val account = Auth0("rotYO6RMfRsIaL6cF2UMFDzF4fFeY03P", "dev-25x9oi7h.us.auth0.com")
        val authentication = AuthenticationAPIClient(account)


        loginButton.text = "Send OTP"
        loginButton.setOnClickListener {
            if (editText.text.trim().toString().isNotEmpty()) sendOtp(
                authentication,
                editText.text.trim().toString()
            ) else Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show()
        }

        loginButtonAfterOtp.setOnClickListener {
            if (editText.text.trim().toString().isNotEmpty()) loginWithOtp(
                authentication,
                editText.text.trim().toString()
            ) else Toast.makeText(this, "Code cannot be empty", Toast.LENGTH_SHORT).show()
        }


////
////        // getting a reference for the ListView
////        val listToDo = findViewById<ListView>(R.id.list_todo)
//
//        // passing the activity, the queue and the ListView to the function
//        // that consumes the RESTful endpoint
//        getItems(this, queue, listToDo)


//        val addItemButton = findViewById<Button>(R.id.add_item)
//        addItemButton.setOnClickListener {
//            val item = itemEditText.text.toString()
//            getAccessToken(this)?.let { it1 ->
//                addItem(queue, item, it1) {
//                    itemEditText.text.clear()
//                    Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show()
//                    getItems(this, queue, listToDo)
//                }
//            }
//        }

    }

    private fun loginWithOtp(authentication: AuthenticationAPIClient, code: String) {
        loginButtonAfterOtp.text = "Sending......"
        authentication
            .loginWithEmail(emailValue, code, "email")
            .start(object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(exception: AuthenticationException) {
                    Toast.makeText(this@MainActivity, exception.toString(), Toast.LENGTH_SHORT)
                        .show()
                    loginButtonAfterOtp.text = "LOGIN"
                }

                override fun onSuccess(credentials: Credentials) {
                    Toast.makeText(
                        this@MainActivity,
                        " Congrats! You have been successfully loggedin and your creds are: ${credentials.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()


                    loginButtonAfterOtp.visibility = View.GONE

                    editText.visibility = View.GONE

                    textView.visibility = View.VISIBLE
                    textView.text =
                        "\nType: ${credentials.type}" + "\nScope: ${credentials.scope}" + "\nRecoveryCode: ${credentials.recoveryCode}" + "\n Accesstoken: ${credentials.accessToken}" + "\nRefreshToken: ${credentials.refreshToken}" + "\nExpiresat: ${credentials.expiresAt}" + "\nID Token: ${credentials.idToken}"
                }
            })
    }

    private fun sendOtp(authentication: AuthenticationAPIClient, email: String) {

        emailValue = email
        loginButton.text = "Sending......"
        authentication
            .passwordlessWithEmail(email, PasswordlessType.CODE, "email")
            .start(object : Callback<Void?, AuthenticationException> {
                override fun onFailure(exception: AuthenticationException) {
                    Toast.makeText(this@MainActivity, exception.toString(), Toast.LENGTH_SHORT)
                        .show()

                    loginButton.text = "Send OTP"
                }

                override fun onSuccess(result: Void?) {
                    Toast.makeText(this@MainActivity, "OTP sent successfully", Toast.LENGTH_SHORT)
                        .show()


                    editText.text.clear()
                    editText.hint = "Please enter the code recieved on mail here...."
                    loginButton.visibility = View.GONE
                    loginButtonAfterOtp.visibility = View.VISIBLE


                }
            })
//

    }
//
//    // Auth0 triggers an intent on a successful login
//    override fun onNewIntent(intent: Intent) {
//        if (WebAuthProvider.resume(intent)) {
//            return
//        }
//        super.onNewIntent(intent)
//    }
//
//    fun getAccessToken(context: Context): String? {
//        val sp = context.getSharedPreferences(
//            "auth0", Context.MODE_PRIVATE)
//
//        return sp!!.getString("access_token", null)
//    }

}
