package com.example.fitness

import Manager.Tools.iTools
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.fitness.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    val i = iTools(this)
    private lateinit var b: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val users = i.ReadUsers()
        for (user in users) {
            if (user.Remember) {
                Intent(this, Daily::class.java).also {
                    it.putExtra("username", user.user)
                    ContextCompat.startActivity(this, it, null)
                }
                break
            }
        }

        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)


        b.btnLogin.setOnClickListener {
            val name = b.username.text.toString()
            val pass = b.Pass.text.toString()
            val remember = b.cbxR.isChecked
            if (!i.checkCredentials(name, pass, remember, Daily::class)) {
                Cleartext()
            }
        }

        b.SignUp.setOnClickListener {
            i.openActivity(Signup0::class)
        }
    }

    private fun Cleartext() {
        b.username.text = null
        b.Pass.text = null
        b.cbxR.isChecked = false
        b.username.requestFocus()
    }

}