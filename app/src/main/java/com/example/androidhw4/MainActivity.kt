package com.example.androidhw4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.androidhw4.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val NONE = -1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val emailEditText = findViewById<EditText>(R.id.name)
        val passwordEditText = findViewById<EditText>(R.id.phone)
//        val loginButton = findViewById<Button>(R.id.button_login)


        findViewById<Button>(binding.buttonTheme.id).setOnClickListener {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }


//        loginButton.setOnClickListener {
//            val email = emailEditText.text
//            val password = passwordEditText.text
//            if (email.isNotBlank() && password.isNotBlank()) {
//                Toast.makeText(this, "Успешно", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Ошибка!", Toast.LENGTH_SHORT).show()
//            }
//        }


        val radioGroup = findViewById<RadioGroup>(binding.radioGroup.id)
        radioGroup.setOnCheckedChangeListener { _, buttonId ->
            when (buttonId) {
                binding.radioButtonOne.id -> showSnackbar("Мужской")
                binding.radioButtonTwo.id -> showSnackbar("Женский")
                NONE -> showSnackbar("Очищено")
            }
        }

//        findViewById<Button>(binding.button.id).setOnClickListener {
//            radioGroup.clearCheck()
//        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
}