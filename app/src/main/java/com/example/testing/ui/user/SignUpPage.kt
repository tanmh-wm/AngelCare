package com.example.testing.ui.user

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.testing.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpPage : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var progressDialog: ProgressDialog
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating account")
        progressDialog.setCanceledOnTouchOutside(false)


        val btnConfirm: Button = findViewById(R.id.buttonSignUpConfirm)
        val txtEmail: TextView = findViewById(R.id.signUpGmail)
        val txtUsername: TextView = findViewById(R.id.signUpUsername)
        val txtPhoneNo: TextView = findViewById(R.id.signUpPhone)
//        val txtStatus:TextView=findViewById(R.id.signUpGmail)
        val txtOkuId: TextView = findViewById(R.id.signUpOKUId)
        val txtPwd: TextView = findViewById(R.id.signUpPassword)
        val txtConfirmPwd: TextView = findViewById(R.id.signUpConfirmPassword)
        val txtYes: RadioButton = findViewById(R.id.radioButtonYes)
        val txtNo: RadioButton = findViewById(R.id.radioButtonNo)
        val btnBack: ImageButton = findViewById(R.id.buttonBackSignUp)

        txtOkuId.visibility = View.GONE

        txtYes.setOnClickListener() {
            txtOkuId.visibility = View.VISIBLE
        }

        txtNo.setOnClickListener() {
            txtOkuId.visibility = View.GONE
        }
        btnBack.setOnClickListener {
            val intent = Intent(this@SignUpPage, LoginPage::class.java)
            startActivity(intent)
        }


        btnConfirm.setOnClickListener {
            if (txtEmail.text.isEmpty()) {
                txtEmail.error = "Invalid Email Address"
                return@setOnClickListener
            } else if (txtUsername.text.isEmpty()) {
                txtUsername.error = "Invalid User name"
                return@setOnClickListener
            } else if (txtPwd.text.isEmpty() || txtPwd.text.length < 6) {
                txtPwd.error = "Invalid password, the password must at least 6 "
                return@setOnClickListener
            } else if (txtConfirmPwd.text.isEmpty()) {
                txtConfirmPwd.error = "Invalid confirm password"
                return@setOnClickListener
            } else if (txtPhoneNo.text.isEmpty()) {
                txtPhoneNo.error = "Invalid Phone Number"
                return@setOnClickListener
            } else if (txtYes.visibility == View.VISIBLE && (txtOkuId.text.isEmpty() || txtOkuId.text.length != 6)) {
                    txtOkuId.error = "Invalid OKU ID, the ID must digit"
                    return@setOnClickListener
            } else {
                if (txtPwd.text.toString().equals(txtConfirmPwd.text.toString())) {
                    createUser(txtEmail.text.toString(), txtConfirmPwd.text.toString())
                    //saveData()
                } else {
                    txtConfirmPwd.error = "Password not match"
                    return@setOnClickListener
                }
            }
        }
    }

    private fun createUser(email: String, pwd: String) {
        auth.createUserWithEmailAndPassword(email, pwd).addOnSuccessListener {
            login(email, pwd)
            saveData()

        }.addOnFailureListener() {
            Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun login(email: String, pwd: String) {
        auth.signInWithEmailAndPassword(email, pwd).addOnSuccessListener {
            saveData()
        }.addOnFailureListener() {
            Toast.makeText(applicationContext, "cannot save information", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveData() {
        val txtEmail: TextView = findViewById(R.id.signUpGmail)
        val txtUsername: TextView = findViewById(R.id.signUpUsername)
        val txtPhoneNo: TextView = findViewById(R.id.signUpPhone)
//        val txtStatus:TextView=findViewById(R.id.signUpGmail)
        val txtOkuId: TextView = findViewById(R.id.signUpOKUId)

        val users = User(
            txtEmail.text.toString(),
            txtUsername.text.toString(),
            txtPhoneNo.text.toString(),
            txtOkuId.text.toString()
        )

        //database.push().setValue(users)
        if (auth.currentUser != null) {
            val currentUser = auth.currentUser?.uid
            val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("User")
            database.child(currentUser.toString()).setValue(users).addOnSuccessListener {
                Log.d("Apply Benefit Activity", "Successfully to save to firebase")
            }.addOnFailureListener {
                Log.d("Apply Benefit Activity", "Failed to save to firebase")
            }
        }

        auth.signOut()

        Log.d("signUpPage", users.toString())
        Toast.makeText(this, "successfully", Toast.LENGTH_SHORT).show()
        progressDialog.dismiss()
        val intent = Intent(this@SignUpPage, LoginPage::class.java)
        startActivity(intent)


    }

}