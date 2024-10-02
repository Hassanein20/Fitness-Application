package Manager.Tools

import android.R
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.fitness.Ingredients
import com.example.fitness.UData
import com.example.fitness.User
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.OutputStreamWriter
import java.io.Serializable
import kotlin.reflect.KClass

class iTools {

    private var currentActivity: Any

    constructor(Activity: Activity) {
        currentActivity = Activity
    }

    constructor(Context: Context) {
        currentActivity = Context
    }

    fun CheckPermission(Permission: String): Boolean {
        return try {
            ContextCompat.checkSelfPermission(
                currentActivity as Context,
                Permission
            ) == PackageManager.PERMISSION_GRANTED
        } catch (ex: Exception) {
            ToastS(ex.message.toString())
            false
        }
    }

    fun iNeedPermission(Permission: Array<String>, requestcode: Int = 1111) {
        //make a mutableList<String>(), Then add to it the items to be added to the array then give it to this function
        // with an extention .toTypedArray
        try {
            ActivityCompat.requestPermissions(
                currentActivity as Activity,
                Permission,
                requestcode
            )
        } catch (ex: Exception) {
            ToastS(ex.message.toString())
        }
        //we need to override a function named by onRequestPermissionResult() the requestCode is 1111 in our case
        //then we make like this:
        /*if(requestcode == 1111 && grantedResults.isnotEmpty()){
            for(i in grantedResult.indices){
                if(grantedResult[i] == PackageManager.PERMISSION_GRANTED){

                }
            }
        }*/
    }

    fun ToastS(Message: String) {
        Toast.makeText(currentActivity as Context, Message, Toast.LENGTH_SHORT).show()
    }

    fun ToastL(Message: String) {
        Toast.makeText(currentActivity as Context, Message, Toast.LENGTH_LONG).show()
    }

    fun fillSpinner(Spinner: Spinner, Items: Array<String>) {
        try {
            var aa = ArrayAdapter(currentActivity as Context, R.layout.simple_spinner_item, Items)
            Spinner.adapter = aa
        } catch (ex: Exception) {
            ToastS(ex.message.toString())
        }
    }

    fun openActivity(Activity: KClass<*>) {
        try {
            var i = Intent(currentActivity as Context, Activity.java)
            ContextCompat.startActivity(currentActivity as Context, i, null)
        } catch (ex: Exception) {
            ToastS(ex.message.toString())
        }
    }

    fun openActivityB(Activity: KClass<*>, bundle: Bundle, bundleName: String) {
        try {
            var i = Intent(currentActivity as Context, Activity.java)
            i.putExtra(bundleName, bundle)
            ContextCompat.startActivity(currentActivity as Context, i, null)
        } catch (ex: Exception) {
            ToastS(ex.message.toString())
        }
    }

    fun openActivityForResult(Activity: KClass<*>, RequestCode: Int) {
        try {
            var i = Intent(currentActivity as Context, Activity.java)
            ActivityCompat.startActivityForResult(currentActivity as Activity, i, RequestCode, null)
        } catch (ex: Exception) {
            ToastS(ex.message.toString())
        }
    }

    fun openActivityC(Activity: KClass<*>, ClassName: String, Class: Serializable) {
        try {
            Intent(currentActivity as Context, Activity.java).also {
                it.putExtra(ClassName, Class)
                startActivity(currentActivity as Context, it, null)
            }
        } catch (ex: Exception) {
            ToastS(ex.message.toString())
        }

    }

    // the uri we can get it from R.raw.(the sound that you copied in the raw directory)
    fun runShortSound(uri: Int) {
        try {
            MediaPlayer.create(currentActivity as Context, uri).start()
        } catch (ex: Exception) {
            ToastS(ex.message.toString())
        }
    }

