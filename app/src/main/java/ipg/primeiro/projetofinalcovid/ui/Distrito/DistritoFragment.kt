package ipg.primeiro.projetofinalcovid.ui.Distrito

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ipg.primeiro.projetofinalcovid.R

class DistritoFragment : Fragment() {

    companion object {
        fun newInstance() = DistritoFragment()
    }

    private lateinit var viewModel: DistritoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_distrito, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DistritoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}