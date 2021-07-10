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
 * Use the [EliminaAlertaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EliminaAlertaFragment : Fragment() {

    private  lateinit var textViewNomeAlerta : TextView
    private  lateinit var textViewDescricao : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_alerta
        return inflater.inflate(R.layout.fragment_elimina_alerta, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNomeAlerta = view.findViewById(R.id.textViewNomeAlerta)
        textViewDescricao = view.findViewById(R.id.textViewDescricao)

        val alerta = DadosApp.alertaSelecionado!!

        textViewNomeAlerta.setText(alerta.nome_alerta)
        textViewDescricao.setText(alerta.descricao)

    }

    fun navegaListaAlerta(){
        findNavController().navigate(R.id.action_eliminaAlertaFragment_to_listaAlertaFragment)
    }

    fun elimina (){

        val uriAlerta = Uri.withAppendedPath(
            ContentProviderPessoas.ENDERECO_ALERTA,
            DadosApp.alertaSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriAlerta,
            null,
            null
        )
        if(registos != 1){
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_alerta,
                Toast.LENGTH_LONG
            ).show()

            return
        }

        Toast.makeText(
            requireContext(),
            R.string.alerta_eliminado_sucesso,
            Toast.LENGTH_LONG
        ).show()

        navegaListaAlerta()

    }

    fun processaOpcaoMenu(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.action_confirma_eliminar_alerta -> elimina()
            R.id.action_cancelar_eliminar_alerta -> navegaListaAlerta()

            else -> return false
        }
        return true
    }




}