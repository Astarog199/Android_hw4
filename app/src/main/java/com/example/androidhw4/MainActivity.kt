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
private var flag = false

class MainActivity : AppCompatActivity() {

    private lateinit var linearProgressLayout: ProgressBar  //Инициализация ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nameEditText = findViewById<EditText>(R.id.inputName)     // Инициализация текстовое поле "имя"
        val phoneEditText = findViewById<EditText>(R.id.inputPhone)  //Инициализация текстовое поле "телефон"
        val buttonRegistration = findViewById<Button>(R.id.buttonRegistration) //Инициализация кнопки "сохранить"
        val mySwitch = findViewById<SwitchCompat>(R.id.notifications)    // Инициализация Switch включает/выключает уведомления
        val notification_1 = findViewById<CheckBox>(R.id.notification_1) // Инициализация CheckBox уведомление 1
        val notification_2 = findViewById<CheckBox>(R.id.notification_2) // Инициализация CheckBox уведомление 2

        linearProgressLayout = findViewById(R.id.progressBar)
        binding.valueProgress.text = progress.toString() //обновляет значение valueProgress
        findViewById<EditText>(R.id.inputName)

        findViewById<Button>(binding.buttonTheme.id).setOnClickListener {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        /**
         * Выбор пола
         */
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

        /**
         * Уведомление 1
         */
        notification_1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                setProgeressValue(20)
            } else {
                setProgeressValue(-20)
            }
        }

        /**
         * Уведомление 2
         */
        notification_2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setProgeressValue(20)
            } else {
                setProgeressValue(-20)

            }
        }
        /**
         * Слушатель клика для кнопки сохранить id.buttonRegistration
         */
        buttonRegistration.setOnClickListener {
            name = nameEditText.text.toString()
            phone = phoneEditText.text.toString()
            if (name.isNotBlank() && phone.isNotBlank() && checkNotification()) {
                val user = reg(name, phone, gender) // создаём объект пользователя
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

    /**
     * Метод выводит полученое сообщение
     */
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

    /**
     * Метод обновляет ProgressBar
     */
    fun setProgeressValue(p: Int) {
        progress += p
        linearProgressLayout.setProgress(progress)
        findViewById<TextView>(R.id.valueProgress).text = progress.toString() //обновляет значение valueProgress
    }

    private fun sleep (mills: Long){
        try {
            Thread.sleep(mills);
        } catch (e: Exception){
            throw RuntimeException(e);
        }
    }
}

