package ipg.primeiro.projetofinalcovid.classedastabelas

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import ipg.primeiro.projetofinalcovid.basedados.TabelaDistritos
import ipg.primeiro.projetofinalcovid.basedados.TabelaPessoas

data class Distrito(var id: Long = -1, var nome_distrito: String) {
    fun toContentValues(): ContentValues {
        val valores= ContentValues()
        valores.put(TabelaDistritos.CAMPO_NOME_DISTRITO, nome_distrito)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Distrito {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaDistritos.CAMPO_NOME_DISTRITO)

            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)

            return Distrito(id, nome)
        }
    }
}