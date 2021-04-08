package ipvc.estg.ecgm.diogo_araujo.roadalert

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime

class NovaAnotacao : AppCompatActivity() {

    private lateinit var titulo: EditText
    private lateinit var descricao: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nova_anotacao)

        titulo = findViewById(R.id.anotacao_titulo)
        descricao = findViewById(R.id.anotacao_desc)

        val button = findViewById<Button>(R.id.btn_save_new_anotacao)
        button.setOnClickListener{
            val replyIntent = Intent()
            if(TextUtils.isEmpty(titulo.text)){
                replyIntent.putExtra(EXTRA_ERRO, getString(R.string.erro_vazio))
               setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val AT = titulo.text.toString()
                replyIntent.putExtra(EXTRA_TITULO, AT)
                val AD = descricao.text.toString()
                replyIntent.putExtra(EXTRA_DESC, AD)
                val AA = LocalDateTime.now().toString()
                replyIntent.putExtra(EXTRA_ATUALIZADA, AA)
                //Toast.makeText(this, AA.toString(), Toast.LENGTH_LONG).show()
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_TITULO = "TITULON"
        const val EXTRA_DESC = "DESCN"
        const val EXTRA_ATUALIZADA = "ATUALIZADAN"
        const val EXTRA_ERRO = "ERRON"
    }
}