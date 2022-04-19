package com.example.testing.ui.user

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.testing.MainActivity
import com.example.testing.R
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        val btnForgetPassword: Button = findViewById(R.id.ButtonForgetPassword)
        val btnSignUp: Button = findViewById(R.id.buttonSignUp)
        val btnLogin: Button = findViewById(R.id.buttonLogin)
        val txtEmail: TextView = findViewById(R.id.loginGmail)
        val txtPwd: TextView = findViewById(R.id.loginPassword)

        btnForgetPassword.setOnClickListener {
            val intent = Intent(this@LoginPage, ResetPasswordPage::class.java)
            startActivity(intent)
        }
        btnSignUp.setOnClickListener {
            val intent = Intent(this@LoginPage, SignUpPage::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            if(txtEmail.text.isEmpty()){
                txtEmail.error = "Invalid Gmail"
            }else if(txtPwd.text.isEmpty()){
                txtPwd.error = "Invalid Password"
            }else{
                checkEmailPsswd(txtEmail.text.toString(), txtPwd.text.toString())
            }

        }
    }

    private fun checkEmailPsswd(email: String, pwd: String) {
        auth.signInWithEmailAndPassword(email, pwd).addOnSuccessListener {
            Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginPage, MainActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener() {
            Toast.makeText(applicationContext, "fail", Toast.LENGTH_SHORT).show()
        }
    }
}
