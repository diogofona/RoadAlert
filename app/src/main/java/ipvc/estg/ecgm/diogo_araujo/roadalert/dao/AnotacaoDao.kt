package ipvc.estg.ecgm.diogo_araujo.roadalert.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ipvc.estg.ecgm.diogo_araujo.roadalert.entities.Anotacao

@Dao
interface AnotacaoDao {

    // Select Data QUERYS
    @Query("SELECT * FROM Anotacao ORDER BY atualizada DESC")
    fun getAutoOrderedAnotacoes(): LiveData<List<Anotacao>>

    @Query("SELECT * FROM Anotacao ORDER BY atualizada ASC")
    fun getAutoOrderedAnotacoesInverted(): LiveData<List<Anotacao>>

    @Query("SELECT * FROM Anotacao ORDER BY titulo")
    fun getOrderedAnotacoesTitle(): LiveData<List<Anotacao>>

    @Query("SELECT * FROM Anotacao WHERE titulo == :titulo")
    fun getAnotacaoesFromTitulo(titulo: String): LiveData<Anotacao>

    @Query("SELECT * FROM Anotacao ORDER BY atualizada DESC LIMIT 1")
    fun getLastAtualizada(): LiveData<List<Anotacao>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(anotacao: Anotacao)

    @Query("DELETE FROM Anotacao WHERE id == :id")
    fun delete(id: Int)

    @Query("UPDATE Anotacao SET titulo = :titulo, descricao = :descricao, atualizada = :atualizada WHERE id == :id")
    fun update(id: Int, titulo: String, descricao: String, atualizada: String)

    @Query("DELETE FROM Anotacao")
    fun deleteAll()
}