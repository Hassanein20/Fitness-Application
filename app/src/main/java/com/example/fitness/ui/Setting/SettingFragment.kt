package com.example.fitness.ui.Setting

import Manager.Tools.iTools
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fitness.Daily
import com.example.fitness.R
import com.example.fitness.UData
import com.example.fitness.databinding.FragmentSettingBinding
import java.io.File
import java.io.FileWriter

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingViewModel =
            ViewModelProvider(requireActivity()).get(SettingViewModel::class.java)

        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val i = iTools(requireActivity().applicationContext)

        var gender: String = ""
        var username: String = ""
        settingViewModel.sharedData.observe(viewLifecycleOwner) {
            binding.name.text = it.name
            binding.Age.setText(it.age.toString())
            binding.Height.setText(it.height.toString())
            binding.Weight.setText(it.weight.toString())
            var prog = 0
            when (it.activitylevel) {
                "Sedentary" -> {
                    prog = 0
                }

                "Lightly active" -> {
                    prog = 1
                }

                "Moderately active" -> {
                    prog = 2
                }

                "Very active" -> {
                    prog = 3
                }

                "Extremely active" -> {
                    prog = 4
                }
            }
            binding.SeekBarA.progress = prog

            val goal = it.goal
            when (goal) {
                getString(R.string.lose_weight) -> binding.cbxLose.isChecked = true
                getString(R.string.maintain_weight) -> binding.cbxMaintain.isChecked = true
                getString(R.string.gain_weight) -> binding.cbxGain.isChecked = true
            }
            var progC = 0
            when (it.CalVariable) {
                200 -> progC = 0
                300 -> progC = 1
                400 -> progC = 2
                500 -> progC = 3
                -200 -> progC = 0
                -300 -> progC = 1
                -400 -> progC = 2
                -500 -> progC = 3

            }
            binding.SeekbarC.progress = progC
            gender = it.gender
            username = it.username
        }

        binding.rdgroup.setOnCheckedChangeListener { group, checkedId -> function() }
        binding.SeekBarA.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when (progress) {
                    0 -> {
                        binding.activityTitle.text = getString(R.string.sedentary)
                        binding.activity.text = getString(R.string.little_or_no_exercise)
                    }

                    1 -> {
                        binding.activityTitle.text = getString(R.string.lightly_active)
                        binding.activity.text = getString(R.string.sports_1_3_days)
                    }

                    2 -> {
                        binding.activityTitle.text = getString(R.string.moderately_active)
                        binding.activity.text = getString(R.string.sports_3_5_days_week)
                    }

                    3 -> {
                        binding.activityTitle.text = getString(R.string.very_active)
                        binding.activity.text = getString(R.string.sports_6_7_days_week)
                    }

                    4 -> {
                        binding.activityTitle.text = getString(R.string.extremely_active)
                        binding.activity.text = getString(R.string.training_twice_a_day)
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
        binding.SeekbarC.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val rd = view?.findViewById<RadioButton>(binding.rdgroup.checkedRadioButtonId)
                if (rd?.text == getString(R.string.lose_weight)) {
                    when (progress) {
                        0 -> binding.calories.text = "-200"
                        1 -> binding.calories.text = "-300"
                        2 -> binding.calories.text = "-400"
                        3 -> binding.calories.text = "-500"
                    }
                } else if (rd?.text == getString(R.string.gain_weight)) {
                    when (progress) {
                        0 -> binding.calories.text = "200"
                        1 -> binding.calories.text = "300"
                        2 -> binding.calories.text = "400"
                        3 -> binding.calories.text = "500"
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        binding.btnUpdate.setOnClickListener {
            val goal =
                view?.findViewById<RadioButton>(binding.rdgroup.checkedRadioButtonId)?.text.toString()

            val udata: UData = UData(
                username,
                binding.name.text.toString(),
                binding.Age.text.toString().toInt(),
                binding.Weight.text.toString().toDouble(),
                binding.Height.text.toString().toDouble(),
                goal,
                binding.activityTitle.text.toString(),
                gender,
                binding.calories.text.toString().toInt()
            )
            try {
                val file = File(requireContext().filesDir, udata.username + "Data.txt")
                val fw = FileWriter(file)
                fw.write(udata.toString())
                fw.close()
                i.ToastS("Saved :)")
            } catch (ex: Exception) {

            }
        }

        return root
    }

    fun function() {
        val rd = view?.findViewById<RadioButton>(binding.rdgroup.checkedRadioButtonId)
        when (rd?.text) {
            getString(R.string.lose_weight) -> {
                when (binding.SeekbarC.progress) {
                    0 -> binding.calories.text = "-200"
                    1 -> binding.calories.text = "-300"
                    2 -> binding.calories.text = "-400"
                    3 -> binding.calories.text = "-500"
                }
            }

            getString(R.string.gain_weight) -> {
                when (binding.SeekbarC.progress) {
                    0 -> binding.calories.text = "200"
                    1 -> binding.calories.text = "300"
                    2 -> binding.calories.text = "400"
                    3 -> binding.calories.text = "500"
                }
            }

            else -> {
                binding.calories.text = "0"
                binding.SeekbarC.progress = 0
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}