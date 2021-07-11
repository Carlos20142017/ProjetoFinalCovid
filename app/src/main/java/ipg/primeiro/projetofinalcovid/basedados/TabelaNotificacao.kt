package ipg.primeiro.projetofinalcovid.basedados

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaNotificacao(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE  $NOME_TABELA ( ${BaseColumns._ID}  INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_RESULTADO  TEXT NOT NULL, $CAMPO_ID_ESTRANG_TESTES  INTEGER NOT NULL, $CAMPO_ID_ESTRANG_ALERTAS  INTEGER NOT NULL, FOREIGN KEY ( $CAMPO_ID_ESTRANG_TESTES) REFERENCES ${TabelaTestes.NOME_TABELA}, FOREIGN KEY ( $CAMPO_ID_ESTRANG_ALERTAS) REFERENCES ${TabelaAlertas.NOME_TABELA})")

    }

    fun insert(values: ContentValues): Long {
        return db.insert(TabelaNotificacao.NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(TabelaNotificacao.NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete( whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(TabelaNotificacao.NOME_TABELA, whereClause, whereArgs)
    }

   /* fun query(columns: Array<String>, selection: String?, selectionArgs: Array<String>?, groupBy: String?, having: String?, orderBy: String?): Cursor? {
        return db.query(TabelaNotificacao.NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }*/










    fun query(columns: Array<String>, selection: String?, selectionArgs: Array<String>?, groupBy: String?, having: String?, orderBy: String?): Cursor? {
        val ultimaColuna = columns.size - 1
        var posColNomeAlerta = -1 // - 1 indica que a coluna não foi pedido
        var posColTemperatura = -1

        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_ALERTA) {
                posColNomeAlerta = i
                //break
            }else if (columns[i] == CAMPO_EXTERNO_TEMPERATURA) {
                posColTemperatura = i
                //break
            }
        }

        if (posColNomeAlerta == -1 && posColTemperatura == -1) {

            return db.query(
                NOME_TABELA,
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

            colunas += if(i == posColNomeAlerta) {
                "${TabelaAlertas.NOME_TABELA}.${TabelaAlertas.CAMPO_NOME_ALERTA} AS ${CAMPO_EXTERNO_NOME_ALERTA}"
            }else if(i == posColTemperatura) {
                "${TabelaTestes.NOME_TABELA}.${TabelaTestes.CAMPO_TEMPERATURA} AS ${CAMPO_EXTERNO_TEMPERATURA}"
            }
            else{
                "${NOME_TABELA}.${columns[i]}"
            }
        }


        val tabelas = "${NOME_TABELA} INNER JOIN ${TabelaAlertas.NOME_TABELA} ON ${TabelaAlertas.NOME_TABELA}.${BaseColumns._ID}=${CAMPO_ID_ESTRANG_ALERTAS}" +
                " INNER JOIN ${TabelaTestes.NOME_TABELA} ON ${TabelaTestes.NOME_TABELA}.${BaseColumns._ID}=${CAMPO_ID_ESTRANG_TESTES}"

       // val tabelas2 = "${NOME_TABELA} INNER JOIN ${TabelaTestes.NOME_TABELA} ON ${TabelaTestes.NOME_TABELA}.${BaseColumns._ID}=${CAMPO_ID_ESTRANG_TESTES}"

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
        const val NOME_TABELA="NOTIFICACAO"
        const val CAMPO_RESULTADO ="resultado"
        const val CAMPO_ID_ESTRANG_TESTES ="id_testes"
        const val CAMPO_ID_ESTRANG_ALERTAS ="id_alertas"

        const val CAMPO_EXTERNO_TEMPERATURA = "nome_temperatura"
        const val CAMPO_EXTERNO_NOME_ALERTA = "nome_externo_alerta"

        val TODAS_COLUNAS = arrayOf(BaseColumns._ID,
            CAMPO_RESULTADO,
            CAMPO_ID_ESTRANG_TESTES,
            CAMPO_ID_ESTRANG_ALERTAS,
            CAMPO_EXTERNO_NOME_ALERTA,
            CAMPO_EXTERNO_TEMPERATURA
        )
    }
}