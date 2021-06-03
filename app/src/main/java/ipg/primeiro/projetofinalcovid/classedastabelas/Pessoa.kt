package ipg.primeiro.projetofinalcovid.classedastabelas

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import ipg.primeiro.projetofinalcovid.basedados.TabelaPessoas
import java.util.*


data class Pessoa (var id: Long = -1, var nome: String, var sexo: String, var data_nascimento: Int, var id_estrang_distrito: Long){
     fun toContentValues(): ContentValues {
       val valores= ContentValues().apply {
           put(TabelaPessoas.CAMPO_NOME, nome)
           put(TabelaPessoas.CAMPO_SEXO, sexo)
           put(TabelaPessoas.CAMPO_DATA_NASCIMENTO, data_nascimento)
           put(TabelaPessoas.CAMPO_ID_ESTRANG_DISTRITO, id_estrang_distrito)
       }

       return valores
   }

    companion object {
        fun fromCursor(cursor: Cursor): Pessoa {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaPessoas.CAMPO_NOME)
            val colSexo = cursor.getColumnIndex(TabelaPessoas.CAMPO_SEXO)
            val colDataNascimento = cursor.getColumnIndex(TabelaPessoas.CAMPO_DATA_NASCIMENTO)
            val colIdDistrito = cursor.getColumnIndex(TabelaPessoas.CAMPO_ID_ESTRANG_DISTRITO)

            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)
            val sexo = cursor.getString(colSexo)
            val dataNascimento = cursor.getInt(colDataNascimento)
            val idDistrito = cursor.getLong(colIdDistrito)

            return Pessoa (id, nome, sexo, dataNascimento, idDistrito)
        }

    }
}