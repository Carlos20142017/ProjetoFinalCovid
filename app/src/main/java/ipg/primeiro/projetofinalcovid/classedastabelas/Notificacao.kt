package ipg.primeiro.projetofinalcovid.classedastabelas

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import ipg.primeiro.projetofinalcovid.basedados.TabelaDistritos
import ipg.primeiro.projetofinalcovid.basedados.TabelaNotificacao
import ipg.primeiro.projetofinalcovid.basedados.TabelaPessoas

data class Notificacao (var id: Long = -1, var descricao: String, var resultado: String, var id_estrang_testes: Long) {
    fun toContentValues(): ContentValues {
        val valores= ContentValues().apply {
            put(TabelaNotificacao.CAMPO_DESCRICAO, descricao)
            put(TabelaNotificacao.CAMPO_RESULTADO, resultado)
            put(TabelaNotificacao.CAMPO_ID_ESTRANG_TESTES, id_estrang_testes)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Notificacao {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colDescricao = cursor.getColumnIndex(TabelaNotificacao.CAMPO_DESCRICAO)
            val colResultado = cursor.getColumnIndex(TabelaNotificacao.CAMPO_RESULTADO)
            val colIdTeste = cursor.getColumnIndex(TabelaNotificacao.CAMPO_ID_ESTRANG_TESTES)

            val id = cursor.getLong(colId)
            val descricao = cursor.getString(colDescricao)
            val resultado = cursor.getString(colResultado)
            val idTeste = cursor.getLong(colIdTeste)

            return Notificacao(id, descricao, resultado, idTeste)
        }
    }
}