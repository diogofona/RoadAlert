package ipvc.estg.ecgm.diogo_araujo.roadalert.db

import androidx.lifecycle.LiveData
import ipvc.estg.ecgm.diogo_araujo.roadalert.dao.AnotacaoDao
import ipvc.estg.ecgm.diogo_araujo.roadalert.entities.Anotacao

class AnotacaoRepository(private val anotacaoDao: AnotacaoDao) {

    val allAnotacao: LiveData<List<Anotacao>> = anotacaoDao.getAutoOrderedAnotacoes()
    val lastAtualizada: LiveData<List<Anotacao>> = anotacaoDao.getLastAtualizada()

    suspend fun insert(anotacao: Anotacao){
        anotacaoDao.insert(anotacao)
    }
}