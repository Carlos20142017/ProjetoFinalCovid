package ipg.primeiro.projetofinalcovid.basedados

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPessoas(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL, $CAMPO_SEXO TEXT NOT NULL, $CAMPO_DATA_NASCIMENTO INTEGER NOT NULL, $CAMPO_TELEMOVEL TEXT NOT NULL, ${CAMPO_ID_ESTRANG_DISTRITO} INTEGER NOT NULL, FOREIGN KEY ( ${CAMPO_ID_ESTRANG_DISTRITO}) REFERENCES ${TabelaDistritos.NOME_TABELA})")

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
        val ultimaColuna = columns.size - 1
        var posColNomeDistrito = -1 // - 1 indica que a coluna não foi pedido
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_DISTRITO) {
                posColNomeDistrito = i
                break
            }
        }

        if (posColNomeDistrito == -1) {

            return db.query(
                TabelaPessoas.NOME_TABELA,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy
            )

        }
        var colunas = ""
        for (i in 0..ultimaColuna){

           if(i > 0) colunas +=","

          colunas += if(i == posColNomeDistrito) {
                "${TabelaDistritos.NOME_TABELA}.${TabelaDistritos.CAMPO_NOME_DISTRITO} AS $CAMPO_EXTERNO_NOME_DISTRITO"
            }else{
              "$NOME_TABELA.${columns[i]}"
            }
        }

        val tabelas = "$NOME_TABELA INNER JOIN ${TabelaDistritos.NOME_TABELA} ON ${TabelaDistritos.NOME_TABELA}.${BaseColumns._ID}=$CAMPO_ID_ESTRANG_DISTRITO"

        var sql ="SELECT $colunas FROM $tabelas"
        if(selection != null) sql += " WHERE $selection"

        if (groupBy != null){
            sql += " GROUP BY $groupBy"
            if(having != null) " HAVING $having"
        }

        if(orderBy != null){
            sql += " ORDER BY $orderBy"
        }

       return db.rawQuery(sql, selectionArgs)
    }

    companion object{
        const val NOME_TABELA ="PESSOAS"
        const val CAMPO_NOME ="nome"
        const val CAMPO_SEXO ="sexo"
        const val CAMPO_DATA_NASCIMENTO="data_nascimento"
        const val CAMPO_TELEMOVEL="telemovel"
        const val CAMPO_ID_ESTRANG_DISTRITO ="id_distritos"
        const val CAMPO_EXTERNO_NOME_DISTRITO = "nome_distrito"

        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, CAMPO_NOME, CAMPO_SEXO, CAMPO_DATA_NASCIMENTO,
            CAMPO_TELEMOVEL, CAMPO_ID_ESTRANG_DISTRITO, CAMPO_EXTERNO_NOME_DISTRITO)

    }

}
