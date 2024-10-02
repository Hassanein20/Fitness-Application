package com.example.fitness

import Manager.Tools.iTools
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.databinding.DialogBinding
import com.example.fitness.databinding.ItemIngredeintBinding
import com.example.fitness.ui.home.HomeViewModel

class IngredientAdapter(
    private val ingredientsList: List<Ingredients>,
    Context: Context
) : RecyclerView.Adapter<IngredientAdapter.ingredientHolder>() {

    inner class ingredientHolder(val binding: ItemIngredeintBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val context = Context
    private val i = iTools(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ingredientHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemIngredeintBinding.inflate(layoutInflater, parent, false)
        return ingredientHolder(binding)
    }

    override fun onBindViewHolder(holder: ingredientHolder, position: Int) {

        val ingredient = ingredientsList[position]
        holder.binding.apply {
            name.text = ingredient.name
            kind.text = ingredient.kind
            calories.text = ingredient.calories.toString()
            protein.text = ingredient.protein.toString()
            carbs.text = ingredient.carbs.toString()
            fats.text = ingredient.fats.toString()
            btnAdd.setOnClickListener {
                showDialog(
                    context,
                    ingredient.name,
                    ingredient.kind,
                    ingredient.calories,
                    ingredient.protein,
                    ingredient.carbs,
                    ingredient.fats
                )
            }
        }
    }

    fun showDialog(
        context: Context,
        name: String,
        kind: String,
        calories: Double,
        protein: Double,
        carbs: Double,
        fats: Double
    ) {
        val b: DialogBinding = DialogBinding.inflate(LayoutInflater.from(context))
        b.calories.text = calories.toString()
        b.protein.text = protein.toString()
        b.carbs.text = carbs.toString()
        b.fats.text = fats.toString()
        val dialog = AlertDialog.Builder(context).setView(b.root).create()
        val textwatcher = MyTextWatcherMac(b.grams) {
            it.apply {
                b.calories.text = (String.format("%.2f", (it.toInt() * calories) / 100))
                b.protein.text = (String.format("%.2f", (it.toInt() * protein) / 100))
                b.carbs.text = (String.format("%.2f", (it.toInt() * carbs) / 100))
                b.fats.text = (String.format("%.2f", (it.toInt() * fats) / 100))
            }
        }
        b.name.text = name
        b.kind.text = kind
        b.grams.addTextChangedListener(textwatcher)
        b.btnAdd.setOnClickListener {
            try {
                val h =
                    ViewModelProvider(context as ViewModelStoreOwner).get(HomeViewModel::class.java)
                h.addMacros(
                    b.calories.text.toString().toFloat(),
                    b.protein.text.toString().toDouble(),
                    b.carbs.text.toString().toDouble(),
                    b.fats.text.toString().toDouble()
                )
                dialog.dismiss()
            } catch (ex: Exception) {
                i.ToastL(ex.message.toString())
            }
        }
        try {
            dialog.show()
        } catch (ex: Exception) {
            i.ToastL(ex.message.toString())
        }
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }
}

