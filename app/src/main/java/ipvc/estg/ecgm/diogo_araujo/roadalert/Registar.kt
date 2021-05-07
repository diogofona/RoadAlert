package ipvc.estg.ecgm.diogo_araujo.roadalert

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.EndPoints
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.OutputPost
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.OutputPostRegist
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Registar : AppCompatActivity() {

    private lateinit var ET_username: EditText
    private lateinit var ET_name: EditText
    private lateinit var ET_email: EditText
    private lateinit var ET_password: EditText
    private lateinit var ET_password2: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registar)

        ET_username = findViewById(R.id.ET_username)
        ET_name = findViewById(R.id.ET_name)
        ET_email = findViewById(R.id.ET_email)
        ET_password = findViewById(R.id.ET_password)
        ET_password2 = findViewById(R.id.ET_password2)

        val btn_registar = findViewById<TextView>(R.id.btn_registar)
        btn_registar.setOnClickListener{
            if(!TextUtils.isEmpty(ET_username.text) && !TextUtils.isEmpty(ET_name.text) && !TextUtils.isEmpty(ET_email.text) && !TextUtils.isEmpty(ET_password.text) && !TextUtils.isEmpty(ET_password2.text)){
                if(ET_password.text.toString() == ET_password2.text.toString()){
                    val request = ServiceBuilder.buildService(EndPoints::class.java)
                    val call = request.regist(username = ET_username.text.toString(), name = ET_name.text.toString(), email = ET_email.text.toString(), password = ET_password.text.toString())

                    call.enqueue(object : Callback<OutputPostRegist> {
                        override fun onResponse(
                            call: Call<OutputPostRegist>,
                            response: Response<OutputPostRegist>
                        ) {
                            //Toast.makeText(this@Login, response.toString(), Toast.LENGTH_SHORT).show()
                            if (response.isSuccessful) {
                                val u: OutputPostRegist = response.body()!!
                                if(u.status != false) {
                                    Toast.makeText(this@Registar, "Registo sucedido", Toast.LENGTH_LONG).show()
                                    val intent = Intent(this@Registar, Login::class.java)
                                    startActivity(intent)
                                    finish()
                                }else{
                                    if(u.erro == "DuplicatedUsername"){
                                        Toast.makeText(this@Registar, "Este utilizador já existe", Toast.LENGTH_LONG).show()
                                    } else if(u.erro == "DuplicatedEmail"){
                                        Toast.makeText(this@Registar, "Este email já se encontra atribuido", Toast.LENGTH_LONG).show()
                                    } else{
                                        Toast.makeText(this@Registar, "Erro ao tentar efectuar o registo", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<OutputPostRegist>, t: Throwable) {
                            Toast.makeText(
                                this@Registar,
                                "Erro ao tentar efectuar o registo",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }else{
                    Toast.makeText(this@Registar, "As palavras-chave devem ser iguais", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this@Registar, "Os dados não estão corretamente preenchidos", Toast.LENGTH_SHORT).show()
            }
        }

        val voltar = findViewById<TextView>(R.id.voltar)
        voltar.setOnClickListener{
            val intent = Intent(this@Registar, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

}