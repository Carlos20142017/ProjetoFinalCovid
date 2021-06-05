package ipg.primeiro.projetofinalcovid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ipg.primeiro.projetofinalcovid.R

class ListaPessoasFragment : Fragment() {

    private lateinit var homeViewModel: ListaPessoasViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(ListaPessoasViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_lista_pessoa, container, false)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }
}