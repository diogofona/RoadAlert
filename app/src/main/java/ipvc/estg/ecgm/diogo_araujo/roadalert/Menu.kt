package ipvc.estg.ecgm.diogo_araujo.roadalert

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class Menu : AppCompatActivity() {

    lateinit var preferences: SharedPreferences
    private lateinit var Name: TextView
    private lateinit var Email: TextView
    private var DataRemembered: Int = 0
    private lateinit var Ausername: String
    private lateinit var Aname: String
    private lateinit var Aemail: String

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
            //Ausername = username.toString()
            val name = preferences.getString("name", "")
            val email = preferences.getString("email", "")
            Name.setText(name)
            Email.setText(email)
            DataRemembered = 2
        }else if(intent.getStringExtra("username") != ""){
            Ausername = intent.getStringExtra("username").toString()
            Aname = intent.getStringExtra("name").toString()
            Aemail = intent.getStringExtra("email").toString()
            Name.setText(Aname)
            Email.setText(Aemail)
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
            if(DataRemembered == 1) {
                intent.putExtra("username", Ausername);
                intent.putExtra("name", Aname);
                intent.putExtra("email", Aemail);
            }
            startActivity(intent)
            finish()
        }

        AP.setOnClickListener{
            val intent = Intent(this@Menu, MainActivity::class.java)
            if(DataRemembered == 1) {
                intent.putExtra("username", Ausername);
                intent.putExtra("name", Aname);
                intent.putExtra("email", Aemail);
            }
            startActivity(intent)
            finish()
        }

        LG.setOnClickListener{
            val intent = Intent(this@Menu, Login::class.java)
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