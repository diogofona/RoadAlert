package ipvc.estg.ecgm.diogo_araujo.roadalert

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.EndPoints
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.OutputPost
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.ServiceBuilder
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    private lateinit var ET_username: EditText
    private lateinit var ET_password: EditText
    private lateinit var sharedpreferences: SharedPreferences
    private lateinit var remember: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        ET_username = findViewById(R.id.ET_username)
        ET_password = findViewById(R.id.ET_password)
        remember = findViewById(R.id.checkRemember)

        sharedpreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)


        if(sharedpreferences.getString("username", "") != ""){
            val intent = Intent(this@Login, Menu::class.java)
            startActivity(intent)
            finish()
        }

        val button = findViewById<Button>(R.id.btn_login)
        button.setOnClickListener{
            val username = ET_username.text.toString()
            val password = ET_password.text.toString()

            if(!TextUtils.isEmpty(ET_username.text) && !TextUtils.isEmpty(ET_password.text)) {
                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.login(username = username, password = password)

                call.enqueue(object : Callback<OutputPost> {
                    override fun onResponse(
                        call: Call<OutputPost>,
                        response: Response<OutputPost>
                    ) {
                        //Toast.makeText(this@Login, response.toString(), Toast.LENGTH_SHORT).show()
                        if (response.isSuccessful) {
                            val u: OutputPost = response.body()!!
                            if(u.status == true){
                                Toast.makeText(this@Login, "Autenticação sucedida", Toast.LENGTH_LONG).show()
                                if(remember.isChecked == true){
                                    val checked: Boolean = remember.isChecked
                                    val editor: SharedPreferences.Editor = sharedpreferences.edit()
                                    editor.putString("username", ET_username.text.toString())
                                    editor.putString("name", u.name)
                                    editor.putString("email", u.email)
                                    editor.putBoolean("remember", checked)
                                    editor.apply()
                                    val intent = Intent(this@Login, Menu::class.java)
                                    startActivity(intent)
                                    finish()
                                }else{
                                    val intent = Intent(this@Login, Menu::class.java)
                                    intent.putExtra("username", ET_username.text);
                                    intent.putExtra("name", u.name);
                                    intent.putExtra("email", u.email);
                                    startActivity(intent)
                                    finish()
                                }
                            }else{
                                Toast.makeText(this@Login, "Autenticação negada, dados incorrectos.", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                        Toast.makeText(
                            this@Login,
                            "Erro ao tentar efectuar a autenticação",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }else{
                Toast.makeText(this@Login, "Os dados não estão corretamente preenchidos", Toast.LENGTH_SHORT).show()
            }
        }

        val buttonreg = findViewById<TextView>(R.id.registar)
        buttonreg.setOnClickListener{
            val intent = Intent(this@Login, Registar::class.java)
            startActivity(intent)
        }

        val menu = findViewById<ImageView>(R.id.menu_login)
        menu.setOnClickListener{
            val intent = Intent(this@Login, Menu::class.java)
            startActivity(intent)
        }
    }
}