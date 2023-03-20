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
import com.fitverse.app.model.UserModel
import com.fitverse.app.model.UserPreference
import com.fitverse.app.response.LoginResponse
import com.fitverse.app.view.main.MainActivity
import com.fitverse.app.view.profile.ProfileFragment
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

            binding.loginButton.setOnClickListener {
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                when {
                    email.isEmpty() -> {
                        binding.emailEditTextLayout.error = "Masukkan email"
                    }
                    password.isEmpty() -> {
                        binding.passwordEditTextLayout.error = "Masukkan password"
                    }
                    else -> {
                        showLoading(true)
                        ApiConfig.getApiService().login(email,password)
                            .enqueue(object : Callback<LoginResponse> {

                                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                    Log.d("failure: ", t.message.toString())
                                }

                                override fun onResponse(
                                    call: Call<LoginResponse>,
                                    response: Response<LoginResponse>
                                ) {
                                    if (response.body()?.error == false) {
                                        showLoading(false)

                                        Toast.makeText(applicationContext,(response.body()?.message), Toast.LENGTH_SHORT).show()

                                        val body = response.body()?.data as UserModel

                                        loginViewModel.saveUser(UserModel(body.id, body.name, body.token, true))

                                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                        intent.putExtra("nama_user", body.name)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        finish()

                                    } else if (response.body()?.error == true){
                                        showLoading(false)

                                        Toast.makeText(this@LoginActivity,("Login failed. Cek Email/Password Anda"), Toast.LENGTH_SHORT).show()

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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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