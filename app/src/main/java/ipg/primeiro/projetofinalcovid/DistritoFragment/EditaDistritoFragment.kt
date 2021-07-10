package ipg.primeiro.projetofinalcovid.DistritoFragment

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import ipg.primeiro.projetofinalcovid.ContentProviderPessoas
import ipg.primeiro.projetofinalcovid.DadosApp
import ipg.primeiro.projetofinalcovid.MainActivity
import ipg.primeiro.projetofinalcovid.R
import ipg.primeiro.projetofinalcovid.basedados.TabelaDistritos
import ipg.primeiro.projetofinalcovid.ui.PessoasFragment.EditaPessoaFragment
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [EditaDistritoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditaDistritoFragment : Fragment(), LoaderManager.LoaderCallbacks <Cursor>  {

    private lateinit var editTextNomeDistrito: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_distrito

        return inflater.inflate(R.layout.fragment_edita_distrito, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomeDistrito = view.findViewById(R.id.editTextNomeDistritoAltera)

        editTextNomeDistrito.setText(DadosApp.distritoSelecionado!!.nome_distrito)
    }







    fun navegaListaDistritos(){
        findNavController().navigate(R.id.action_editaDistritoFragment_to_listaDistritoFragment)
    }

    fun guardar(){

        val nome = editTextNomeDistrito.text.toString()
        if(nome.isEmpty()){
            editTextNomeDistrito.setError(getString(R.string.preencha_nome))
            editTextNomeDistrito.requestFocus()
            return
        }


        val distrito = DadosApp.distritoSelecionado!!

        distrito.nome_distrito = nome

        val uriDistrito = Uri.withAppendedPath(
            ContentProviderPessoas.ENDERECO_DISTRITO,
            distrito.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriDistrito,
            distrito.toContentValues(),
            null,
            null
        )
        if(registos != 1){
            Toast.makeText(
                requireContext(),
                getString(R.string.erro_alterar_distrito),
                Toast.LENGTH_LONG
            ).show()

            return
        }

        Toast.makeText(
            requireContext(),
            getString(R.string.distrito_alterado_sucesso),
            Toast.LENGTH_LONG
        ).show()

        navegaListaDistritos()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.action_guardar_edita_distrito -> guardar()
            R.id.action_cancelar_edita_distrito -> navegaListaDistritos()

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
       // TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
       // TODO("Not yet implemented")
    }




}