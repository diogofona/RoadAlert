package ipvc.estg.ecgm.diogo_araujo.roadalert

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        ET_username = findViewById(R.id.ET_username)
        ET_password = findViewById(R.id.ET_password)

        val button = findViewById<Button>(R.id.btn_login)
        button.setOnClickListener{
            //Toast.makeText(this@Login, "CLICCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCL", Toast.LENGTH_SHORT).show()
            val username = ET_username.text.toString()
            val password = ET_password.text.toString()

            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.login(username = username, password = password)

            call.enqueue(object : Callback<OutputPost> {
                override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {
                    Toast.makeText(this@Login, response.toString(), Toast.LENGTH_SHORT).show()
                    if(response.isSuccessful){
                        //Toast.makeText(this@Login, "inside SUCC", Toast.LENGTH_SHORT).show()
                        val u: OutputPost = response.body()!!
                        Toast.makeText(this@Login, u.name + "///" + u.email, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                    Toast.makeText(this@Login, "ERRO!", Toast.LENGTH_SHORT).show()
                    //To change body of created functions use File | Settings | File Templates.
                }
            })
        }
    }

    fun CheckLogin(view: View){


    }
}