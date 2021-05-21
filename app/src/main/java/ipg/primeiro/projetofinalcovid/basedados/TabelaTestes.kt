package ipg.primeiro.projetofinalcovid.basedados

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class TabelaTestes(db: SQLiteDatabase) {
        private val db: SQLiteDatabase = db

        fun cria() {
            db.execSQL("CREATE TABLE $NOME_TABELA ( ${BaseColumns._ID } INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_TEMPERATURA REAL NOT NULL, $CAMPO_SINTOMAS TEXT NOT NULL, $CAMPO_EST_SAUDE TEXT NOT NULL, $CAMPO_ID_ESTRANG_PESSOAS INTEGER NOT NULL, FOREIGN KEY ( $CAMPO_ID_ESTRANG_PESSOAS) REFERENCES ${TabelaPessoas.NOME_TABELA} )")

        }

    fun insert(values: ContentValues): Long {
        return db.insert(TabelaPessoas.NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(TabelaPessoas.NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(TabelaPessoas.NOME_TABELA, whereClause, whereArgs)
    }

    fun query(columns: Array<String>, selection: String, selectionArgs: Array<String>, groupBy: String, having: String, orderBy: String): Cursor? {
        return db.query(TabelaPessoas.NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

       companion object{
           const val NOME_TABELA ="testes"
           const val CAMPO_TEMPERATURA ="temperatura"
           const val CAMPO_SINTOMAS = "sintomas"
           const val CAMPO_EST_SAUDE = "est_saude"
           const val CAMPO_ID_ESTRANG_PESSOAS ="id_pessoas"

       }
    }