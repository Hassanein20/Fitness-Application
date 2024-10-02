package com.example.fitness

import Manager.Tools.iTools
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fitness.databinding.ActivitySignup0Binding

class Signup0 : AppCompatActivity() {

    private lateinit var b: ActivitySignup0Binding
    private val i = iTools(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySignup0Binding.inflate(layoutInflater)
        setContentView(b.root)

        val remember: Boolean = true

        b.next.setOnClickListener {

            val user = b.username.text.toString()
            val pass = b.Pass.text.toString()

            val users = i.ReadUsers()
            if ((user != "") || (pass != "")) {
                var x = 0
                for (User in users) {
                    if (User.user == user) {
                        x = 1
                    }
                }
                if (x == 0) {
                    i.AppendFile(this, "$user\t$pass\t$remember\n", "Credentials")
                    val b = Bundle()
                    b.putString("username", user)
                    i.openActivityB(SignUp::class,b,"user")
                } else {
                    i.ToastS("UserName is already used, pickup another one")
                    b.username.requestFocus()
                }
            } else {
                i.ToastS("Please fill the above Credentials")
            }
        }

    }
}