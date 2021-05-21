package ipg.primeiro.projetofinalcovid

import android.content.ContentValues
import ipg.primeiro.projetofinalcovid.basedados.TabelaPessoas

data class Pessoa (var id: Long = -1, var nome: String, var sexo: String, var idade: Int, var distrito: String){

    fun toContentValues(): ContentValues{
        val valores= ContentValues()
        valores.put(TabelaPessoas.CAMPO_NOME, nome)
        valores.put(TabelaPessoas.CAMPO_SEXO, sexo)
        valores.put(TabelaPessoas.CAMPO_IDADE, idade)
        valores.put(TabelaPessoas.CAMPO_NOME, distrito)
        return valores
    }
}