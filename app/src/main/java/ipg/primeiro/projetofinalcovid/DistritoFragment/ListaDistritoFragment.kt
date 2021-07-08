package ipg.primeiro.projetofinalcovid.DistritoFragment

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipg.primeiro.projetofinalcovid.ContentProviderPessoas
import ipg.primeiro.projetofinalcovid.DadosApp
import ipg.primeiro.projetofinalcovid.MainActivity
import ipg.primeiro.projetofinalcovid.R
import ipg.primeiro.projetofinalcovid.basedados.TabelaDistritos


/**
 * A simple [Fragment] subclass.
 * Use the [ListaDistritoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaDistritoFragment : Fragment(), LoaderManager.LoaderCallbacks <Cursor> {

    private lateinit var distritoViewModel: ListaDistritoViewModel
    private var adapterDistrito : AdapterDistrito? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
           distritoViewModel =
                ViewModelProvider(this).get(ListaDistritoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_lista_distrito, container, false)

        distritoViewModel.text.observe(viewLifecycleOwner, Observer {

        })

        DadosApp.fragment = this
       (activity as MainActivity).menuAtual = R.menu.menu_lista_distrito

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewDistrito = view.findViewById<RecyclerView>(R.id.recyclerViewDistrito)
        adapterDistrito = AdapterDistrito(this)
        recyclerViewDistrito.adapter = adapterDistrito
        recyclerViewDistrito.layoutManager = LinearLayoutManager(requireContext())

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_DISTRITOS,null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderPessoas.ENDERECO_DISTRITO,
            TabelaDistritos.TODAS_COLUNAS,
            null, null,
            TabelaDistritos.CAMPO_NOME_DISTRITO

        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {

        adapterDistrito!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterDistrito!!.cursor = null
    }


    fun navegaNovoDistrito(){
        findNavController().navigate(R.id.action_listaDistritoFragment_to_novoDistritoFragment)
    }
    fun navegaAlterarDistrito(){
     //   findNavController().navigate(R.id.action_listaPessoaFragment_to_editPessoaFragment)
    }

    fun navegaEliminarDistrito(){
      //  findNavController().navigate(R.id.action_listaPessoaFragment_to_eliminaPessoaFragment)
    }





    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_novo_distrito -> navegaNovoDistrito()
            R.id.action_alterar_distrito -> navegaAlterarDistrito()
            R.id.action_eliminar_distrito -> navegaEliminarDistrito()
            else -> return false
        }
        return true
    }

    companion object{
        const val ID_LOADER_MANAGER_DISTRITOS = 0
    }


}