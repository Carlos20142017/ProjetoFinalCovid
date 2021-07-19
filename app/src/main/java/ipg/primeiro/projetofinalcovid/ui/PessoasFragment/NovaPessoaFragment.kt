package ipg.primeiro.projetofinalcovid.ui.PessoasFragment

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
import ipg.primeiro.projetofinalcovid.classedastabelas.Pessoa
import java.text.SimpleDateFormat
import java.util.*

class NovaPessoaFragment : Fragment(), LoaderManager.LoaderCallbacks <Cursor> {

    private lateinit var galleryViewModel: NovaPessoaViewModel

    private lateinit var editTextNome: EditText
    private lateinit var editTextSexo: EditText
    private lateinit var editTextDataNascimento: EditText
    private lateinit var editTextTelemovel: EditText
    private lateinit var spinnerDistrito: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(NovaPessoaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_nova_pessoa, container, false)
        // val textView: TextView = root.findViewById(R.id.textView)
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            //  textView.text = it
        })
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_nova_pessoa
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextNome)
        editTextSexo = view.findViewById(R.id.editTextSexo)
        editTextDataNascimento = view.findViewById(R.id.editTextDataNascimento)
        editTextTelemovel = view.findViewById(R.id.editTextTelemovel)
        spinnerDistrito = view.findViewById(R.id.spinnerDistrito)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_DISTRITOS,null, this)


    }

    fun navegaListaPessoas(){
        findNavController().navigate(R.id.action_NovaPessoaFragment_to_ListaPessoaFragment)
    }

    fun guardar(){

        val nome = editTextNome.text.toString()
        if(nome.isEmpty() ||nome.length<3){
            editTextNome.setError(getString(R.string.preencha_nome))
            editTextNome.requestFocus()
            return
        }

        val sexo = editTextSexo.text.toString()
        if(sexo.isEmpty()){
            editTextSexo.setError(getString(R.string.preencha_sexo))
            editTextSexo.requestFocus()
            return
        }

        val dataNascimento = editTextDataNascimento.text.toString()
        if(dataNascimento == null || !dataNascimento.equals(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()))){
            editTextDataNascimento.setError(getString(R.string.preencha_data_nascimento))
            editTextDataNascimento.requestFocus()
            return
        }

        val telemovel = editTextTelemovel.text.toString()
        if(telemovel.isEmpty() ||telemovel.toInt()<910000000){
            editTextTelemovel.setError(getString(R.string.preencha_contacto_telemovel))
            editTextTelemovel.requestFocus()
            return
        }

        val idDistrito = spinnerDistrito.selectedItemId

        val pessoa = Pessoa(nome = nome, sexo = sexo, data_nascimento = Date(dataNascimento), telemovel = telemovel, id_estrang_distrito = idDistrito)
        val uri = activity?.contentResolver?.insert(
            ContentProviderPessoas.ENDERECO_PESSOAS, pessoa.toContentValues()
        )
        if(uri == null){
            Snackbar.make(editTextNome,
                getString(R.string.erro_ao_inserir_pessoa),
                Snackbar.LENGTH_LONG).show()
            return
        }

        navegaListaPessoas()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.action_guardar_nova_pessoa -> guardar()
            R.id.action_cancelar_nova_pessoa -> navegaListaPessoas()

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
            ContentProviderPessoas.ENDERECO_DISTRITO,
            TabelaDistritos.TODAS_COLUNAS,
            null, null,
            TabelaDistritos.CAMPO_NOME_DISTRITO

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
        spinnerDistrito.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaDistritos.CAMPO_NOME_DISTRITO),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    companion object{
        const val ID_LOADER_MANAGER_DISTRITOS = 0
    }

}
