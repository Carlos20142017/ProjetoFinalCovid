package ipg.primeiro.projetofinalcovid.basedados

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPessoas(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL, $CAMPO_SEXO TEXT NOT NULL, $CAMPO_DATA_NASCIMENTO INTEGER NOT NULL,${CAMPO_ID_ESTRANG_DISTRITO} INTEGER NOT NULL, FOREIGN KEY ( ${CAMPO_ID_ESTRANG_DISTRITO}) REFERENCES ${TabelaDistritos.NOME_TABELA})")

    }

    fun insert(values: ContentValues): Long {
        return db.insert(TabelaPessoas.NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(TabelaPessoas.NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete( whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(TabelaPessoas.NOME_TABELA, whereClause, whereArgs)
    }

    fun query(columns: Array<String>, selection: String?, selectionArgs: Array<String>?, groupBy: String?, having: String?, orderBy: String?): Cursor? {
        return db.query(TabelaPessoas.NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object{
        const val NOME_TABELA ="pessoas"
        const val CAMPO_NOME ="nome"
        const val CAMPO_SEXO ="sexo"
        const val CAMPO_DATA_NASCIMENTO="data_nascimento"
        const val CAMPO_ID_ESTRANG_DISTRITO ="id_distritos"

    }

}
