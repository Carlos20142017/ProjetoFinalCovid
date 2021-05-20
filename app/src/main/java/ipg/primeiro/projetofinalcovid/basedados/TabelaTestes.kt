package ipg.primeiro.projetofinalcovid.basedados

import android.database.sqlite.SQLiteDatabase


class TabelaTestes(db: SQLiteDatabase) {
        private val db: SQLiteDatabase = db

        fun cria() {
            db.execSQL("CREATE TABLE testes(_id INTEGER PRIMARY KEY AUTOINCREMENT, temperatura REAL NOT NULL, sintomas TEXT NOT NULL, est_saude TEXT NOT NULL, id_notificacao INTEGER NOT NULL, FOREIGN KEY (id_notificacao) REFERENCES notificacao)")

        }

    }