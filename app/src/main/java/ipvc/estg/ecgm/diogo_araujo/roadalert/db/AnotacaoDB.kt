package ipvc.estg.ecgm.diogo_araujo.roadalert.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ipvc.estg.ecgm.diogo_araujo.roadalert.dao.AnotacaoDao
import ipvc.estg.ecgm.diogo_araujo.roadalert.entities.Anotacao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Anotacao::class), version = 1, exportSchema = false)
public abstract class AnotacaoDB : RoomDatabase() {

    abstract fun anotacaoDao(): AnotacaoDao

    private class AnotacaoDatabaseCallback(private val scope: CoroutineScope) :RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase){
            super.onOpen(db)
            INSTANCE?.let { databse -> scope.launch {
                    var anotacaoDao = databse.anotacaoDao()

                    //add sample
                    var anotacao = Anotacao(1, "Nota1", "DESCRI", "1")
                    anotacaoDao.insert(anotacao)
                    anotacao = Anotacao(2, "Nota2", "DESCRI2", "2")
                    anotacaoDao.insert(anotacao)
                }
            }
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: AnotacaoDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AnotacaoDB {

            val tempInstace = INSTANCE
            if(tempInstace != null){
                return tempInstace
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AnotacaoDB::class.java, "anotacoes_database")
                    .addCallback(AnotacaoDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
