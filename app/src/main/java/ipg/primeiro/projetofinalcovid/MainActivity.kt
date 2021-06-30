package ipg.primeiro.projetofinalcovid

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ipg.primeiro.projetofinalcovid.ui.ListaPessoaFragment.ListaPessoasFragment
import ipg.primeiro.projetofinalcovid.ui.NovaPessoaFragment.NovaPessoaFragment

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

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.listaPessoaFragment, R.id.novaPessoaFragment, R.id.nav_slideshow), drawerLayout)
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

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val opcaoProcessada = when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, R.string.versao, Toast.LENGTH_LONG).show()
                true
            }

            else -> when (menuAtual){
                R.menu.menu_lista_pessoas -> (DadosApp.fragment as ListaPessoasFragment).processaOpcaoMenu(item)
                R.menu.menu_nova_pessoa -> (DadosApp.fragment as NovaPessoaFragment).processaOpcaoMenu(item)
                R.menu.menu_edita_pessoa -> (DadosApp.fragment as EditaPessoaFragment).processaOpcaoMenu(item)
                R.menu.menu_elimina_pessoa -> (DadosApp.fragment as EliminaPessoaFragment).processaOpcaoMenu(item)
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
}