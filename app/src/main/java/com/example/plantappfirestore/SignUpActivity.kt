package com.example.plantappfirestore

import android.os.Bundle
import android.text.InputFilter
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.plantappfirestore.databinding.ActivitySignUpBinding
import com.example.plantappfirestore.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private var mAuth: FirebaseAuth? = null
    private var email: String = ""
    private var password: String = ""
    private var name: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater);
        setContentView(binding.root)

        bindComponent()
        bindEvent()
    }

    private fun bindComponent() {
        mAuth = FirebaseAuth.getInstance()
        binding.edtPassword.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(11))
    }

    private fun bindEvent() {
        //sự kiện click đăng ký
        binding.btnSignUp.setOnClickListener(View.OnClickListener {
            //lấy text từ editext khi người dùng nhập vào
            email = binding.edtEmail.text.toString().trim()
            password = binding.edtPassword.text.toString().trim()
            name = binding.edtName.text.toString()
            //nếu đã nhập email &&
            //nhập password && nhập name &&
            // nhâp conformPassword &&
            // có điền @gmail.com thì chui vô hàm registerUser để xử lý đăng ký trên firebase
            if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty() && password.length == 11 && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                if (isValidPasswordFormat(password)) {
                    binding.backgroundLoading.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                    registerUser(email, password, name)
//                } else {
//                    Toast.makeText(applicationContext, "Please enter at least 1 digit, at least 1 lower case letter, at least 1 upper case letter", Toast.LENGTH_SHORT).show()
//                }
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
                else if (password.length < 11) {
                    binding.edtPassword.error = "Passwords must have at least 11 characters!"
                    binding.edtPassword.requestFocus()
                }
                //nếu không nhập name thì show lỗi và yêu cầu nhâp
                else if (name.isEmpty()) {
                    binding.edtName.error = "Name is required!"
                    binding.edtName.requestFocus()
                }
            }
        })

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
    }

    private fun registerUser(email: String, password: String, name: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //tạo object chứa 3 params email, password, name
                val user = User(email, password, name)
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser?.uid.toString()).setValue(user).addOnCompleteListener(
                    OnCompleteListener<Void?> { task ->
                    if (task.isSuccessful) {
                        binding.backgroundLoading.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        //nếu thành công thì quay về màn hình login để login
                        onBackPressed()
                        Toast.makeText(applicationContext, "REGISTER SUCCESSFULLY", Toast.LENGTH_SHORT).show()
                    } else {
                        binding.backgroundLoading.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(applicationContext, "REGISTER FAIL!", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                binding.backgroundLoading.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                Toast.makeText(applicationContext, "REGISTER FAILED!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidPasswordFormat(password: String): Boolean {
        val passwordREGEX = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{11,}" +               //at least 11 characters
                "$");
        return passwordREGEX.matcher(password).matches()
    }
}