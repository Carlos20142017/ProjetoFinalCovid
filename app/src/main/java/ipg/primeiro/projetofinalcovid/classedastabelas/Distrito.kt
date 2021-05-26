package ipg.primeiro.projetofinalcovid.classedastabelas

import android.content.ContentValues
import ipg.primeiro.projetofinalcovid.basedados.TabelaDistritos
import ipg.primeiro.projetofinalcovid.basedados.TabelaPessoas

data class Distrito(var id: Long = -1, var nome_distrito: String) {
    fun toContentValues(): ContentValues {
        val valores= ContentValues()
        valores.put(TabelaDistritos.CAMPO_NOME_DISTRITO, nome_distrito)

        return valores
    }
}