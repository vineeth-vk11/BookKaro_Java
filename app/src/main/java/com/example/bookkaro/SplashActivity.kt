package com.example.bookkaro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        Handler().postDelayed({
            val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val firstStart = prefs.getBoolean("firstStart", true)
            if (firstStart) {
                val intent = Intent(this, IntroActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                if(user==null) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 1000)

    }
}
