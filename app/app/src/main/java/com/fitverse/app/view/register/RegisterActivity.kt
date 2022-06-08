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
import androidx.appcompat.app.AlertDialog
import com.fitverse.app.R
import com.fitverse.app.api.ApiConfig
import com.fitverse.app.databinding.ActivityLoginBinding
import com.fitverse.app.databinding.ActivityRegisterBinding
import com.fitverse.app.model.RegisterModel
import com.fitverse.app.response.GeneralResponse
import com.fitverse.app.view.login.LoginActivity
import com.fitverse.app.view.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var jenisKelamin: String = ""

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
                        jenisKelamin = "Laki-Laki"
                    }
                R.id.radio_female ->
                    if (checked) {
                        jenisKelamin = "Perempuan"
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
            val nama_user = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val jenis_kelamin = jenisKelamin

            when {
                nama_user.isEmpty() -> {
                    binding.nameEditTextLayout.error = "Masukkan email"
                }
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Masukkan password"
                }
                jenis_kelamin.isEmpty() -> {
                    binding.jenisKelaminTextView.error = "Pilih salah satu"
                }
                else -> {
                    ApiConfig.getApiService().register(nama_user, email, password, jenis_kelamin)
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
//                                    binding.pbSignup.visibility = View.INVISIBLE
                                    Toast.makeText(applicationContext, ("user_created"), Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
//                                    binding.pbSignup.visibility = View.INVISIBLE
                                    Toast.makeText(applicationContext, ("invalid_input"), Toast.LENGTH_SHORT).show()
                                }
                            }

                        })
                }
            }
        }
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