package ipg.primeiro.projetofinalcovid.classedastabelas

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import ipg.primeiro.projetofinalcovid.basedados.TabelaPessoas
import java.util.*


data class Pessoa (var id: Long = -1, var nome: String, var sexo: String, var data_nascimento: Date,
                   var telemovel: String, var id_estrang_distrito: Long, var nomeDistrito: String? = null){
     fun toContentValues(): ContentValues {
       val valores= ContentValues().apply {
           put(TabelaPessoas.CAMPO_NOME, nome)
           put(TabelaPessoas.CAMPO_SEXO, sexo)
           put(TabelaPessoas.CAMPO_DATA_NASCIMENTO, data_nascimento.time)
           put(TabelaPessoas.CAMPO_TELEMOVEL, telemovel)
           put(TabelaPessoas.CAMPO_ID_ESTRANG_DISTRITO, id_estrang_distrito)
       }


       return valores
   }

    /*
    override fun equals(other: Any?): Boolean {
        if(!(other is Pessoa )) return false
        if(id != other.id) return false
        if(nome != other.nome) return false
        if(sexo != other.sexo) return false
        if(data_nascimento!= other.data_nascimento) return false
        if(telemovel != other.telemovel) return false

        return true
    }

     */

    companion object {
        fun fromCursor(cursor: Cursor): Pessoa {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaPessoas.CAMPO_NOME)
            val colSexo = cursor.getColumnIndex(TabelaPessoas.CAMPO_SEXO)
            val colDataNascimento = cursor.getColumnIndex(TabelaPessoas.CAMPO_DATA_NASCIMENTO)
            val colTelemovel = cursor.getColumnIndex(TabelaPessoas.CAMPO_TELEMOVEL)
            val colIdDistrito = cursor.getColumnIndex(TabelaPessoas.CAMPO_ID_ESTRANG_DISTRITO)
            val colNomeDistrito = cursor.getColumnIndex(TabelaPessoas.CAMPO_EXTERNO_NOME_DISTRITO)

            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)
            val sexo = cursor.getString(colSexo)
            val dataNascimento = cursor.getLong(colDataNascimento)
            val telemovel = cursor.getString(colTelemovel)
            val idDistrito = cursor.getLong(colIdDistrito)
            val nomeDistrito = if(colNomeDistrito != -1) cursor.getString(colNomeDistrito) else null

            return Pessoa (id, nome, sexo, Date(dataNascimento), telemovel, idDistrito, nomeDistrito)
        }

    }
}