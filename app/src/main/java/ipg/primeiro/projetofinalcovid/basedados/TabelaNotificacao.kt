package ipg.primeiro.projetofinalcovid.basedados

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaNotificacao(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE  $NOME_TABELA ( ${BaseColumns._ID}  INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_ALERTA  TEXT NOT NULL, $CAMPO_DESCRICAO  TEXT NOT NULL,$CAMPO_RESULTADO  TEXT NOT NULL, $CAMPO_ID_ESTRANG_TESTES  INTEGER NOT NULL, FOREIGN KEY ( $CAMPO_ID_ESTRANG_TESTES) REFERENCES ${TabelaTestes.NOME_TABELA})")

    }

    fun insert(values: ContentValues): Long {
        return db.insert(TabelaTestes.NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(TabelaTestes.NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(TabelaTestes.NOME_TABELA, whereClause, whereArgs)
    }

    fun query(columns: Array<String>, selection: String, selectionArgs: Array<String>, groupBy: String, having: String, orderBy: String): Cursor? {
        return db.query(TabelaTestes.NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object{
        const val NOME_TABELA="NOTIFICACAO"
        const val CAMPO_ALERTA ="alerta"
        const val CAMPO_DESCRICAO ="descricao"
        const val CAMPO_RESULTADO ="resultado"
        const val CAMPO_ID_ESTRANG_TESTES ="id_testes"
    }
}