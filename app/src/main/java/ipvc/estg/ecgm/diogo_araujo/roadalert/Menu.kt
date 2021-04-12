package ipvc.estg.ecgm.diogo_araujo.roadalert

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class Menu : AppCompatActivity() {

    lateinit var preferences: SharedPreferences
    private lateinit var Name: TextView
    private lateinit var Email: TextView
    private var DataRemembered: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        Name = findViewById(R.id.label_name)
        Email = findViewById(R.id.label_email)
        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        val TS = findViewById<TextView>(R.id.btn_TS)
        val AP = findViewById<Button>(R.id.btn_AP)
        val SP = findViewById<Button>(R.id.btn_SP)
        val LG = findViewById<Button>(R.id.btn_LG)

        if(preferences.getString("username", "") != "") {
            val username = preferences.getString("username", "")
            val name = preferences.getString("name", "")
            val email = preferences.getString("email", "")
            Name.setText(name)
            Email.setText(email)
            DataRemembered = 2
        }else if(intent.getStringExtra("username") != ""){
            val username = intent.getStringExtra("username")
            val name = intent.getStringExtra("name")
            val email = intent.getStringExtra("email")
            Name.setText(name)
            Email.setText(email)
            DataRemembered = 1
        }else{
            Name.setText("")
            Email.setText("")
            DataRemembered = 0
        }

        TS.setOnClickListener{
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.clear()
            editor.apply()
            val intent = Intent(this@Menu, Login::class.java)
            startActivity(intent)
            finish()
        }

        SP.setOnClickListener{
            val intent = Intent(this@Menu, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }

        if(Name.text != ""){
            TS.isEnabled = true
            TS.isVisible = true
            SP.isEnabled = true
            LG.isEnabled = false
        }else{
            TS.isEnabled = false
            TS.isVisible = false
            SP.isEnabled = false
            LG.isEnabled = true
        }
    }
}