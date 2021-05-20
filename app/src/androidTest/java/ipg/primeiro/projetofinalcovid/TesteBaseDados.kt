package ipg.primeiro.projetofinalcovid

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import ipg.primeiro.projetofinalcovid.basedados.BDCovidOpenHelper

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

        val openHelper = BDCovidOpenHelper(getAppContext())
        val db = openHelper.readableDatabase
        assert(db.isOpen)
        db.close()
    }
}