package ipg.primeiro.projetofinalcovid

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipg.primeiro.projetofinalcovid.basedados.TabelaAlertas


/**
 * A simple [Fragment] subclass.
 * Use the [ListaAlertaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaAlertaFragment : Fragment(), LoaderManager.LoaderCallbacks <Cursor> {

    private var adapterAlerta : AdapterAlertas? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_lista_alerta

        return inflater.inflate(R.layout.fragment_lista_alerta, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewAlerta = view.findViewById<RecyclerView>(R.id.recyclerViewAlertas)
        adapterAlerta = AdapterAlertas(this)
        recyclerViewAlerta.adapter = adapterAlerta
        recyclerViewAlerta.layoutManager = LinearLayoutManager(requireContext())

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_ALERTAS,null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderPessoas.ENDERECO_ALERTA,
            TabelaAlertas.TODAS_COLUNAS,
            null, null,
            TabelaAlertas.CAMPO_NOME_ALERTA

        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {

        adapterAlerta!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterAlerta!!.cursor = null
    }


    fun navegaNovoAlerta(){
       // findNavController().navigate(R.id.action_listaDistritoFragment_to_novoDistritoFragment)
    }
    fun navegaAlterarAlerta(){
      //  findNavController().navigate(R.id.action_listaDistritoFragment_to_editaDistritoFragment)
    }

    fun navegaEliminarAlerta(){
      //  findNavController().navigate(R.id.action_listaDistritoFragment_to_eliminaDistritoFragment)
    }



    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_novo_alerta -> navegaNovoAlerta()
            R.id.action_alterar_alerta -> navegaAlterarAlerta()
            R.id.action_eliminar_alerta -> navegaEliminarAlerta()
            else -> return false
        }
        return true
    }

    companion object{
        const val ID_LOADER_MANAGER_ALERTAS = 0
    }




}