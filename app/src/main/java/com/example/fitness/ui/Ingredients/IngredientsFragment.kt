package com.example.fitness.ui.Ingredients

import Manager.Tools.iTools
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.IngredientAdapter
import com.example.fitness.Ingredients
import com.example.fitness.MyTextWatcher
import com.example.fitness.databinding.FragmentIngredientsBinding

class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewmodel =
            ViewModelProvider(requireActivity()).get(IngredientsViewModel()::class.java)
        val i = iTools(requireActivity().applicationContext)



        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val ingredient = mutableListOf(
            Ingredients("Sugar", "Normal", 1.1, 1.1, 1.1, 1.1),
            Ingredients("Meat", "Normal", 10.1, 12.1, 11.1, 3.1),
            Ingredients("Sugar", "brown", 0.1, 1.1, 1.1, 0.0)
        )*/

        viewmodel.livedata.observe(viewLifecycleOwner) {
            val ingredients = it
            try {
                binding.rvIngredients.adapter = IngredientAdapter(
                    ingredients, requireActivity()
                )
            }catch (ex:Exception){
                i.ToastL(ex.message.toString())
            }
            binding.rvIngredients.layoutManager = LinearLayoutManager(requireContext())

            val textwatcher = MyTextWatcher(binding.search) {
                val n = mutableListOf<Ingredients>()
                it.apply {
                    for (i in ingredients) {
                        if (i.name.contains(it,ignoreCase = true)) {
                            n.add(i)
                        }
                    }
                    binding.rvIngredients.adapter =
                        IngredientAdapter(n, requireActivity())
                }
            }
            binding.search.addTextChangedListener(textwatcher)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}