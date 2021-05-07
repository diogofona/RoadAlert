package ipvc.estg.ecgm.diogo_araujo.roadalert

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class VerReport: AppCompatActivity() {

    private lateinit var id: String
    private lateinit var username: String
    private lateinit var title: String
    private lateinit var situation: String
    private lateinit var location: String
    private lateinit var image: String
    private lateinit var date: String
    private lateinit var latitude: String
    private lateinit var longitude: String
    private var DataRemembered: Int = 0
    lateinit var preferences: SharedPreferences

    private lateinit var report_titulo: EditText
    private lateinit var report_desc: EditText
    private lateinit var report_img: EditText
    private lateinit var report_date: TextView
    private lateinit var report_username: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_report)

        //next
        report_titulo = findViewById(R.id.report_titulo)
        report_desc = findViewById(R.id.report_desc)
        report_img = findViewById(R.id.report_img)
        report_date = findViewById(R.id.label_atualizada)
        report_username = findViewById(R.id.label_username)

        if(intent.getStringExtra("ID").toString() != ""){
            id = intent.getStringExtra("ID").toString()
            username = intent.getStringExtra("USERNAMES").toString()
            title = intent.getStringExtra("TITLE").toString()
            situation = intent.getStringExtra("SITUATION").toString()
            location = intent.getStringExtra("LOCATION").toString()
            latitude = intent.getStringExtra("latitude").toString()
            longitude = intent.getStringExtra("longitude").toString()
            image = intent.getStringExtra("IMAGE").toString()
            date = intent.getStringExtra("DATE").toString()
            //Toast.makeText(this@NovoReport, title, Toast.LENGTH_LONG).show()
        } else {
            id = "0"
            username = "0"
            title = "0"
            situation = "0"
            location = "0"
            latitude = "0"
            longitude = "0"
            image = "0"
            date = "0"
        }

        report_titulo.setText(title)
        report_desc.setText(situation)
        report_img.setText(image)
        report_username.setText("Criado por: "+ username)

        val strs = date?.split("T")!!.toTypedArray()
        val stt = strs[0]?.split("-")!!.toTypedArray()
        val snt= strs[1]?.split(":")!!.toTypedArray()
        report_date.setText("Ultima edição: "+ stt[2]+"-"+stt[1]+"-"+stt[0] + "   " + snt[0]+":"+snt[1])

        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        if(preferences.getString("username", "") != "") {
            DataRemembered = 2
        }else if(intent.getStringExtra("username") != ""){
            DataRemembered = 1
        }

        val buttonVoltar = findViewById<ImageView>(R.id.see_report_back)
        buttonVoltar.setOnClickListener {
            val intent = Intent(this@VerReport, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}