package com.example.fitness

import Manager.Tools.iTools
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.fitness.databinding.ActivitySignup1Binding

class Signup1 : AppCompatActivity() {

    private lateinit var b: ActivitySignup1Binding
    private var i = iTools(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySignup1Binding.inflate(layoutInflater)
        setContentView(b.root)

        b.rdgroup.setOnCheckedChangeListener { group, checkedId -> function() }

        b.Seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val rd = findViewById<RadioButton>(b.rdgroup.checkedRadioButtonId)
                if (rd.text == getString(R.string.lose_weight)) {
                    when (progress) {
                        0 -> b.calories.text = "-200"
                        1 -> b.calories.text = "-300"
                        2 -> b.calories.text = "-400"
                        3 -> b.calories.text = "-500"
                    }
                } else if (rd.text == getString(R.string.gain_weight)) {
                    when (progress) {
                        0 -> b.calories.text = "200"
                        1 -> b.calories.text = "300"
                        2 -> b.calories.text = "400"
                        3 -> b.calories.text = "500"
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        b.Next.setOnClickListener {
            val rdbutton = findViewById<RadioButton>(b.rdgroup.checkedRadioButtonId)
            val bun = intent.getBundleExtra("Data")
            val goal = rdbutton.text.toString()
            val username = bun?.getString("username")
            val name = bun?.getString("name")
            val age = bun?.getInt("age")
            val weight = bun?.getDouble("weight")
            val height = bun?.getDouble("height")
            val gender = bun?.getString("gender")
            val activity = bun?.getString("activity")
            val caloriesV = b.calories.text.toString().toInt()


            val udata = UData(
                username!!,
                name!!,
                age!!,
                weight!!,
                height!!,
                goal,
                activity!!,
                gender!!,
                caloriesV
            )
            val u = udata.username + "Data"
            i.AppendFile(this, udata.toString(), u)
            Intent(this as Context, Daily::class.java).also {
                it.putExtra("username",udata.username)
                ContextCompat.startActivity(this as Context, it, null)
            }
        }
    }

    fun function() {
        val rd = findViewById<RadioButton>(b.rdgroup.checkedRadioButtonId)
        when (rd.text) {
            getString(R.string.lose_weight) -> {
                when (b.Seekbar.progress) {
                    0 -> b.calories.text = "-200"
                    1 -> b.calories.text = "-300"
                    2 -> b.calories.text = "-400"
                    3 -> b.calories.text = "-500"
                }
            }

            getString(R.string.gain_weight) -> {
                when (b.Seekbar.progress) {
                    0 -> b.calories.text = "200"
                    1 -> b.calories.text = "300"
                    2 -> b.calories.text = "400"
                    3 -> b.calories.text = "500"
                }
            }

            else -> {
                b.calories.text = "0"
                b.Seekbar.progress = 0
            }
        }
    }
}