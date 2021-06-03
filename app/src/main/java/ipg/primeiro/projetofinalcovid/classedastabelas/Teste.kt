package ipg.primeiro.projetofinalcovid.classedastabelas

import android.content.ContentValues
import ipg.primeiro.projetofinalcovid.basedados.TabelaNotificacao
import ipg.primeiro.projetofinalcovid.basedados.TabelaPessoas
import ipg.primeiro.projetofinalcovid.basedados.TabelaTestes

data class Teste(var temperatura: Float, var sintomas: String, var estado_saude: String, var id_estrang_pessoas: Int) {
    fun toContentValues(): ContentValues {
        val valores= ContentValues()
        valores.put(TabelaTestes.CAMPO_TEMPERATURA, temperatura)
        valores.put(TabelaTestes.CAMPO_SINTOMAS, sintomas)
        valores.put(TabelaTestes.CAMPO_EST_SAUDE, estado_saude)
        valores.put(TabelaTestes.CAMPO_ID_ESTRANG_PESSOAS, id_estrang_pessoas)
        return valores
    }
}