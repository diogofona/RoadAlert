package ipvc.estg.ecgm.diogo_araujo.roadalert

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditarAnotacao : AppCompatActivity() {

    private lateinit var titulo_ET: EditText
    private lateinit var descricao_ET: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_anotacao)

        val id = MainActivity.EXTRA_ID
        val titulo = MainActivity.EXTRA_TITULO
        val descricao = MainActivity.EXTRA_DESCRICAO
        val atualizada = MainActivity.EXTRA_ATUALIZADA

        titulo_ET = findViewById(R.id.anotacao_titulo)
        descricao_ET = findViewById(R.id.anotacao_desc)

        titulo_ET.setText(titulo)
        descricao_ET.setText(descricao)

    }

}