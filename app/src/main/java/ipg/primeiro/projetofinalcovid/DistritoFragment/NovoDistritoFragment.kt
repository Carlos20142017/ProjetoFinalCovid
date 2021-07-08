package ipg.primeiro.projetofinalcovid.DistritoFragment

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ipg.primeiro.projetofinalcovid.ContentProviderPessoas
import ipg.primeiro.projetofinalcovid.DadosApp
import ipg.primeiro.projetofinalcovid.MainActivity
import ipg.primeiro.projetofinalcovid.R
import ipg.primeiro.projetofinalcovid.basedados.TabelaDistritos
import ipg.primeiro.projetofinalcovid.classedastabelas.Distrito


/**
 * A simple [Fragment] subclass.
 * Use the [NovoDistritoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NovoDistritoFragment : Fragment(), LoaderManager.LoaderCallbacks <Cursor> {

    private lateinit var editTextNomeDistrito: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_distrito

        return inflater.inflate(R.layout.fragment_novo_distrito, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomeDistrito = view.findViewById(R.id.editTextNomeDistrito)

    }



    fun navegaListaDistritos(){
        findNavController().navigate(R.id.action_novoDistritoFragment_to_listaDistritoFragment)
    }

    fun guardar(){

        val nomeDistrito = editTextNomeDistrito.text.toString()
        if(nomeDistrito.isEmpty()){
            editTextNomeDistrito.setError(getString(R.string.preencha_nome))
            editTextNomeDistrito.requestFocus()
            return
        }




        val distrito = Distrito(nome_distrito = nomeDistrito)
        val uri = activity?.contentResolver?.insert(
            ContentProviderPessoas.ENDERECO_DISTRITO, distrito.toContentValues()
        )
        if(uri == null){
            Snackbar.make(editTextNomeDistrito,
                R.string.erro_inserir_distrito,
                Snackbar.LENGTH_LONG).show()
            return
        }

        navegaListaDistritos()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.action_guardar_novo_distrito -> guardar()
            R.id.action_cancelar_novo_distrito -> navegaListaDistritos()

            else -> return false
        }
        return true
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
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }



}