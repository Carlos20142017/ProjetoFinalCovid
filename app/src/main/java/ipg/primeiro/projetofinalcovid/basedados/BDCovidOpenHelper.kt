package ipg.primeiro.projetofinalcovid.basedados

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BDCovidOpenHelper(context: Context?)
    : SQLiteOpenHelper(context, Nome_Base_Dados, null, VERSAO_BASE_DADOS) {
    override fun onCreate(db: SQLiteDatabase?) {

        if (db != null) {
            TabelaPessoas(db).cria()
            TabelaTestes(db).cria()
            TabelaNotificacao(db).cria()
            TabelaDistritos(db).cria()
            TabelaAlertas(db).cria()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object{
        const val Nome_Base_Dados = "Covid.db"
        const val VERSAO_BASE_DADOS = 1
    }
}