package ipg.primeiro.projetofinalcovid.classedastabelas

import android.content.ContentValues
import ipg.primeiro.projetofinalcovid.basedados.TabelaPessoas

data class Pessoa (var id: Long = -1, var nome: String, var sexo: String, var data_nascimento: Int){

    fun toContentValues(): ContentValues{
        val valores= ContentValues()
        valores.put(TabelaPessoas.CAMPO_NOME, nome)
        valores.put(TabelaPessoas.CAMPO_SEXO, sexo)
        valores.put(TabelaPessoas.CAMPO_DATA_NASCIMENTO, data_nascimento)

        return valores
    }
}