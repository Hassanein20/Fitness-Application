package com.example.fitness

import Manager.Tools.iTools
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fitness.databinding.ActivityDailyBinding
import com.example.fitness.databinding.IngredientAddBinding
import com.example.fitness.ui.Ingredients.IngredientsViewModel
import com.example.fitness.ui.Setting.SettingViewModel
import com.example.fitness.ui.home.HomeViewModel
import com.google.android.material.navigation.NavigationView

class Daily : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDailyBinding

    val i = iTools(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDailyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewmodelS = ViewModelProvider(this).get(SettingViewModel::class.java)
        val viewmodelI = ViewModelProvider(this).get(IngredientsViewModel()::class.java)
        val viewmodelH = ViewModelProvider(this).get(HomeViewModel::class.java)

        setSupportActionBar(binding.appBarDaily.toolbar)
        val username = intent.getStringExtra("username")
        val udata = i.ReadData(username!!)
        viewmodelH.DailyCalories(udata.FinalCalories)

        binding.appBarDaily.fab.setOnClickListener { view ->
            val ingred = i.ReadIngredients()
            val b: IngredientAddBinding = IngredientAddBinding.inflate(LayoutInflater.from(this))
            val dialog = AlertDialog.Builder(this).setView(b.root).create()
            dialog.show()
            b.btnconfirm.setOnClickListener {
                val ing = Ingredients(
                    b.name.text.toString(),
                    b.kind.text.toString(),
                    b.calories.text.toString().toDouble(),
                    b.protein.text.toString().toDouble(),
                    b.carbs.text.toString().toDouble(),
                    b.fats.text.toString().toDouble()
                )
                ingred.add(ing)
                i.AppendFile(this, ing.toString(), "Ingredients")
                viewmodelI.addIngredient(ingred)
                viewmodelI.addIngredient(i.ReadIngredients())
                dialog.dismiss()
            }

        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_daily)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_ingredients, R.id.nav_setting,
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        navView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.nav_home -> {
                    navController.navigate(R.id.nav_home)
                }

                R.id.nav_ingredients -> {
                    viewmodelI.addIngredient(i.ReadIngredients())
                    navController.navigate(R.id.nav_ingredients)
                }

                R.id.nav_setting -> {
                    viewmodelS.sharedclass(i.ReadData(username!!))
                    navController.navigate(R.id.nav_setting)
                }

                R.id.logout -> {
                    try {
                        val users = i.ReadUsers()
                        val udata = i.ReadData(username!!)
                        for (user in users) {
                            if (user.user.equals(udata.username)) {
                                user.Remember = false
                                var allusers = ""
                                for (u in users) {
                                    allusers += u.toString()
                                    //a null line will appear since in the u.toString() have \n and in fun saveFile() it also have
                                }
                                i.saveFile(this, allusers, "Credentials")
                                break
                            }
                        }
                        i.openActivity(MainActivity::class)
                    } catch (ex: Exception) {
                        i.ToastL(ex.message.toString())
                    }
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

    }

    /*this is for the optionItems(Example in this applicaion is the setting in the right corner)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }*/

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.daily, menu)
        return true
    }*/

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_daily)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}