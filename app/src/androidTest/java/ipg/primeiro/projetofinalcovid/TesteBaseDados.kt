package ipg.primeiro.projetofinalcovid

import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import ipg.primeiro.projetofinalcovid.basedados.BDCovidOpenHelper
import ipg.primeiro.projetofinalcovid.basedados.TabelaDistritos
import ipg.primeiro.projetofinalcovid.basedados.TabelaPessoas
import ipg.primeiro.projetofinalcovid.classedastabelas.Distrito
import ipg.primeiro.projetofinalcovid.classedastabelas.Pessoa


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TesteBaseDados {


    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBDCovidOpenHelper() = BDCovidOpenHelper(getAppContext())

    private  fun insereDistrito(tabela: TabelaDistritos, distrito: Distrito): Long {
        val id = tabela.insert(distrito.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun getDistritoBaseDados(tabela: TabelaDistritos, id: Long): Distrito {
        val cursor = tabela.query(
            TabelaDistritos.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Distrito.fromCursor(cursor)
    }

    private fun getPessoaBaseDados(tabela: TabelaPessoas, id: Long): Pessoa {
        val cursor = tabela.query(
            TabelaPessoas.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Pessoa.fromCursor(cursor)
    }

    private fun inserePesssoa(tabela: TabelaPessoas, pessoa: Pessoa): Long {
        val id = tabela.insert(pessoa.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    @Before
    fun apagaBaseDados(){
        getAppContext().deleteDatabase(BDCovidOpenHelper.Nome_Base_Dados)
    }

    @Test
    fun consegueAbrirBaseDados(){

        val db = getBDCovidOpenHelper().readableDatabase
        assert(db.isOpen)
        db.close()
    }

 //==========================================================
 //Tabela Distrito
 //=========================================================
    @Test
    fun consegueInserirDistritos(){
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaDistritos = TabelaDistritos(db)

        val distrito = Distrito(nome_distrito = "Guarda")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        db.close()

    }

    @Test
    fun consegueAlterarDistrito(){
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaDistritos = TabelaDistritos(db)

        val distrito = Distrito(nome_distrito = "Lisboa")
        distrito.id = insereDistrito(tabelaDistritos, distrito)
        distrito.nome_distrito = "LISBOA-PORTO"

        val registoAlterados = tabelaDistritos.update(
            distrito.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(distrito.id.toString())
        )

        assertEquals(1, registoAlterados)
        db.close()
    }

    @Test
    fun consegueEliminarDistritos(){
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaDistritos = TabelaDistritos(db)

        val distrito = Distrito(nome_distrito = "teste")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val registosEliminados= tabelaDistritos.delete(
            "${BaseColumns._ID}=?",
            arrayOf(distrito.id.toString())
        )

        assertEquals(1, registosEliminados)
        db.close()
    }

    @Test
    fun consegueLerDistritos() {
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaDistritos = TabelaDistritos(db)

        val distrito = Distrito(nome_distrito = "Viseu")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        assertEquals(distrito, getDistritoBaseDados(tabelaDistritos, distrito.id))

        db.close()
    }



    //==========================================================
    //Tabela Pessoa
    //=========================================================

    @Test
    fun consegueInserirPessoas(){
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaDistritos = TabelaDistritos(db)
        val distrito = Distrito(nome_distrito = "Guarda")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa(nome = "Carlos", sexo="Masculino", data_nascimento=10-1995, id_estrang_distrito=distrito.id)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        assertEquals(pessoa, getPessoaBaseDados(tabelaPessoas, pessoa.id))
        db.close()

    }




}