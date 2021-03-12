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

    init {
        val anotacoesDao = AnotacaoDB.getDatabase(application, viewModelScope).anotacaoDao()
        repository = AnotacaoRepository(anotacoesDao)
        allAnotacao = repository.allAnotacao
    }

    fun insert(anotacao: Anotacao) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(anotacao)
    }
}