package ipg.primeiro.projetofinalcovid.AlertaFragment

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
import ipg.primeiro.projetofinalcovid.basedados.TabelaAlertas


/**
 * A simple [Fragment] subclass.
 * Use the [EditaAlertaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditaAlertaFragment : Fragment(), LoaderManager.LoaderCallbacks <Cursor> {


    private lateinit var editTextNomeAlerta: EditText
    private lateinit var editTextDescricao: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_alerta
        return inflater.inflate(R.layout.fragment_edita_alerta, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomeAlerta = view.findViewById(R.id.editTextNomeAlerta)
        editTextDescricao = view.findViewById(R.id.editTextDescricao)

        editTextNomeAlerta.setText(DadosApp.alertaSelecionado!!.nome_alerta)
        editTextDescricao.setText(DadosApp.alertaSelecionado!!.descricao)
    }

    fun navegaListaAlerta(){
        findNavController().navigate(R.id.action_editaAlertaFragment_to_listaAlertaFragment)
    }

    fun guardar(){

        val nomeAlerta = editTextNomeAlerta.text.toString()
        if(nomeAlerta.isEmpty()|| nomeAlerta.length<5){
            editTextNomeAlerta.setError(getString(R.string.preencha_campo_alerta))
            editTextNomeAlerta.requestFocus()
            return
        }

        val descricao = editTextDescricao.text.toString()
        if(descricao.isEmpty()|| descricao.length<5){
            editTextDescricao.setError(getString(R.string.preencha_campo_descricao))
            editTextDescricao.requestFocus()
            return
        }


        val alerta = DadosApp.alertaSelecionado!!

        alerta.nome_alerta = nomeAlerta
        alerta.descricao = descricao

        val uriAlerta = Uri.withAppendedPath(
            ContentProviderPessoas.ENDERECO_ALERTA,
            alerta.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriAlerta,
            alerta.toContentValues(),
            null,
            null
        )
        if(registos != 1){
            Toast.makeText(
                requireContext(),
                R.string.erro_alterar_alerta,
                Toast.LENGTH_LONG
            ).show()

            return
        }

        Toast.makeText(
            requireContext(),
            R.string.alerta_alterado_sucesso,
            Toast.LENGTH_LONG
        ).show()

        navegaListaAlerta()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.action_guardar_edita_alerta -> guardar()
            R.id.action_cancelar_edita_alerta -> navegaListaAlerta()

            else -> return false
        }
        return true
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
        // TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        // TODO("Not yet implemented")
    }


}