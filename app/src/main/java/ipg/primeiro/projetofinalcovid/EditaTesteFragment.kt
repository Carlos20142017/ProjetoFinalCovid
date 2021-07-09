package ipg.primeiro.projetofinalcovid

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
import ipg.primeiro.projetofinalcovid.TestesFragment.NovoTesteFragment
import ipg.primeiro.projetofinalcovid.basedados.TabelaPessoas
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [EditaTesteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditaTesteFragment : Fragment(), LoaderManager.LoaderCallbacks <Cursor> {

    private lateinit var editTextTemperatura: EditText
    private lateinit var editTextSintomas: EditText
    private lateinit var editTextEstadoSaude: EditText
    private lateinit var editTextDataTeste: EditText
    private lateinit var spinnerNomePessoa: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_teste
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edita_teste, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextTemperatura = view.findViewById(R.id.editTextTemperatura)
        editTextSintomas = view.findViewById(R.id.editTextSintomas)
        editTextEstadoSaude = view.findViewById(R.id.editTextEstadoSaude)
        editTextDataTeste = view.findViewById(R.id.editTextDataTeste)
        spinnerNomePessoa = view.findViewById(R.id.spinnerNomePessoa)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_Pessoas,null, this)

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dataTeste = sdf.format(DadosApp.testeSelecionado!!.data_teste)

        editTextTemperatura.setText(DadosApp.testeSelecionado!!.temperatura.toString())
        editTextSintomas.setText(DadosApp.testeSelecionado!!.sintomas)
        editTextEstadoSaude.setText(DadosApp.testeSelecionado!!.estado_saude)
        editTextDataTeste.setText(dataTeste.toString())


    }

    fun navegaListaTestes(){
        findNavController().navigate(R.id.action_editaTesteFragment_to_listaTestesFragment)
    }

    fun guardar(){

        val temperatura = editTextTemperatura.text.toString()
        if(temperatura.isEmpty()){
            editTextTemperatura.setError(getString(R.string.preencha_temperatura))
            editTextTemperatura.requestFocus()
            return
        }

        val sintomas= editTextSintomas.text.toString()
        if(sintomas.isEmpty()){
            editTextSintomas.setError(getString(R.string.preencha_sintoma))
            editTextSintomas.requestFocus()
            return
        }

        val dataTeste = editTextDataTeste.text.toString()
        if(dataTeste == null){
            editTextDataTeste.setError(getString(R.string.preencha_data_teste))
            editTextDataTeste.requestFocus()
            return
        }

        val estadoSaude = editTextEstadoSaude.text.toString()
        if(estadoSaude.isEmpty()){
            editTextEstadoSaude.setError(getString(R.string.preencha_estado_saude))
            editTextEstadoSaude.requestFocus()
            return
        }

        val idPessoa = spinnerNomePessoa.selectedItemId

        val teste = DadosApp.testeSelecionado!!

        teste.temperatura = temperatura.toString().toFloat()
        teste.sintomas = sintomas
        teste.estado_saude = estadoSaude
        teste.data_teste = Date(dataTeste)
        teste.id_estrang_pessoas = idPessoa


        val uriTeste = Uri.withAppendedPath(
            ContentProviderPessoas.ENDERECO_TESTE,
            teste.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriTeste,
            teste.toContentValues(),
            null,
            null
        )
        if(registos != 1){
            Toast.makeText(
                requireContext(),
                R.string.erro_alterar_teste,
                Toast.LENGTH_LONG
            ).show()

            return
        }

        Toast.makeText(
            requireContext(),
           getString(R.string.teste_alterado_sucesso),
            Toast.LENGTH_LONG
        ).show()


        navegaListaTestes()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.action_guardar_edita_teste -> guardar()
            R.id.action_cancelar_edita_teste -> navegaListaTestes()

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

        atualizaSpinnerDistrito(data)
        atualizaPessoaSelecionada()
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
        atualizaSpinnerDistrito(null)
    }

    private fun atualizaSpinnerDistrito(data: Cursor?) {
        spinnerNomePessoa.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaPessoas.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    private fun atualizaPessoaSelecionada() {
        val idPessoa = DadosApp.testeSelecionado!!.id_estrang_pessoas

        val ultimaPessoa = spinnerNomePessoa.count - 1
        for (i in 0..ultimaPessoa){
            if(idPessoa == spinnerNomePessoa.getItemIdAtPosition(i)){
                spinnerNomePessoa.setSelection(i)
                return
            }
        }
    }
    companion object{
        const val ID_LOADER_MANAGER_Pessoas = 0
    }


}