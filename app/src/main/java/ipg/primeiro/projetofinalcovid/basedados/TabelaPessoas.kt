package ipg.primeiro.projetofinalcovid.basedados

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPessoas(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE" +
                NOME_TABELA +
                "(" +
                BaseColumns._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CAMPO_NOME +
                " TEXT NOT NULL," +
                CAMPO_SEXO +
                "TEXT NOT NULL," +
                CAMPO_IDADE +
                " INTEGER NOT NULL," +
                CAMPO_DISTRITO +
                " TEXT NOT NULL)")

    }

    companion object{
        const val NOME_TABELA ="pessoas"
        const val CAMPO_NOME ="nome"
        const val CAMPO_SEXO ="sexo"
        const val CAMPO_IDADE="idade"
        const val CAMPO_DISTRITO ="distrito"

    }

}
