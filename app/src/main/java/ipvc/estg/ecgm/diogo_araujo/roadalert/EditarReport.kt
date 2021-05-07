package ipvc.estg.ecgm.diogo_araujo.roadalert

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.EndPoints
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.OutputReport
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class EditarReport: AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_report)

        report_titulo = findViewById(R.id.report_titulo)
        report_desc = findViewById(R.id.report_desc)
        report_img = findViewById(R.id.report_img)
        report_date = findViewById(R.id.label_atualizada)

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
            //Toast.makeText(this@EditarReport, id, Toast.LENGTH_LONG).show()
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

        val buttonVoltar = findViewById<ImageView>(R.id.edit_report_back)
        buttonVoltar.setOnClickListener {
            val intent = Intent(this@EditarReport, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }

        val buttonReport = findViewById<Button>(R.id.btn_edit_report)
        buttonReport.setOnClickListener {
            if(!TextUtils.isEmpty(report_titulo.text) && !TextUtils.isEmpty(report_desc.text) && !TextUtils.isEmpty(report_img.text)) {
                val DT = LocalDateTime.now().toString()
                val request = ServiceBuilder.buildService(EndPoints::class.java)
                var loc = latitude + "," + longitude;
                val call = request.editReport(id = id, username = username, title = report_titulo.text.toString(), situation = report_desc.text.toString(), location = loc.toString(), image = report_img.text.toString(), date = DT)

                call.enqueue(object : Callback<OutputReport> {
                    override fun onResponse(
                        call: Call<OutputReport>,
                        response: Response<OutputReport>
                    ) {
                        //Toast.makeText(this@Login, response.toString(), Toast.LENGTH_SHORT).show()
                        if (response.isSuccessful) {
                            val u: OutputReport = response.body()!!
                            if(u.status == true){
                                Toast.makeText(this@EditarReport, "Situação editada com sucesso!", Toast.LENGTH_LONG).show()
                                if(DataRemembered == 2){
                                    val intent = Intent(this@EditarReport, MapsActivity::class.java)
                                    startActivity(intent)
                                }
                            }else{
                                Toast.makeText(this@EditarReport, "Erro ao tentar editar a situação.", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<OutputReport>, t: Throwable) {
                        Toast.makeText(
                            this@EditarReport,
                            "Erro ao tentar editar a situação.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }else{
                Toast.makeText(this@EditarReport, "Os dados não estão corretamente preenchidos.", Toast.LENGTH_SHORT).show()
            }
        }

        val buttonReportE = findViewById<ImageView>(R.id.eliminar_report)
        buttonReportE.setOnClickListener {
            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.deleteReport(id = id)

            call.enqueue(object : Callback<OutputReport> {
                override fun onResponse(
                    call: Call<OutputReport>,
                    response: Response<OutputReport>
                ) {
                    //Toast.makeText(this@Login, response.toString(), Toast.LENGTH_SHORT).show()
                    if (response.isSuccessful) {
                        val u: OutputReport = response.body()!!
                        if(u.status == true){
                            Toast.makeText(this@EditarReport, "Situação eliminada!", Toast.LENGTH_LONG).show()
                            if(DataRemembered == 2){
                                val intent = Intent(this@EditarReport, MapsActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }else{
                            Toast.makeText(this@EditarReport, "Erro ao tentar eliminar a situação.", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<OutputReport>, t: Throwable) {
                    Toast.makeText(
                        this@EditarReport,
                        "Erro ao tentar eliminar a situação.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}