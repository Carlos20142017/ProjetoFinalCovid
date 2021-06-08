package ipg.primeiro.projetofinalcovid.classedastabelas

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import ipg.primeiro.projetofinalcovid.basedados.TabelaAlertas

data class Alerta(var id: Long = -1, var nome_alerta: String, var descricao: String) {
    fun toContentValues(): ContentValues {
        val valores= ContentValues()
        valores.put(TabelaAlertas.CAMPO_NOME_ALERTA, nome_alerta)
        valores.put(TabelaAlertas.CAMPO_DESCRICAO, descricao)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Alerta {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNomeAlerta = cursor.getColumnIndex(TabelaAlertas.CAMPO_NOME_ALERTA)
            val colDescricao = cursor.getColumnIndex(TabelaAlertas.CAMPO_DESCRICAO)

            val id = cursor.getLong(colId)
            val nomeAlerta = cursor.getString(colNomeAlerta)
            val descricao = cursor.getString(colDescricao)

            return Alerta(id, nomeAlerta, descricao)
        }
    }
}