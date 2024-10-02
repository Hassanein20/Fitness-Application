package com.example.fitness

import Manager.Tools.iTools
import android.os.Bundle
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.example.fitness.databinding.ActivitySignUpBinding


class SignUp : AppCompatActivity() {
    val i = iTools(this)
    private lateinit var s: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        s = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(s.root)


        s.SeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when (progress) {
                    0 -> {
                        s.activityTitle.text = getString(R.string.sedentary)
                        s.activity.text = getString(R.string.little_or_no_exercise)
                    }

                    1 -> {
                        s.activityTitle.text = getString(R.string.lightly_active)
                        s.activity.text = getString(R.string.sports_1_3_days)
                    }

                    2 -> {
                        s.activityTitle.text = getString(R.string.moderately_active)
                        s.activity.text = getString(R.string.sports_3_5_days_week)
                    }

                    3 -> {
                        s.activityTitle.text = getString(R.string.very_active)
                        s.activity.text = getString(R.string.sports_6_7_days_week)
                    }

                    4 -> {
                        s.activityTitle.text = getString(R.string.extremely_active)
                        s.activity.text = getString(R.string.training_twice_a_day)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //when touched
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //when the touch is released
            }
        })

        s.Next.setOnClickListener{
            if((s.Name.text != null) && (s.Age.text != null) && (s.Weight.text != null) && (s.Height.text != null)){
                val b = Bundle()
                val n = intent.getBundleExtra("user")
                b.putString("username",n?.getString("username"))
                b.putString("name",s.Name.text.toString())
                b.putInt("age", s.Age.text.toString().toInt())
                b.putDouble("height", s.Height.text.toString().toDouble())
                b.putDouble("weight", s.Weight.text.toString().toDouble())
                val rd = findViewById<RadioButton>(s.rdGender.checkedRadioButtonId)
                b.putString("gender", rd.text.toString())
                b.putString("activity", s.activityTitle.text.toString())
                i.openActivityB(Signup1::class,b,"Data")

                // we can also share classes using putExtra but we need to iniatilize the class as Serializable
                //I iniatilize the UData class but in this case I can't make a data class right now
                //www.youtube.com/watch?v=IWXYV1dC2FQ&list=PLQkwcJG4YTCTq1raTb5iMuxnEB06J1VHX&index=15

            }else{
                i.ToastS("fill all the above data please")
            }
        }




    }
}