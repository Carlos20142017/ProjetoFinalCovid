package ipg.primeiro.projetofinalcovid.basedados

import android.database.sqlite.SQLiteDatabase

class TabelaNotificacao(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE notificacao(_id INTEGER PRIMARY KEY AUTOINCREMENT, alerta TEXT NOT NULL, descricao TEXT NOT NULL, resultado TEXT NOT NULL)")

    }

}