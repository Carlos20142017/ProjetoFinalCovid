package ipg.primeiro.projetofinalcovid.NotificacaoFragment

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
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
import ipg.primeiro.projetofinalcovid.basedados.TabelaTestes
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [EditaNotificacaoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditaNotificacaoFragment : Fragment(), LoaderManager.LoaderCallbacks <Cursor> {

    private lateinit var editTextResultado: EditText
    private lateinit var spinnerAlerta: Spinner
    private lateinit var spinnerPessoa: Spinner


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_notificacao
        return inflater.inflate(R.layout.fragment_edita_notificacao, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextResultado = view.findViewById(R.id.editTextResultado)

        spinnerAlerta = view.findViewById(R.id.spinnerAlerta)
        spinnerPessoa = view.findViewById(R.id.spinnerPessoa)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_ALERTAS,null, this)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_ESTADO_SAUDE,null, this)


        editTextResultado.setText(DadosApp.notificacaoSelecionado!!.resultado)

    }


    fun navegaListaNotificacao(){
        findNavController().navigate(R.id.action_editaNotificacaoFragment_to_listaNotificacaoFragment)
    }

    fun guardar(){

        val resultado = editTextResultado.text.toString()
        if(resultado.isEmpty()){
            editTextResultado.setError(getString(R.string.preencha_campo_resultado))
            editTextResultado.requestFocus()
            return
        }


        val idAlerta = spinnerAlerta.selectedItemId
        val idPessoa = spinnerPessoa.selectedItemId


        val notificacao = DadosApp.notificacaoSelecionado!!

        notificacao.resultado = resultado
        notificacao.id_estrang_alertas = idAlerta
        notificacao.id_estrang_testes = idPessoa


        val uriNotificacao = Uri.withAppendedPath(
            ContentProviderPessoas.ENDERECO_NOTIFICACAO,
            notificacao.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriNotificacao,
            notificacao.toContentValues(),
            null,
            null
        )
        if(registos != 1){
            Toast.makeText(
                requireContext(),
                getString(R.string.erro_alterar_notificacao),
                Toast.LENGTH_LONG
            ).show()

            return
        }

        Toast.makeText(
            requireContext(),
            getString(R.string.notificacao_alterada_sucesso),
            Toast.LENGTH_LONG
        ).show()

        navegaListaNotificacao()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.action_guardar_edita_notificacao -> guardar()
            R.id.action_cancelar_edita_notificacao -> navegaListaNotificacao()

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

        if(id == ID_LOADER_MANAGER_ALERTAS) {
            return CursorLoader(
                requireContext(),
                ContentProviderPessoas.ENDERECO_ALERTA,
                TabelaAlertas.TODAS_COLUNAS,
                null, null,
                TabelaAlertas.CAMPO_NOME_ALERTA

            )
        }
        else if(id== ID_LOADER_MANAGER_ESTADO_SAUDE){
            return CursorLoader(
                requireContext(),
                ContentProviderPessoas.ENDERECO_TESTE,
                TabelaTestes.TODAS_COLUNAS,
                null, null,
                TabelaTestes.CAMPO_EXTERNO_NOME_Pessoas

            )
        }

        return null!!
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

        if(loader.id == ID_LOADER_MANAGER_ALERTAS){
            atualizaSpinnerAlertas(data)
        }

        else if(loader.id == ID_LOADER_MANAGER_ESTADO_SAUDE){
            atualizaSpinnerTestes(data)
        }

        atualizaNotificacaoSelecionada()
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
        atualizaSpinnerAlertas(null)
        atualizaSpinnerTestes(null)
    }

    private fun atualizaSpinnerAlertas(data: Cursor?) {
        spinnerAlerta.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaAlertas.CAMPO_NOME_ALERTA),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    private fun atualizaSpinnerTestes(data: Cursor?) {
        spinnerPessoa.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaTestes.CAMPO_EXTERNO_NOME_Pessoas),
            intArrayOf(android.R.id.text1),
            0
        )
    }



    private fun atualizaNotificacaoSelecionada() {
        val idAlerta = DadosApp.notificacaoSelecionado!!.id_estrang_alertas
        val idPessoa = DadosApp.notificacaoSelecionado!!.id_estrang_testes

        val ultimoAlerta = spinnerAlerta.count - 1
        val ultimaPessoa = spinnerPessoa.count - 1
        for (i in 0..ultimoAlerta){
            if(idAlerta == spinnerAlerta.getItemIdAtPosition(i)){
                spinnerAlerta.setSelection(i)
                return
            }
        }

        for (i in 0..ultimaPessoa){
            if(idPessoa == spinnerPessoa.getItemIdAtPosition(i)){
                spinnerPessoa.setSelection(i)
                return
            }
        }
    }





    companion object{
        const val ID_LOADER_MANAGER_ALERTAS = 1

        const val ID_LOADER_MANAGER_ESTADO_SAUDE = 2
    }


}