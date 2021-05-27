package ipg.primeiro.projetofinalcovid.classedastabelas

import android.content.ContentValues
import ipg.primeiro.projetofinalcovid.basedados.TabelaPessoas
import java.util.*


data class Pessoa (var id: Long = -1, var nome: String, var sexo: String, var data_nascimento: Int, var id_estrang_distrito: Int){
     fun toContentValues(): ContentValues {
       val valores= ContentValues()
       valores.put(TabelaPessoas.CAMPO_NOME, nome)
       valores.put(TabelaPessoas.CAMPO_SEXO, sexo)
       valores.put(TabelaPessoas.CAMPO_DATA_NASCIMENTO, data_nascimento)
       valores.put(TabelaPessoas.CAMPO_ID_ESTRANG_DISTRITO, id_estrang_distrito)
       return valores
   }
}