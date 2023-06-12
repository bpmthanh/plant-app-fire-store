package com.example.plantappfirestore

import android.content.Intent
import android.os.Bundle
import android.text.Spanned
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.example.plantappfirestore.databinding.ActivityLogInBinding
import com.example.plantappfirestore.extensions.hide
import com.example.plantappfirestore.extensions.show
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding

    private var mAuth: FirebaseAuth? = null
    private var email: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater);
        setContentView(binding.root)

        bindComponent()
        bindData()
        bindEvent()
    }

    private fun bindComponent() {
        mAuth = FirebaseAuth.getInstance()

        binding.tvDontHaveAccount.text = getTitleVoucherInHtml(
            text = "Don’t Have Account? ",
            textColor = "Sign Up",
            R.color.light_green
        )
    }

    private fun bindData() {

    }

    private fun bindEvent() {
        binding.tvDontHaveAccount.setOnClickListener {
            startActivity(Intent(applicationContext, SignUpActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            //lấy text từ editext khi người dùng nhập vào
            email = binding.edtEmail.text.toString().trim()
            password = binding.edtPassword.text.toString().trim()
            //nếu đã nhập email && nhập password && có điền @gmail.com thì chui vô hàm checklogin để xử lý đăng nhập trên firebase
            if (!email.isEmpty() && !password.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.backgroundLoading.show()
                binding.progressBar.show()
                checkLogin(email, password)
            } else {
                //nếu không nhập email thì show lỗi và yêu cầu nhâp
                if (email.isEmpty()) {
                    binding.edtEmail.error = "Email is required!"
                    binding.edtEmail.requestFocus()
                }
                //nếu không nhập @gmail.com thì show lỗi và yêu cầu nhâp
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.edtEmail.error = "Please provide valid email! @gmail.com?"
                    binding.edtEmail.requestFocus()
                }
                //nếu không nhập password thì show lỗi và yêu cầu nhâp
                else if (password.isEmpty()) {
                    binding.edtPassword.error = "Password is required!"
                    binding.edtPassword.requestFocus()
                }
                //nếu chiều dài password < 6 thì show lỗi và yêu cầu nhâp
                else if (password.length < 6) {
                    binding.edtPassword.error = "Passwords must have at least 6 characters!"
                    binding.edtPassword.requestFocus()
                }
            }
        }

        binding.ivShowPassword.setOnClickListener {
            if (binding.edtPassword.transformationMethod is PasswordTransformationMethod) {
                binding.edtPassword.transformationMethod = null
                binding.ivShowPassword.background = ContextCompat.getDrawable(applicationContext, R.drawable.ic_hide_password
                )
            } else {
                binding.edtPassword.transformationMethod = PasswordTransformationMethod()
                binding.ivShowPassword.background = ContextCompat.getDrawable(applicationContext, R.drawable.ic_show_password)
            }
        }

        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(applicationContext, ForgotPasswordActivity::class.java))
        }
    }

    private fun checkLogin(email: String, password: String) {
        //kiểm tra đăng nhập với thông tin là email và password người dùng nhập
        mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener { task ->
            try {
                if (task.isSuccessful) {
                    binding.backgroundLoading.hide()
                    binding.progressBar.hide()
                    var user = FirebaseAuth.getInstance().currentUser
                    if (user?.isEmailVerified == true) {
                        val intentUser = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intentUser)
                        finish()
                    } else {
                        user?.sendEmailVerification()
                        Toast.makeText(applicationContext, "Check email to verify!!!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    binding.backgroundLoading.hide()
                    binding.progressBar.hide()
                    Toast.makeText(applicationContext, "LOGIN FAILED!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                binding.backgroundLoading.hide()
                binding.progressBar.hide()
                Toast.makeText(applicationContext, "ERROR!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getTitleVoucherInHtml(text: String, textColor: String, color: Int): Spanned {
        return HtmlCompat.fromHtml("$text <font color='${ContextCompat.getColor(applicationContext, color)}'>$textColor</font>", HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}