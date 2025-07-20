package com.vharya.latihan

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    private lateinit var textWelcome: TextView
    private lateinit var buttonLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefs = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        val authUsername = prefs.getString("auth_username", "")

        textWelcome = findViewById(R.id.text_welcome)
        buttonLogout = findViewById(R.id.button_logout)

        textWelcome.text = "Welcome back, ${authUsername}!"

        buttonLogout.setOnClickListener {
            prefs.edit {
                clear()
            }
            finish()
        }
    }
}