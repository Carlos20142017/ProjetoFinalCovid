package ipg.primeiro.projetofinalcovid

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass.
 * Use the [EliminaPessoaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EliminaPessoaFragment : Fragment() {

    private  lateinit var textViewNome : TextView
    private  lateinit var textViewSexo : TextView
    private  lateinit var textViewDataNascimento : TextView
    private  lateinit var textViewTelemovel : TextView
    private  lateinit var textViewDistrito : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_pessoa

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_pessoa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNome = view.findViewById(R.id.textViewNome)
        textViewSexo = view.findViewById(R.id.textViewSexo)
        textViewDataNascimento = view.findViewById(R.id.textViewDataNascimento)
        textViewTelemovel = view.findViewById(R.id.textViewTelemovel)
        textViewDistrito = view.findViewById(R.id.textViewDistrito)


        val pessoa = DadosApp.pessoaSelecionada!!
        textViewNome.setText(pessoa.nome)
        textViewSexo.setText(pessoa.sexo)
        textViewDataNascimento.setText(pessoa.data_nascimento.toString())
        textViewTelemovel.setText(pessoa.telemovel)
        textViewDistrito.setText(pessoa.nomeDistrito)
    }

    fun navegaListaLivros(){
        findNavController().navigate(R.id.action_eliminaPessoaFragment_to_listaPessoaFragment)
    }

    fun elimina (){

        val uriPessoa = Uri.withAppendedPath(
            ContentProviderPessoas.ENDERECO_PESSOAS,
            DadosApp.pessoaSelecionada!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriPessoa,
            null,
            null
        )
        if(registos != 1){
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_pessoa,
                Toast.LENGTH_LONG
            ).show()

            return
        }

        Toast.makeText(
            requireContext(),
            R.string.pessoa_eliminada_com_sucesso,
            Toast.LENGTH_LONG
        ).show()

        navegaListaLivros()

    }

    fun processaOpcaoMenu(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.action_confirma_eliminar_pessoa -> elimina()
            R.id.action_cancelar_eliminar_pessoa -> navegaListaLivros()

            else -> return false
        }
        return true
    }

}