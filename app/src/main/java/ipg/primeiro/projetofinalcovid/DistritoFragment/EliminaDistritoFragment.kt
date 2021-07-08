package ipg.primeiro.projetofinalcovid.DistritoFragment

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
 * Use the [EliminaDistritoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EliminaDistritoFragment : Fragment(){

    private  lateinit var textViewNomeDistrito : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_distrito


        return inflater.inflate(R.layout.fragment_elimina_distrito, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        textViewNomeDistrito = view.findViewById(R.id.textViewNomeDistrito)


        val distrito = DadosApp.distritoSelecionado!!

        textViewNomeDistrito.setText(distrito.nome_distrito)

    }

    fun navegaListaDistrito(){
        findNavController().navigate(R.id.action_eliminaDistritoFragment_to_listaDistritoFragment)
    }

    fun elimina (){

        val uriDistrito = Uri.withAppendedPath(
            ContentProviderPessoas.ENDERECO_DISTRITO,
            DadosApp.distritoSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriDistrito,
            null,
            null
        )
        if(registos != 1){
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_distrito,
                Toast.LENGTH_LONG
            ).show()

            return
        }

        Toast.makeText(
            requireContext(),
            R.string.distrito_eliminado_sucesso,
            Toast.LENGTH_LONG
        ).show()

        navegaListaDistrito()

    }

    fun processaOpcaoMenu(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.action_confirma_eliminar_distrito -> elimina()
            R.id.action_cancelar_eliminar_distrito -> navegaListaDistrito()

            else -> return false
        }
        return true
    }





}