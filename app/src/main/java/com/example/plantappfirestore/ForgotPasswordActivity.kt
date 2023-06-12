package com.example.plantappfirestore

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.plantappfirestore.databinding.ActivityForgotPasswordBinding
import com.example.plantappfirestore.databinding.ActivityIntroProfileBinding
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class ForgotPasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindComponent()
        bindEvent()
    }

    private fun bindComponent() {

    }

    private fun bindEvent() {
        binding.btnForgot.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        if (binding.edtEmail.text.toString().isEmpty()) {
            binding.edtEmail.error = "Email is required"
            binding.edtEmail.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.edtEmail.text.toString()).matches()) {
            binding.edtEmail.error = "Please provide valid email! @gmail.com?"
            binding.edtEmail.requestFocus()
        } else {
            FirebaseAuth.getInstance().sendPasswordResetEmail(binding.edtEmail.text.toString().trim()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Check your email to request your password!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Try again! Something wrong happened!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}