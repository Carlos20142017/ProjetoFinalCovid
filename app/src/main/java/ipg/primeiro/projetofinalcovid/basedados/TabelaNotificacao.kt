package ipg.primeiro.projetofinalcovid.basedados

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaNotificacao(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE " +
                NOME_TABELA +
                "(" +
                BaseColumns._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CAMPO_ALERTA +
                " TEXT NOT NULL," +
                CAMPO_DESCRICAO +
                " TEXT NOT NULL," +
                CAMPO_RESULTADO +
                "TEXT NOT NULL," +
                CAMPO_ID_ESTRANG_TESTES +
                "INTEGER NOT NULL," +
                " FOREIGN KEY (" +
                CAMPO_ID_ESTRANG_TESTES +
                ") REFERENCES" +
                TabelaTestes.NOME_TABELA +
                ")")

    }

    companion object{
        const val NOME_TABELA="NOTIFICACAO"
        const val CAMPO_ALERTA ="alerta"
        const val CAMPO_DESCRICAO ="descricao"
        const val CAMPO_RESULTADO ="resultado"
        const val CAMPO_ID_ESTRANG_TESTES ="id_testes"
    }
}