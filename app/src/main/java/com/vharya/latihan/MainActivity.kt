package com.vharya.latihan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vharya.latihan.database.UsersAdapter
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {
    private lateinit var inputUsername: EditText
    private lateinit var inputPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button

    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefs = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        val isAuth = prefs.getString("auth_username", "") ?: ""

        if (isAuth.isNotEmpty()) {
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        usersAdapter = UsersAdapter(this)

        inputUsername = findViewById(R.id.input_username)
        inputPassword = findViewById(R.id.input_password)
        buttonRegister = findViewById(R.id.button_register)
        buttonLogin = findViewById(R.id.button_login)

        buttonLogin.setOnClickListener {
            val username = inputUsername.text.toString()
            val password = inputPassword.text.toString()

            try {
                val isAuth = usersAdapter.login(username, password)
                if (!isAuth) return@setOnClickListener

                prefs.edit {
                    putString("auth_username", username);
                    apply()
                }

                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
            } catch (error: Exception) {
                Log.e("ERROR DATABASE", "${error.message}")
            }
        }

        buttonRegister.setOnClickListener {
            val username = inputUsername.text.toString()
            val password = inputPassword.text.toString()

            try {
                usersAdapter.register(username, password)
                Toast.makeText(
                    this,
                    "Sucessfully Registered!",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (error: Exception) {
                Log.e("ERROR DATABASE", "${error.message}")
            }
        }
    }
}