package ipg.primeiro.projetofinalcovid.NotificacaoFragment

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
import ipg.primeiro.projetofinalcovid.ContentProviderPessoas
import ipg.primeiro.projetofinalcovid.DadosApp
import ipg.primeiro.projetofinalcovid.MainActivity
import ipg.primeiro.projetofinalcovid.R
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [EliminaNotificacaoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EliminaNotificacaoFragment : Fragment() {

    private  lateinit var textViewNome : TextView
    private  lateinit var textViewAlerta : TextView
    private  lateinit var textViewResultado : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_notificacao
        return inflater.inflate(R.layout.fragment_elimina_notificacao, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        textViewNome = view.findViewById(R.id.textViewNome)
        textViewAlerta = view.findViewById(R.id.textViewAlerta)
        textViewResultado = view.findViewById(R.id.textViewResultado)


        val notificacao = DadosApp.notificacaoSelecionado!!


        textViewNome.setText(notificacao.nomeExternoPessoa)
        textViewAlerta.setText(notificacao.nomeAlerta)
        textViewResultado.setText(notificacao.resultado)
    }

    fun navegaListaNotificacao(){
        findNavController().navigate(R.id.action_eliminaNotificacaoFragment_to_listaNotificacaoFragment)
    }

    fun elimina (){

        val uriNotificacao = Uri.withAppendedPath(
            ContentProviderPessoas.ENDERECO_NOTIFICACAO,
            DadosApp.notificacaoSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriNotificacao,
            null,
            null
        )
        if(registos != 1){
            Toast.makeText(
                requireContext(),
                getString(R.string.erro_eliminar_notificacao),
                Toast.LENGTH_LONG
            ).show()

            return
        }

        Toast.makeText(
            requireContext(),
            getString(R.string.notificacao_eliminada_sucesso),
            Toast.LENGTH_LONG
        ).show()

        navegaListaNotificacao()

    }

    fun processaOpcaoMenu(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.action_confirma_eliminar_notificacao -> elimina()
            R.id.action_cancelar_eliminar_notificacao -> navegaListaNotificacao()

            else -> return false
        }
        return true
    }


}