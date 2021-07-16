package ipg.primeiro.projetofinalcovid

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ipg.primeiro.projetofinalcovid.basedados.TabelaAlertas
import ipg.primeiro.projetofinalcovid.basedados.TabelaDistritos
import ipg.primeiro.projetofinalcovid.basedados.TabelaNotificacao
import ipg.primeiro.projetofinalcovid.basedados.TabelaTestes
import ipg.primeiro.projetofinalcovid.classedastabelas.Notificacao
import ipg.primeiro.projetofinalcovid.ui.PessoasFragment.NovaPessoaFragment
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [NovaNotificacaoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NovaNotificacaoFragment : Fragment(), LoaderManager.LoaderCallbacks <Cursor>  {

    private lateinit var editTextResutado: EditText
    private lateinit var spinnerAlerta: Spinner
    private lateinit var spinnerEstadoSaude: Spinner


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_nova_notificacao

        return inflater.inflate(R.layout.fragment_nova_notificacao, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextResutado = view.findViewById(R.id.editTextResultado)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_ALERTAS,null, this)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_ESTADO_SAUDE,null, this)


        spinnerAlerta = view.findViewById(R.id.spinnerAlerta)
        spinnerEstadoSaude = view.findViewById(R.id.spinnerEstadoSaude)



    }


    fun navegaListaNotificacao(){
        findNavController().navigate(R.id.action_novaNotificacaoFragment_to_listaNotificacaoFragment)
    }

    fun guardar(){

        val resultado = editTextResutado.text.toString()
        if(resultado.isEmpty()){
            editTextResutado.setError("Preencha o campo resultado")
            editTextResutado.requestFocus()
            return
        }



        val idAlerta = spinnerAlerta.selectedItemId

        val idEstadoSaude = spinnerEstadoSaude.selectedItemId

        val notificacao = Notificacao(resultado = resultado,id_estrang_alertas = idAlerta, id_estrang_testes = idEstadoSaude)
        val uri = activity?.contentResolver?.insert(
            ContentProviderPessoas.ENDERECO_NOTIFICACAO, notificacao.toContentValues()
        )
        if(uri == null){
            Snackbar.make(editTextResutado,
                getString(R.string.erro_inserir_notificacao),
                Snackbar.LENGTH_LONG).show()
            return
        }

        navegaListaNotificacao()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.action_guardar_nova_notificacao -> guardar()
            R.id.action_cancelar_nova_notificacao -> navegaListaNotificacao()

            else -> return false
        }
        return true
    }

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
        spinnerEstadoSaude.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaTestes.CAMPO_EXTERNO_NOME_Pessoas),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    companion object{
        const val ID_LOADER_MANAGER_ALERTAS = 1

        const val ID_LOADER_MANAGER_ESTADO_SAUDE = 2
    }


}