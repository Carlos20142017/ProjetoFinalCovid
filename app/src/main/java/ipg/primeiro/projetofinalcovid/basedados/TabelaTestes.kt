package ipg.primeiro.projetofinalcovid.basedados

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class TabelaTestes(db: SQLiteDatabase) {
        private val db: SQLiteDatabase = db

        fun cria() {
            db.execSQL("CREATE TABLE" +
                    NOME_TABELA +
                    "(" +
                    BaseColumns._ID +
                    "INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CAMPO_TEMPERATURA +
                    " REAL NOT NULL," +
                    CAMPO_SINTOMAS +
                    " TEXT NOT NULL," +
                    CAMPO_EST_SAUDE +
                    " TEXT NOT NULL," +
                    CAMPO_ID_ESTRANG_PESSOAS +
                    " INTEGER NOT NULL," +
                    " FOREIGN KEY (" +
                    CAMPO_ID_ESTRANG_PESSOAS +
                    ") REFERENCES" +
                    "" +
                    TabelaPessoas.CAMPO_NOME +
                    ")")

        }

       companion object{
           const val NOME_TABELA ="testes"
           const val CAMPO_TEMPERATURA ="temperatura"
           const val CAMPO_SINTOMAS = "sintomas"
           const val CAMPO_EST_SAUDE = "est_saude"
           const val CAMPO_ID_ESTRANG_PESSOAS ="id_pessoas"

       }
    }