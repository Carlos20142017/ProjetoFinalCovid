package ipg.primeiro.projetofinalcovid

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ipg.primeiro.projetofinalcovid.AlertaFragment.EditaAlertaFragment
import ipg.primeiro.projetofinalcovid.AlertaFragment.EliminaAlertaFragment
import ipg.primeiro.projetofinalcovid.AlertaFragment.ListaAlertaFragment
import ipg.primeiro.projetofinalcovid.AlertaFragment.NovoAlertaFragment
import ipg.primeiro.projetofinalcovid.DistritoFragment.EditaDistritoFragment
import ipg.primeiro.projetofinalcovid.DistritoFragment.EliminaDistritoFragment
import ipg.primeiro.projetofinalcovid.DistritoFragment.ListaDistritoFragment
import ipg.primeiro.projetofinalcovid.DistritoFragment.NovoDistritoFragment
import ipg.primeiro.projetofinalcovid.Notificacao.ListaNotificacaoFragment
import ipg.primeiro.projetofinalcovid.TestesFragment.EditaTesteFragment
import ipg.primeiro.projetofinalcovid.TestesFragment.EliminaTesteFragment
import ipg.primeiro.projetofinalcovid.TestesFragment.ListaTestesFragment
import ipg.primeiro.projetofinalcovid.TestesFragment.NovoTesteFragment
import ipg.primeiro.projetofinalcovid.ui.PessoasFragment.ListaPessoasFragment
import ipg.primeiro.projetofinalcovid.ui.PessoasFragment.EditaPessoaFragment
import ipg.primeiro.projetofinalcovid.ui.PessoasFragment.EliminaPessoaFragment
import ipg.primeiro.projetofinalcovid.ui.PessoasFragment.NovaPessoaFragment

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var menu: Menu


    var menuAtual = R.menu.menu_lista_pessoas
        set(value) {
            field = value
            invalidateOptionsMenu()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.listaPessoaFragment,
                R.id.listaDistritoFragment,
                R.id.listaTestesFragment,
                R.id.listaAlertaFragment,
                R.id.listaNotificacaoFragment

        ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        DadosApp.activity = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.


        menuInflater.inflate(menuAtual, menu)

        this.menu = menu

        if(menuAtual == R.menu.menu_lista_pessoas){
            atualizaMenuListaPessoas(false)
        }
        if(menuAtual == R.menu.menu_lista_distrito){
            atualizaMenuListaDistrito(false)
        }
        if(menuAtual == R.menu.menu_lista_testes){
            atualizaMenuListaTeste(false)
        }
        if(menuAtual == R.menu.menu_lista_alerta){
            atualizaMenuListaAlerta(false)
        }
        if(menuAtual == R.menu.menu_lista_notificacao){
            atualizaMenuListaNotificacao(false)
        }

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        val opcaoProcessada = when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, R.string.versao, Toast.LENGTH_LONG).show()
                true
            }

            else -> when (menuAtual){
                //Menu Pessoas
                R.menu.menu_lista_pessoas -> (DadosApp.fragment as ListaPessoasFragment).processaOpcaoMenu(item)
                R.menu.menu_nova_pessoa -> (DadosApp.fragment as NovaPessoaFragment).processaOpcaoMenu(item)
                R.menu.menu_edita_pessoa -> (DadosApp.fragment as EditaPessoaFragment).processaOpcaoMenu(item)
                R.menu.menu_elimina_pessoa -> (DadosApp.fragment as EliminaPessoaFragment).processaOpcaoMenu(item)

                //Menu Distrito
                R.menu.menu_lista_distrito -> (DadosApp.fragment as ListaDistritoFragment).processaOpcaoMenu(item)
                R.menu.menu_novo_distrito -> (DadosApp.fragment as NovoDistritoFragment).processaOpcaoMenu(item)
                R.menu.menu_edita_distrito -> (DadosApp.fragment as EditaDistritoFragment).processaOpcaoMenu(item)
                R.menu.menu_elimina_distrito -> (DadosApp.fragment as EliminaDistritoFragment).processaOpcaoMenu(item)

                //Menu Testes
                R.menu.menu_lista_testes -> (DadosApp.fragment as ListaTestesFragment).processaOpcaoMenu(item)
                R.menu.menu_novo_teste -> (DadosApp.fragment as NovoTesteFragment).processaOpcaoMenu(item)
                R.menu.menu_edita_teste -> (DadosApp.fragment as EditaTesteFragment).processaOpcaoMenu(item)
                R.menu.menu_elimina_teste -> (DadosApp.fragment as EliminaTesteFragment).processaOpcaoMenu(item)

                //Alerta
                R.menu.menu_lista_alerta -> (DadosApp.fragment as ListaAlertaFragment).processaOpcaoMenu(item)
                R.menu.menu_novo_alerta -> (DadosApp.fragment as NovoAlertaFragment).processaOpcaoMenu(item)
                R.menu.menu_edita_alerta -> (DadosApp.fragment as EditaAlertaFragment).processaOpcaoMenu(item)
                R.menu.menu_elimina_alerta -> (DadosApp.fragment as EliminaAlertaFragment).processaOpcaoMenu(item)

                //Notificação
                R.menu.menu_lista_notificacao -> (DadosApp.fragment as ListaNotificacaoFragment).processaOpcaoMenu(item)
                R.menu.menu_nova_notificacao -> (DadosApp.fragment as NovaNotificacaoFragment).processaOpcaoMenu(item)
                R.menu.menu_edita_notificacao -> (DadosApp.fragment as EditaNotificacaoFragment).processaOpcaoMenu(item)


               else -> false

            }
        }
        return if(opcaoProcessada) true else super.onOptionsItemSelected(item)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun atualizaMenuListaPessoas(mostraBotoesAlterarEliminar : Boolean){

        menu.findItem(R.id.action_alterar_pessoa).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_pessoa).setVisible(mostraBotoesAlterarEliminar)


    }

    fun atualizaMenuListaDistrito(mostraBotoesAlterarEliminar: Boolean) {

       menu.findItem(R.id.action_alterar_distrito).setVisible(mostraBotoesAlterarEliminar)
       menu.findItem(R.id.action_eliminar_distrito).setVisible(mostraBotoesAlterarEliminar)
    }

    fun atualizaMenuListaTeste(mostraBotoesAlterarEliminar: Boolean) {

        menu.findItem(R.id.action_alterar_teste).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_teste).setVisible(mostraBotoesAlterarEliminar)
    }

    fun atualizaMenuListaAlerta(mostraBotoesAlterarEliminar: Boolean) {

        menu.findItem(R.id.action_alterar_alerta).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_alerta).setVisible(mostraBotoesAlterarEliminar)
    }

    fun atualizaMenuListaNotificacao(mostraBotoesAlterarEliminar: Boolean) {

        menu.findItem(R.id.action_alterar_notificacao).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_notificacao).setVisible(mostraBotoesAlterarEliminar)
    }


}