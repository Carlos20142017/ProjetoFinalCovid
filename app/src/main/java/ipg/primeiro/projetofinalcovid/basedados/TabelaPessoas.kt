package ipg.primeiro.projetofinalcovid.basedados

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPessoas(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE testes(_id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, sexo TEXT NOT NULL, idade INTEGER NOT NULL, distrito TEXT NOT NULL, id_teste INTEGER NOT NULL, FOREIGN KEY (id_teste) REFERENCES testes)")

    }

}
