package ipvc.estg.ecgm.diogo_araujo.roadalert

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ipvc.estg.ecgm.diogo_araujo.roadalert.adapters.AnotacaoAdapter
import ipvc.estg.ecgm.diogo_araujo.roadalert.dao.AnotacaoDao
import ipvc.estg.ecgm.diogo_araujo.roadalert.db.AnotacaoDB
import ipvc.estg.ecgm.diogo_araujo.roadalert.db.AnotacaoRepository
import ipvc.estg.ecgm.diogo_araujo.roadalert.entities.Anotacao
import ipvc.estg.ecgm.diogo_araujo.roadalert.viewModel.AnotacaoViewModel
import kotlinx.android.synthetic.main.recyclerview_item.*

class MainActivity : AppCompatActivity(), CellClickListener {

    private lateinit var anotacaoViewModel: AnotacaoViewModel
    private val newAnotacaoActivityCode = 1
    private val editAnotacaoActivityCode = 2
    private lateinit var database: AnotacaoRepository
    private val Mrecente = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recylerview)
        val adapter = AnotacaoAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val recyclerViewbackanotacao = findViewById<RecyclerView>(R.id.recylerviewbackanotacao)
        val adapter2 = AnotacaoAdapter(this, this)
        recyclerViewbackanotacao.adapter = adapter2
        recyclerViewbackanotacao.layoutManager = LinearLayoutManager(this)

        //view model
        anotacaoViewModel = ViewModelProvider(this).get(AnotacaoViewModel::class.java)
        anotacaoViewModel.allAnotacao.observe(this, { anotacoes -> anotacoes?.let {adapter.setAnotacoes(it)}})
        anotacaoViewModel.lastAtualizada.observe(this, { anotacoes -> anotacoes?.let {adapter2.setAnotacoes(it)}})

        //Fab
        val fab = findViewById<ImageView>(R.id.add_anotacao)
        fab.setOnClickListener{
            val intent = Intent(this@MainActivity, NovaAnotacao::class.java)
            startActivityForResult(intent, newAnotacaoActivityCode)
        }

        //Toast.makeText(this ,AnotacaoAtualizada.text, Toast.LENGTH_SHORT).show()
    }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if(requestCode == newAnotacaoActivityCode) {
                if (resultCode == Activity.RESULT_OK) {
                    val titulo = data?.getStringExtra(NovaAnotacao.EXTRA_TITULO).toString()
                    val descricao = data?.getStringExtra(NovaAnotacao.EXTRA_DESC).toString()
                    val atualizada = data?.getStringExtra(NovaAnotacao.EXTRA_ATUALIZADA).toString()
                    val anotacao =
                        Anotacao(titulo = titulo, descricao = descricao, atualizada = atualizada)
                    anotacaoViewModel.insert(anotacao)
                } else {
                    Toast.makeText(
                        applicationContext,
                        data?.getStringExtra(NovaAnotacao.EXTRA_ERRO).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else if(requestCode == editAnotacaoActivityCode) {
                if(resultCode == Activity.RESULT_OK){
                    if(data?.action == "DELETE"){
                        val id = data?.getStringExtra(EXTRA_ID).toString()
                        anotacaoViewModel.delete(Integer.parseInt(id))
                    } else {
                        val id = data?.getStringExtra(EXTRA_ID).toString()
                        val titulo = data?.getStringExtra(EXTRA_TITULO).toString()
                        val descricao = data?.getStringExtra(EXTRA_DESCRICAO).toString()
                        val atualizada = data?.getStringExtra(EXTRA_ATUALIZADA).toString()
                        anotacaoViewModel.update(Integer.parseInt(id), titulo, descricao, atualizada)
                    }
                } else {
                    Toast.makeText(applicationContext,
                        data?.getStringExtra(EXTRA_ERRO).toString(),
                        Toast.LENGTH_LONG).show()
                }
            }
        }

    override fun onCellClickListener(anotacao: Anotacao){
        //Toast.makeText(this,"${data.id}", Toast.LENGTH_LONG).show()
        val id = anotacao.id.toString()
        val titulo = anotacao.titulo
        val descricao = anotacao.descricao
        val atualizada = anotacao.atualizada
        val intent = Intent(this, EditarAnotacao::class.java).apply {
            putExtra(EXTRA_ID, id)
            putExtra(EXTRA_TITULO, titulo)
            putExtra(EXTRA_DESCRICAO, descricao)
            putExtra(EXTRA_ATUALIZADA, atualizada)
        }
        //Toast.makeText(this,""+id+" "+titulo, Toast.LENGTH_LONG).show()
        startActivityForResult(intent, editAnotacaoActivityCode)
    }

    companion object {
        const val EXTRA_ID = "ID"
        const val EXTRA_TITULO = "TITULO"
        const val EXTRA_DESCRICAO = "DESCRICAO"
        const val EXTRA_ATUALIZADA = "ATUALIZADA"
        const val EXTRA_ERRO = "ERRO"
    }
}
