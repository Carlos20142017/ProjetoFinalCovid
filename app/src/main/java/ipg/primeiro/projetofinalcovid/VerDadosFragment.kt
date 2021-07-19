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
import androidx.recyclerview.widget.RecyclerView
import ipg.primeiro.projetofinalcovid.classedastabelas.Teste
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [VerDadosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VerDadosFragment : Fragment() {


    private  lateinit var textViewNome : TextView
    private  lateinit var textViewSexo : TextView
    private  lateinit var textViewDataNascimento : TextView
    private  lateinit var textViewTelemovel : TextView
    private  lateinit var textViewDistrito : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DadosApp.fragment = this
        return inflater.inflate(R.layout.fragment_ver_dados, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        textViewNome = view.findViewById(R.id.textViewNome)
        textViewSexo = view.findViewById(R.id.textViewSexo)
        textViewDataNascimento = view.findViewById(R.id.textViewDataNascimento)
        textViewTelemovel = view.findViewById(R.id.textViewTelemovel)
        textViewDistrito = view.findViewById(R.id.textViewDistrito)


        val pessoa = DadosApp.pesquisaSelecionada!!

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dataNascimento = sdf.format(pessoa.data_nascimento)

        textViewNome.setText(pessoa.nome)
        textViewSexo.setText(pessoa.sexo)
        textViewDataNascimento.setText(dataNascimento)
        textViewTelemovel.setText(pessoa.telemovel)
        textViewDistrito.setText(pessoa.nomeDistrito)
    }






}


