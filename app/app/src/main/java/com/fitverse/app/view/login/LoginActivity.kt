package com.fitverse.app.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.fitverse.app.ViewModelFactory
import com.fitverse.app.api.ApiConfig
import com.fitverse.app.databinding.ActivityLoginBinding
import com.fitverse.app.model.LoginModel
import com.fitverse.app.model.UserModel
import com.fitverse.app.model.UserPreference
import com.fitverse.app.response.LoginResponse
import com.fitverse.app.view.main.MainActivity
import com.fitverse.app.view.register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        playAnimation()

        loginViewModel = ViewModelProvider(this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
//                Toast.makeText(this, "${user.token}", Toast.LENGTH_SHORT).show()
            }
        }
        binding.apply {
            Register.setOnClickListener{
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java).apply {
                    startActivity(this)
                })
            }
            passwordEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    binding.loginButton.isEnabled = binding.passwordEditText.text?.length!! >= 6
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    binding.loginButton.isEnabled = binding.passwordEditText.text?.length!! >= 6
                }

                override fun afterTextChanged(p0: Editable?) {
                    binding.loginButton.isEnabled = binding.passwordEditText.text?.length!! >= 6
                }

            })
//            loginButton.setOnClickListener{
//                startActivity(Intent(this@LoginActivity, MainActivity::class.java).apply {
//                    startActivity(this)
//                })
//            }
            binding.loginButton.setOnClickListener {
                val email = binding.emailEditText.text.toString()
                val pass = binding.passwordEditText.text.toString()
                when {
                    email.isEmpty() -> {
                        binding.emailEditTextLayout.error = "Masukkan email"
                    }
                    pass.isEmpty() -> {
                        binding.passwordEditTextLayout.error = "Masukkan password"
                    }
                    else -> {
                        ApiConfig.getApiService().login(email,pass)
                            .enqueue(object : Callback<LoginResponse> {

                                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                    Log.d("failure: ", t.message.toString())
                                }

                                override fun onResponse(
                                    call: Call<LoginResponse>,
                                    response: Response<LoginResponse>
                                ) {
                                    if (response.code() == 200) {
                                        val body = response.body()?.loginResult as UserModel

                                        loginViewModel.saveUser(UserModel(body.id_user, body.email, body.pass,body.nama_user,body.jenis_kelamin, true))

//                                        Toast.makeText(applicationContext,("login success"), Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()

                                    } else {
                                        Toast.makeText(applicationContext,("login failed"), Toast.LENGTH_SHORT).show()

                                        Log.d(
                                            LoginActivity::class.java.simpleName,
                                            response.body()?.message.toString()
                                        )
                                    }
                                }
                            })
                    }
                }
            }
        }

    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(300)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(300)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(300)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(title, emailTextView, emailEditTextLayout, passwordTextView, passwordEditTextLayout, login)
            startDelay = 500
        }.start()
    }
}