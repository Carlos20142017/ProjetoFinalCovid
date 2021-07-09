package ipg.primeiro.projetofinalcovid.TestesFragment

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
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [EliminaTesteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EliminaTesteFragment : Fragment() {

    private  lateinit var textViewTemperatura : TextView
    private  lateinit var textViewSintomas : TextView
    private  lateinit var textViewDataTeste : TextView
    private  lateinit var textViewEstadoSaude : TextView
    private  lateinit var textViewNomePessoa : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_teste
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_teste, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        textViewTemperatura = view.findViewById(R.id.textViewTemperatura)
        textViewSintomas = view.findViewById(R.id.textViewSintomas)
        textViewDataTeste = view.findViewById(R.id.textViewDataTeste)
        textViewEstadoSaude = view.findViewById(R.id.textViewEstadoSaude)
        textViewNomePessoa = view.findViewById(R.id.textViewNomePessoa)


        val teste = DadosApp.testeSelecionado!!

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dataTeste = sdf.format(teste.data_teste)

        textViewTemperatura.setText(teste.temperatura.toString())
        textViewSintomas.setText(teste.sintomas)
        textViewDataTeste.setText(dataTeste.toString())
        textViewEstadoSaude.setText(teste.estado_saude)
        textViewNomePessoa.setText(teste.nomePessoa)
    }

    fun navegaListaTestes(){
        findNavController().navigate(R.id.action_eliminaTesteFragment_to_listaTestesFragment)
    }

    fun elimina (){

        val uriTeste = Uri.withAppendedPath(
            ContentProviderPessoas.ENDERECO_TESTE,
            DadosApp.testeSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriTeste,
            null,
            null
        )
        if(registos != 1){
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_teste,
                Toast.LENGTH_LONG
            ).show()

            return
        }

        Toast.makeText(
            requireContext(),
            R.string.teste_eliminado_sucesso,
            Toast.LENGTH_LONG
        ).show()

        navegaListaTestes()

    }

    fun processaOpcaoMenu(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.action_confirma_eliminar_teste -> elimina()
            R.id.action_cancelar_eliminar_teste -> navegaListaTestes()

            else -> return false
        }
        return true
    }



}