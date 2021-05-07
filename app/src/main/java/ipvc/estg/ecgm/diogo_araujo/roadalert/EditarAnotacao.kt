package ipvc.estg.ecgm.diogo_araujo.roadalert

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import java.time.LocalDateTime

class EditarAnotacao : AppCompatActivity() {

    //next1
    private lateinit var titulo_ET: EditText
    private lateinit var descricao_ET: EditText
    private lateinit var atualizada_TV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_anotacao)
        //Toast.makeText(this, intent.getStringExtra(MainActivity.EXTRA_ID), Toast.LENGTH_LONG).show()
        val id = intent.getStringExtra(MainActivity.EXTRA_ID)
        val titulo = intent.getStringExtra(MainActivity.EXTRA_TITULO)
        val descricao = intent.getStringExtra(MainActivity.EXTRA_DESCRICAO)
        val atualizada = intent.getStringExtra(MainActivity.EXTRA_ATUALIZADA)
        val strs = atualizada?.split("T")!!.toTypedArray()
        val stt = strs[0]?.split("-")!!.toTypedArray()
        val snt= strs[1]?.split(":")!!.toTypedArray()

        titulo_ET = findViewById(R.id.anotacao_titulo)
        titulo_ET.setText(titulo)
        descricao_ET = findViewById(R.id.anotacao_desc)
        descricao_ET.setText(descricao)
        atualizada_TV = findViewById(R.id.label_atualizada)
        atualizada_TV.setText("Ultima edição: "+ stt[2]+"-"+stt[1]+"-"+stt[0] + "   " + snt[0]+":"+snt[1])

        //Botao voltar
        val bt_voltar = findViewById<ImageView>(R.id.edit_anotacao_back)
        bt_voltar.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //Botao guardar
        val bt_guardar = findViewById<Button>(R.id.btn_save_anotacao)
        bt_guardar.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(titulo_ET.text)) {
                replyIntent.putExtra(MainActivity.EXTRA_ERRO, getString(R.string.erro_vazio))
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else if(titulo_ET.text.toString() == titulo && descricao_ET.text.toString() == descricao){
                replyIntent.putExtra(MainActivity.EXTRA_ERRO, getString(R.string.erro_sem_alteracao_anotacao))
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(MainActivity.EXTRA_ID, id.toString())
                replyIntent.putExtra(MainActivity.EXTRA_TITULO, titulo_ET.text.toString())
                replyIntent.putExtra(MainActivity.EXTRA_DESCRICAO, descricao_ET.text.toString())
                replyIntent.putExtra(MainActivity.EXTRA_ATUALIZADA, LocalDateTime.now().toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        //Botao delete
        val bt_eleminar = findViewById<ImageView>(R.id.eliminar_anotacao)
        bt_eleminar.setOnClickListener{
            val rt_conf = findViewById<RelativeLayout>(R.id.rt_conf)
            rt_conf.isVisible = true

            val bt_sim = findViewById<Button>(R.id.btn_conf_sim)
            bt_sim.setOnClickListener {
                val replyIntent = Intent()
                replyIntent.putExtra(MainActivity.EXTRA_ID, id.toString())
                replyIntent.setAction("DELETE")
                setResult(Activity.RESULT_OK, replyIntent)
                rt_conf.isVisible = false
                finish()
            }

            val bt_nao = findViewById<Button>(R.id.btn_conf_nao)
            bt_nao.setOnClickListener {
                rt_conf.isVisible = false
            }

        }
    }

}