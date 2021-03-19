package ipvc.estg.ecgm.diogo_araujo.roadalert.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ipvc.estg.ecgm.diogo_araujo.roadalert.dao.AnotacaoDao
import ipvc.estg.ecgm.diogo_araujo.roadalert.entities.Anotacao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnotacaoRepository(private val anotacaoDao: AnotacaoDao) {

    val allAnotacao: LiveData<List<Anotacao>> = anotacaoDao.getAutoOrderedAnotacoes()
    val lastAtualizada: LiveData<List<Anotacao>> = anotacaoDao.getLastAtualizada()

    suspend fun insert(anotacao: Anotacao){
        anotacaoDao.insert(anotacao)
    }

    suspend fun update(id: Int, titulo: String, descricao: String, atualizada: String){
        anotacaoDao.update(id, titulo, descricao, atualizada)
    }

    suspend fun delete(id: Int){
        anotacaoDao.delete(id)
    }
}