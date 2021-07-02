package ipg.primeiro.projetofinalcovid

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass.
 * Use the [ListaDistritoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaDistritoFragment : Fragment(), LoaderManager.LoaderCallbacks <Cursor> {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_distrito, container, false)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        TODO("Not yet implemented")
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }


    fun navegaNovoDistrito(){
        findNavController().navigate(R.id.action_ListaPessoaFragment_to_NovaPessoaFragment)
    }
    fun navegaAlterarDistrito(){
        findNavController().navigate(R.id.action_listaPessoaFragment_to_editPessoaFragment)
    }

    fun navegaEliminarDistrito(){
        findNavController().navigate(R.id.action_listaPessoaFragment_to_eliminaPessoaFragment)
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_nova_pessoa -> navegaNovoDistrito()
            R.id.action_alterar_pessoa -> navegaAlterarDistrito()
            R.id.action_eliminar_pessoa -> navegaEliminarDistrito()
            else -> return false
        }
        return true
    }



}