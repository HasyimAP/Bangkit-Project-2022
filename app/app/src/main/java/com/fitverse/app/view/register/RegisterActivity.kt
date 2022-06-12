package com.fitverse.app.view.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.Toast
import com.fitverse.app.R
import com.fitverse.app.api.ApiConfig
import com.fitverse.app.databinding.ActivityRegisterBinding
import com.fitverse.app.response.GeneralResponse
import com.fitverse.app.view.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var gender: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupView()
        setupAction()
        playAnimation()
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.radio_male ->
                    if (checked) {
                        gender = "Laki-Laki"
                    }
                R.id.radio_female ->
                    if (checked) {
                        gender = "Perempuan"
                    }
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.registerButton.isEnabled = binding.passwordEditText.text?.length!! >= 6
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.registerButton.isEnabled = binding.passwordEditText.text?.length!! >= 6
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.registerButton.isEnabled = binding.passwordEditText.text?.length!! >= 6
            }

        })
        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val gender = gender

            when {
                name.isEmpty() -> {
                    binding.nameEditTextLayout.error = "Masukkan email"
                }
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Masukkan password"
                }
                gender.isEmpty() -> {
                    binding.jenisKelaminTextView.error = "Pilih salah satu"
                }
                else -> {
                    showLoading(true)
                    ApiConfig.getApiService().register(name, email, password, gender)
                        .enqueue(object: Callback<GeneralResponse> {
                            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
//                                binding.pbSignup.visibility = View.INVISIBLE
                                Log.d("failure: ", t.message.toString())
                            }
                            override fun onResponse(
                                call: Call<GeneralResponse>,
                                response: Response<GeneralResponse>
                            ) {
                                if (response.code() == 200) {
                                    showLoading(false)
//                                    binding.pbSignup.visibility = View.INVISIBLE
                                    Toast.makeText(applicationContext, ("User Created, Silahkan login kembali"), Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    showLoading(false)
//                                    binding.pbSignup.visibility = View.INVISIBLE
                                    Toast.makeText(applicationContext, ("Invalid Input, Cek kembali data anda"), Toast.LENGTH_SHORT).show()
                                }
                            }

                        })
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(300)
        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(300)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(300)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(300)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val jenisKelaminTextView = ObjectAnimator.ofFloat(binding.jenisKelaminTextView, View.ALPHA, 1f).setDuration(300)
        val radioGroup = ObjectAnimator.ofFloat(binding.radioGroup, View.ALPHA, 1f).setDuration(300)
        val register = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(300)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                jenisKelaminTextView,
                radioGroup,
                register
            )
            startDelay = 300
        }.start()
    }


}