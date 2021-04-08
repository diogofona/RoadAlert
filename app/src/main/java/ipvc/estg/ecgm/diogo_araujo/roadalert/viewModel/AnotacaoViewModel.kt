package ipvc.estg.ecgm.diogo_araujo.roadalert.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ipvc.estg.ecgm.diogo_araujo.roadalert.db.AnotacaoDB
import ipvc.estg.ecgm.diogo_araujo.roadalert.db.AnotacaoRepository
import ipvc.estg.ecgm.diogo_araujo.roadalert.entities.Anotacao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnotacaoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AnotacaoRepository
    val allAnotacao: LiveData<List<Anotacao>>
    val lastAtualizada: LiveData<List<Anotacao>>
    //lateinit var AnotacaoFromTitulo: LiveData<List<Anotacao>>

    init {
        val anotacoesDao = AnotacaoDB.getDatabase(application, viewModelScope).anotacaoDao()
        repository = AnotacaoRepository(anotacoesDao)
        allAnotacao = repository.allAnotacao
        lastAtualizada = repository.lastAtualizada
    }

    fun insert(anotacao: Anotacao) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(anotacao)
    }

    fun update(id: Int, titulo: String, descricao: String, atualizada: String) = viewModelScope.launch(Dispatchers.IO){
        repository.update(id, titulo, descricao, atualizada)
    }

    fun delete(id: Int) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(id)
    }

    /*fun callAnotacaoFromTitulo(titulo: String) = viewModelScope.launch(Dispatchers.IO){
        repository.callAnotacaoFromTitulo(titulo)
    }*/
}