package ipg.primeiro.projetofinalcovid

import android.database.Cursor
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipg.primeiro.projetofinalcovid.basedados.TabelaPessoas
import ipg.primeiro.projetofinalcovid.classedastabelas.Pessoa
import ipg.primeiro.projetofinalcovid.ui.PessoasFragment.AdapterPessoas


/**
 * A simple [Fragment] subclass.
 * Use the [PesquisarPessoasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PesquisarPessoasFragment : Fragment(), LoaderManager.LoaderCallbacks <Cursor>,
    SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private var adapterPesquisaPessoas : AdapterPesquisar? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_pesquisar
        return inflater.inflate(R.layout.fragment_pesquisar_pessoas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewPessoas = view.findViewById<RecyclerView>(R.id.recyclerViewPesquisar)
        adapterPesquisaPessoas = AdapterPesquisar(this)
        recyclerViewPessoas.adapter = adapterPesquisaPessoas
        recyclerViewPessoas.layoutManager = LinearLayoutManager(requireContext())

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_PESSOAS,null, this)

    }




    fun processaOpcaoMenu(item: MenuItem): Boolean{
        when(item.itemId){

            else -> return false
        }
        return true
    }


    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderPessoas.ENDERECO_PESSOAS,
            TabelaPessoas.TODAS_COLUNAS,
            null, null,
            TabelaPessoas.CAMPO_NOME

        )
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterPesquisaPessoas!!.cursor = data
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterPesquisaPessoas!!.cursor = null
    }

    companion object{
        const val ID_LOADER_MANAGER_PESSOAS = 0
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_pesquisar, menu)

     var searchItem: MenuItem = menu.findItem(R.id.action_pesquisar_pessoas)
      val searchView: SearchView = searchItem.getActionView() as SearchView
        searchView.setOnQueryTextListener(this)



        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
       return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {

     if(newText == null){
         return false
     }


        var itemPessoaListFilter = ArrayList<Pessoa>()


        for( item in  itemPessoaListFilter){

        }


      return false
    }

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
       return true
    }

}