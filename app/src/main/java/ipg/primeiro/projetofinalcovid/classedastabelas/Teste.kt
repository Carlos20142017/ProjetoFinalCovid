package ipg.primeiro.projetofinalcovid.classedastabelas

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import ipg.primeiro.projetofinalcovid.basedados.TabelaNotificacao
import ipg.primeiro.projetofinalcovid.basedados.TabelaPessoas
import ipg.primeiro.projetofinalcovid.basedados.TabelaTestes
import java.util.*

data class Teste(var id: Long = -1, var temperatura: Float, var sintomas: String, var estado_saude: String, var data_teste: Date,
                 var id_estrang_pessoas: Long, var nomePessoa: String? = null) {
    fun toContentValues(): ContentValues {
        val valores= ContentValues().apply {
            put(TabelaTestes.CAMPO_TEMPERATURA, temperatura)
            put(TabelaTestes.CAMPO_SINTOMAS, sintomas)
            put(TabelaTestes.CAMPO_EST_SAUDE, estado_saude)
            put(TabelaTestes.CAMPO_DATA_TESTE, data_teste.time)
            put(TabelaTestes.CAMPO_ID_ESTRANG_PESSOAS, id_estrang_pessoas)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Teste {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colTemperatura = cursor.getColumnIndex(TabelaTestes.CAMPO_TEMPERATURA)
            val colSintomas = cursor.getColumnIndex(TabelaTestes.CAMPO_SINTOMAS)
            val colSaude = cursor.getColumnIndex(TabelaTestes.CAMPO_EST_SAUDE)
            val colIdPessoas = cursor.getColumnIndex(TabelaTestes.CAMPO_ID_ESTRANG_PESSOAS)
            val colDataTeste = cursor.getColumnIndex(TabelaTestes.CAMPO_DATA_TESTE)
            val colNomePessoas = cursor.getColumnIndex(TabelaTestes.CAMPO_EXTERNO_NOME_Pessoas)

            val id = cursor.getLong(colId)
            val temperatura = cursor.getFloat(colTemperatura)
            val sintomas = cursor.getString(colSintomas)
            val estadoSaude = cursor.getString(colSaude)
            val dataTeste = cursor.getLong(colDataTeste)
            val idPessoas = cursor.getLong(colIdPessoas)
            val nomePessoas = if(colNomePessoas != -1) cursor.getString(colNomePessoas) else null

            return Teste(id, temperatura, sintomas, estadoSaude, Date(dataTeste), idPessoas, nomePessoas)
        }
    }
}