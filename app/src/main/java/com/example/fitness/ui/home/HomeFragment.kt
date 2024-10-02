package com.example.fitness.ui.home

import Manager.Tools.iTools
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fitness.Progress
import com.example.fitness.databinding.FragmentHomeBinding
import java.util.Calendar
import java.util.concurrent.Executors

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val i = iTools(requireActivity().applicationContext)
        val h =
            ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var macros = ""

        h.livedata.observe(viewLifecycleOwner) {
            binding.dcalories.text = (String.format("%.0f", it.toString().toDouble()))
        }

        h.livedatain.observe(viewLifecycleOwner) {
            binding.calories.text = (String.format("%.0f", it.toString().toDouble()))
            macros = binding.calories.text.toString() + "\t"
        }

        h.livedatar.observe(viewLifecycleOwner) {
            binding.rcalories.text = (String.format("%.0f", it.toString().toDouble()))
            val r = binding.rcalories.text.toString()
            i.saveFile(requireActivity(), "$r\t1", "r")
        }

        h.livedatap.observe(viewLifecycleOwner) {
            binding.protein.text = (String.format("%.0f", it.toString().toDouble()))
            macros += binding.protein.text.toString() + "\t"
        }

        h.livedatac.observe(viewLifecycleOwner) {
            binding.carbs.text = (String.format("%.0f", it.toString().toDouble()))
            macros += binding.carbs.text.toString() + "\t"
        }

        h.livedataf.observe(viewLifecycleOwner) {
            binding.fats.text = (String.format("%.0f", it.toString().toDouble()))
            macros += binding.fats.text.toString()
            i.saveFile(requireActivity(), macros, "day")
        }

        try {
            val rline = i.readFile("r")
            val rprop = if (!rline.equals("0\t0")) {
                rline.split("\t").toTypedArray()
            } else {
                arrayOf("0", "0")
            }
            if (rprop[1] == "1") {
                h.rCalories(rprop[0].toFloat())

                if (rprop[0] != h.livedata.value?.toInt().toString()) {

                    val line = i.readFile("day")
                    val prop = if (!line.equals("0\t0")) {
                        line.split("\t").toTypedArray()
                    } else {
                        arrayOf("0", "0", "0", "0")
                    }
                    h.addMacros(
                        prop[0].toFloat(),
                        prop[1].toDouble(),
                        prop[2].toDouble(),
                        prop[3].toDouble()
                    )
                }
            }

        } catch (ex: Exception) {
            i.ToastL(ex.message.toString())
        }

        return root
    }



    private fun reset(i: iTools, vm: HomeViewModel) {
        val h =
            ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        val date = Calendar.getInstance()
        date.add(Calendar.DAY_OF_MONTH, -1) // Subtract 1 day from today
        val d = date.time
        val progress = Progress(
            d,
            binding.dcalories.text.toString().toDouble(),
            binding.rcalories.text.toString().toDouble(),
            binding.protein.text.toString().toDouble(),
            binding.carbs.text.toString().toDouble(),
            binding.fats.text.toString().toDouble()
        )
        i.AppendFile(requireActivity().applicationContext, progress.toString(), "Progress")

        vm.livedata.observe(viewLifecycleOwner) {
            binding.dcalories.text = it.toString()
        }

        vm.livedata.observe(viewLifecycleOwner) {
            binding.rcalories.text = it.toString()
        }

        i.saveFile(requireActivity(), h.livedatac.value.toString() + "\t0", "r")

        binding.protein.text = "0"
        binding.carbs.text = "0"
        binding.fats.text = "0"

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}