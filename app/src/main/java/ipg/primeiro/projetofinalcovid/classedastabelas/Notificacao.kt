package ipg.primeiro.projetofinalcovid.classedastabelas

import android.content.ContentValues
import ipg.primeiro.projetofinalcovid.basedados.TabelaNotificacao
import ipg.primeiro.projetofinalcovid.basedados.TabelaPessoas

data class Notificacao (var alerta: String, var descricao: String, var resultado: String) {
    fun toContentValues(): ContentValues {
        val valores= ContentValues()
        valores.put(TabelaNotificacao.CAMPO_ALERTA, alerta)
        valores.put(TabelaNotificacao.CAMPO_DESCRICAO, descricao)
        valores.put(TabelaNotificacao.CAMPO_RESULTADO, resultado)

        return valores
    }
}