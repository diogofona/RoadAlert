package ipvc.estg.ecgm.diogo_araujo.roadalert.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Anotacao")

class Anotacao (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "titulo") val titulo: String?,
    @ColumnInfo(name = "descricao") val descricao: String,
    @ColumnInfo(name = "atualizada") val atualizada: String?
)