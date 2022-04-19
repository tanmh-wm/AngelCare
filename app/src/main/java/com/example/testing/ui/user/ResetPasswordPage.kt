package com.example.testing.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testing.R
import com.example.testing.databinding.ActivityResetPasswordPageBinding


class ResetPasswordPage : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password_page)

        binding = ActivityResetPasswordPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ButtonBackReset.setOnClickListener {
            val intent = Intent(this@ResetPasswordPage, LoginPage::class.java)
            startActivity(intent)

            val pass = getIntent().getStringExtra("gmail")

        }
    }
}