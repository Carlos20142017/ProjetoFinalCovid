package ipg.primeiro.projetofinalcovid

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import ipg.primeiro.projetofinalcovid.basedados.BDCovidOpenHelper
import ipg.primeiro.projetofinalcovid.basedados.TabelaPessoas
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

    private fun getBDCovidOpenHelper() = BDCovidOpenHelper(getAppContext())

    @Test
    fun consegueInserirPessoas(){
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaPessoas = TabelaPessoas(db)

        val id = tabelaPessoas.insert(Pessoa(nome ="José",sexo = "Masculino", data_nascimento = 25/10/1990).toContentValues())
        assertNotEquals(-1, id)
        db.close()

    }
}