package ipg.primeiro.projetofinalcovid.ui.NovaPessoaFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ipg.primeiro.projetofinalcovid.DadosApp
import ipg.primeiro.projetofinalcovid.MainActivity
import ipg.primeiro.projetofinalcovid.R

class NovaPessoaFragment : Fragment() {

    private lateinit var galleryViewModel: NovaPessoaViewModel

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
        DadosApp.novaPessoaFragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_nova_pessoa
        return root
    }

    fun navegaListaLivros(){
        // todo: guardar livro
    }

    fun guardar(){
        // todo: livro
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.action_guardar_nova_pessoa -> guardar()
            R.id.action_cancelar_nova_pessoa -> navegaListaLivros()

            else -> return false
        }
        return true
    }
    
}