    fun AppendFile(Context: Context, content: String, Name: String) {

        val file = File(Context.filesDir.path + "/$Name.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
        try {
            val outputStream =
                Context.openFileOutput("$Name.txt", android.content.Context.MODE_APPEND)
            val fw = BufferedWriter(OutputStreamWriter(outputStream))
            fw.append("$content\n")
            fw.close()
        } catch (ex: Exception) {
            ToastS(ex.message.toString())
        }
    }

    fun saveFile(Context: Context, content: String, Name: String) {

        val file = File((currentActivity as Context).filesDir, "$Name.txt")
        try {
            if (!file.exists()) {
                file.createNewFile()
            }
            val fw = FileWriter(file)
            fw.write("$content\n")
            fw.close()
        } catch (ex: Exception) {
            ToastS(ex.message.toString())
        }
    }

    fun readFile(Name: String): String {
        val path = (currentActivity as Context).filesDir.path + "/$Name.txt"
        val file = File(path)
        if (!file.exists()) {
            file.createNewFile()
        }
        val line = if(file.readLines().size != 0) {
            file.readLines()[0]
        }else{
           "0\t0"
        }
        return line
    }

    fun checkCredentials(
        user: String,
        pass: String,
        remember: Boolean,
        NextActivity: KClass<*>
    ): Boolean {

        val path = (currentActivity as Context).filesDir.path + "/Credentials.txt"
        try {
            val lines = File(path).readLines()
            var p = 0
            var c = 0
            for (line in lines) {
                val cre = line.split("\t").toTypedArray()
                if (cre[0] == user) {
                    c = 1
                    if (cre[1] == pass) {
                        p = 1
                        val Users = ReadUsers()
                        if (remember) {
                            for (User in Users) {
                                if (User.user.equals(user)) {
                                    User.Remember = true
                                    var u = ""
                                    for (U in Users) {
                                        u += U.toString()
                                    }
                                    saveFile(
                                        currentActivity as Context,
                                        u,
                                        "Credentials"
                                    )
                                    break
                                }
                            }
                        }
                        val udata = ReadData(cre[0])
                        Intent(currentActivity as Context, NextActivity.java).also {
                            it.putExtra("username", udata.username)
                            startActivity(currentActivity as Context, it, null)
                        }
                        break
                    }
                    break

                }
            }
            if (c == 0) {
                ToastL("Please register a new user")
                return false
            } else if (p == 0) {
                ToastS("Your password is not correct")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun ReadData(username: String): UData {

        val path = (currentActivity as Context).filesDir.path + "/$username" + "Data.txt"
        try {
            val lines = File(path).readLines()
            val prop = lines[0].split("\t").toTypedArray()
            val udata = UData(
                prop[0],
                prop[1],
                prop[2].toInt(),
                prop[3].toDouble(),
                prop[4].toDouble(),
                prop[5],
                prop[6],
                prop[7],
                prop[9].toInt()
            )

            return udata
        } catch (ex: Exception) {
            ToastS(ex.message.toString())
            return UData("", "", 0, 0.0, 0.0, "", "", "", 0)
        }
    }

    fun ReadUsers(): ArrayList<User> {

        val users = arrayListOf<User>()

        val path = (currentActivity as Context).filesDir.path + "/Credentials.txt"
        try {

            val lines = File(path).readLines()
            for (line in lines) {
                val cre = line.split("\t").toTypedArray()
                var remember: Boolean = false
                if (cre[2] == "true") {
                    remember = true
                }
                val user = User(cre[0], cre[1], remember)
                users.add(user)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return users
    }

    fun ReadIngredients(): ArrayList<Ingredients> {

        val ingredient = ArrayList<Ingredients>()

        val path = (currentActivity as Context).filesDir.path + "/Ingredients.txt"
        try {

            val lines = File(path).readLines()
            for (line in lines) {
                val ingd = line.split("\t").toTypedArray()
                val ingredient1 = Ingredients(
                    ingd[0],
                    ingd[1],
                    ingd[2].toDouble(),
                    ingd[3].toDouble(),
                    ingd[4].toDouble(),
                    ingd[5].toDouble()
                )
                ingredient.add(ingredient1)
            }


        } catch (ex: Exception) {
            ToastS(ex.message.toString())
        }
        return ingredient
    }

    fun Photo() {
        //Implicit Intent
        Intent(Intent.ACTION_GET_CONTENT).also {
            it.type = "image/*"
            ActivityCompat.startActivityForResult(currentActivity as Activity, it, 0, null)
        }

        // we need to override a fun which is onActivityResult()
        //in it we make:
        /*if(resultCode == Activity.RESULT_OK && requestCode == 0){
            val uri = data?.data
            ImageView.setImage(uri)
        }*/

    }

    /*fun AlertDialogMaking(context: Context,Title: String, Message: String): AlertDialog {
        return AddContactDialog
        //After implimentation you need to extend the show() method ".show()"
    }*/

    fun AlertDialogMaking1(Array: Array<String>, Title: String): AlertDialog {

        val SingleChoiceDialog = AlertDialog.Builder(currentActivity as Activity)
            .setTitle(Title)
            .setSingleChoiceItems(Array, 0) { dialogInterface, i ->
                ToastS("you clicked on ${Array[i]}")
            }
            .setPositiveButton("Accept") { _, _ ->
                ToastS("Accepted")
            }
            .setNegativeButton("Decline") { _, _ ->
                ToastS("Declined")
            }
            .create()

        return SingleChoiceDialog
    }

}