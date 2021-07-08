package ipg.primeiro.projetofinalcovid.basedados

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class TabelaTestes(db: SQLiteDatabase) {
        private val db: SQLiteDatabase = db

        fun cria() {
            db.execSQL("CREATE TABLE $NOME_TABELA ( ${BaseColumns._ID } INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_TEMPERATURA REAL NOT NULL, $CAMPO_SINTOMAS TEXT NOT NULL, $CAMPO_EST_SAUDE TEXT NOT NULL,$CAMPO_DATA_TESTE INTEGER NOT NULL, $CAMPO_ID_ESTRANG_PESSOAS INTEGER NOT NULL, FOREIGN KEY ( $CAMPO_ID_ESTRANG_PESSOAS) REFERENCES ${TabelaPessoas.NOME_TABELA} )")

        }

    fun insert(values: ContentValues): Long {
        return db.insert(TabelaTestes.NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(TabelaTestes.NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete( whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(TabelaTestes.NOME_TABELA, whereClause, whereArgs)
    }


    fun query(columns: Array<String>, selection: String?, selectionArgs: Array<String>?, groupBy: String?, having: String?, orderBy: String?): Cursor? {
        val ultimaColuna = columns.size - 1
        var posColNomePessoa = -1 // - 1 indica que a coluna não foi pedido
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_Pessoas) {
                posColNomePessoa = i
                break
            }
        }

        if (posColNomePessoa == -1) {

            return db.query(
                TabelaTestes.NOME_TABELA,
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

            colunas += if(i == posColNomePessoa) {
                "${TabelaPessoas.NOME_TABELA}.${TabelaPessoas.CAMPO_NOME} AS ${CAMPO_EXTERNO_NOME_Pessoas}"
            }else{
                "${NOME_TABELA}.${columns[i]}"
            }
        }

        val tabelas = "${NOME_TABELA} INNER JOIN ${TabelaPessoas.NOME_TABELA} ON ${TabelaPessoas.NOME_TABELA}.${BaseColumns._ID}=${CAMPO_ID_ESTRANG_PESSOAS}"

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
           const val NOME_TABELA ="TESTES"
           const val CAMPO_TEMPERATURA ="temperatura"
           const val CAMPO_SINTOMAS = "sintomas"
           const val CAMPO_EST_SAUDE = "est_saude"
           const val CAMPO_DATA_TESTE = "data_teste"
           const val CAMPO_ID_ESTRANG_PESSOAS ="id_pessoas"
           const val CAMPO_EXTERNO_NOME_Pessoas = "nome_pessoas"

           val TODAS_COLUNAS = arrayOf(BaseColumns._ID,
               CAMPO_TEMPERATURA,
               CAMPO_SINTOMAS,
               CAMPO_EST_SAUDE,
               CAMPO_DATA_TESTE,
               CAMPO_ID_ESTRANG_PESSOAS,
               CAMPO_EXTERNO_NOME_Pessoas
           )
       }
    }