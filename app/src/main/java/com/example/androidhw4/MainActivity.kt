package com.example.androidhw4

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.widget.addTextChangedListener
import com.example.androidhw4.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception


private const val NONE = -1
private var name = ""
private var phone = ""
private var gender = ""
private var progress = 0
var flag = false

class MainActivity : AppCompatActivity() {

    lateinit var linearProgressLayout: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nameEditText = findViewById<EditText>(R.id.inputName)
        val phoneEditText = findViewById<EditText>(R.id.inputPhone)
        val buttonRegistration = findViewById<Button>(R.id.buttonRegistration)
        val mySwitch = findViewById<SwitchCompat>(R.id.notifications)
        val notification_1 = findViewById<CheckBox>(R.id.notification_1)
        val notification_2 = findViewById<CheckBox>(R.id.notification_2)

        linearProgressLayout = findViewById(R.id.progressBar)
        binding.valueProgress.text = progress.toString()
        findViewById<EditText>(R.id.inputName)

        findViewById<Button>(binding.buttonTheme.id).setOnClickListener {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }



        val radioGroup = findViewById<RadioGroup>(binding.radioGroup.id)
        radioGroup.setOnCheckedChangeListener { _, buttonId ->
            when (buttonId) {
                binding.radioButtonOne.id -> showSnackbar("Мужской")
                binding.radioButtonTwo.id -> showSnackbar("Женский")
                NONE -> showSnackbar("Очищено")
            }
        }

        mySwitch.isChecked = true
        notification_1.isChecked = true
        notification_2.isChecked = true
        setProgeressValue(40)

        mySwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Switch включен
                binding.notification1.isEnabled = true
                binding.notification2.isEnabled = true
            } else {
                // Switch выключен
                notification_1.isChecked = false
                notification_2.isChecked = false
                binding.notification1.isEnabled = false
                binding.notification2.isEnabled = false
            }
        }

        notification_1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                setProgeressValue(20)
            } else {
                setProgeressValue(-20)
            }
        }

        notification_2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setProgeressValue(20)
            } else {
                setProgeressValue(-20)

            }
        }

        buttonRegistration.setOnClickListener {
            val user = reg(name, phone, gender)

            name = nameEditText.text.toString()
            phone = phoneEditText.text.toString()
            if (name.isNotBlank() && phone.isNotBlank() && checkNotification()) {
                Toast.makeText(
                    this,
                    "Пользователь ${user.getName()} зарегистрирован",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, "Ошибка!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Метод проверяет чтобы если активен переключатель, должен быть выбран хотя бы один вид уведомлений.
     */
    fun checkNotification(): Boolean {
        if (findViewById<SwitchCompat>(R.id.notifications).isChecked) {
            return findViewById<CheckBox>(R.id.notification_1).isChecked || findViewById<CheckBox>(R.id.notification_2).isChecked
        } else {
            return true
        }
    }

    private fun showSnackbar(message: String) {
        gender = message
        if (!flag) {
            flag = true
            setProgeressValue(20)
        }
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }

    private fun reg(name: String, phone: String, gender: String): User {
        return User(name, phone, gender)
    }

    fun setProgeressValue(p: Int) {
        progress += p
        linearProgressLayout.setProgress(progress)
        findViewById<TextView>(R.id.valueProgress).text = progress.toString()
    }

    private fun sleep (mills: Long){
        try {
            Thread.sleep(mills);
        } catch (e: Exception){
            throw RuntimeException(e);
        }
    }
}

