package ipvc.estg.ecgm.diogo_araujo.roadalert

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ipvc.estg.ecgm.diogo_araujo.roadalert.adapters.AnotacaoAdapter
import ipvc.estg.ecgm.diogo_araujo.roadalert.entities.Anotacao
import ipvc.estg.ecgm.diogo_araujo.roadalert.viewModel.AnotacaoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var anotacaoViewModel: AnotacaoViewModel
    private val newAnotacaoActivityCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recylerview)
        val adapter = AnotacaoAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //view model
        anotacaoViewModel = ViewModelProvider(this).get(AnotacaoViewModel::class.java)
        anotacaoViewModel.allAnotacao.observe(this, { anotacoes -> anotacoes?.let {adapter.setAnotacoes(it)}})

        //Fab
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val intent = Intent(this@MainActivity, NovaAnotacao::class.java)
            startActivityForResult(intent, newAnotacaoActivityCode)
        }
    }

    //override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    //    super.onActivityResult(requestCode, resultCode, data)
    //    if(requestCode == newAnotacaoActivityCode && resultCode == Activity.RESULT_OK){
    //        data?.getStringExtra(NovaAnotacao.EXTRA_REPLY)?.let {
    //            val anotacao = Anotacao(titulo = it, descricao = it, atualizada = it)
    //            anotacaoViewModel.insert(anotacao)
    //        }
    //    } else {
    //        Toast.makeText(applicationContext, "Titulo vazio: não inserido", Toast.LENGTH_LONG).show()
     //   }
    //}
}
