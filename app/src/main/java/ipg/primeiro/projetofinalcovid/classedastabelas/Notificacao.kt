package ipg.primeiro.projetofinalcovid.classedastabelas

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import ipg.primeiro.projetofinalcovid.basedados.TabelaDistritos
import ipg.primeiro.projetofinalcovid.basedados.TabelaNotificacao
import ipg.primeiro.projetofinalcovid.basedados.TabelaPessoas

data class Notificacao (var id: Long = -1, var resultado: String, var id_estrang_testes: Long, var id_estrang_alertas: Long) {
    fun toContentValues(): ContentValues {
        val valores= ContentValues().apply {
            put(TabelaNotificacao.CAMPO_RESULTADO, resultado)
            put(TabelaNotificacao.CAMPO_ID_ESTRANG_TESTES, id_estrang_testes)
            put(TabelaNotificacao.CAMPO_ID_ESTRANG_ALERTAS, id_estrang_alertas)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Notificacao {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colResultado = cursor.getColumnIndex(TabelaNotificacao.CAMPO_RESULTADO)
            val colIdTeste = cursor.getColumnIndex(TabelaNotificacao.CAMPO_ID_ESTRANG_TESTES)
            val colIdAlerta = cursor.getColumnIndex(TabelaNotificacao.CAMPO_ID_ESTRANG_ALERTAS)

            val id = cursor.getLong(colId)
            val resultado = cursor.getString(colResultado)
            val idTeste = cursor.getLong(colIdTeste)
            val idAlerta = cursor.getLong(colIdAlerta)

            return Notificacao(id,resultado, idTeste, idAlerta)
        }
    }
}