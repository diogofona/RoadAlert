package ipvc.estg.ecgm.diogo_araujo.roadalert

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.EndPoints
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.OutputPost
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.OutputReport
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class NovoReport: AppCompatActivity() {

    lateinit var preferences: SharedPreferences
    private lateinit var username: String
    private var DataRemembered: Int = 0
    private lateinit var latitude: String
    private lateinit var longitude: String

    private lateinit var report_titulo: EditText
    private lateinit var report_desc: EditText
    private lateinit var report_img: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.novo_report)

        report_titulo = findViewById(R.id.report_titulo)
        report_desc = findViewById(R.id.report_desc)
        report_img = findViewById(R.id.report_img)

        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        if(preferences.getString("username", "") != "") {
            username = preferences.getString("username", "").toString()
            DataRemembered = 2
        }else if(intent.getStringExtra("username") != ""){
            username = intent.getStringExtra("username").toString()
            DataRemembered = 1
        }

        if(intent.getStringExtra("latitude").toString() != ""){
            latitude = intent.getStringExtra("latitude").toString()
            longitude = intent.getStringExtra("longitude").toString()
            //Toast.makeText(this@NovoReport, latitude, Toast.LENGTH_LONG).show()
        } else {
            latitude = "0"
            longitude = "0"
        }

        val buttonVoltar = findViewById<ImageView>(R.id.novo_report_back)
        buttonVoltar.setOnClickListener {
            val intent = Intent(this@NovoReport, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }

        val buttonReport = findViewById<Button>(R.id.btn_save_new_report)
        buttonReport.setOnClickListener {
            if(!TextUtils.isEmpty(report_titulo.text) && !TextUtils.isEmpty(report_desc.text) && !TextUtils.isEmpty(report_img.text)) {
                val DT = LocalDateTime.now().toString()
                val request = ServiceBuilder.buildService(EndPoints::class.java)
                var loc = latitude + "," + longitude;
                val call = request.addReport(username = username, title = report_titulo.text.toString(), situation = report_desc.text.toString(), location = loc.toString(), image = report_img.text.toString(), date = DT)

                call.enqueue(object : Callback<OutputReport> {
                    override fun onResponse(
                        call: Call<OutputReport>,
                        response: Response<OutputReport>
                    ) {
                        //Toast.makeText(this@Login, response.toString(), Toast.LENGTH_SHORT).show()
                        if (response.isSuccessful) {
                            val u: OutputReport = response.body()!!
                            if(u.status == true){
                                Toast.makeText(this@NovoReport, "Situação reportada com sucesso!", Toast.LENGTH_LONG).show()
                                if(DataRemembered == 2){
                                    val intent = Intent(this@NovoReport, MapsActivity::class.java)
                                    startActivity(intent)
                                }
                            }else{
                                Toast.makeText(this@NovoReport, "Erro ao tentar guardar a situação.", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<OutputReport>, t: Throwable) {
                        Toast.makeText(
                            this@NovoReport,
                            "Erro ao tentar guardar a situação.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }else{
                Toast.makeText(this@NovoReport, "Os dados não estão corretamente preenchidos.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}