package ipg.primeiro.projetofinalcovid.ui.PessoasFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListaPessoasViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Pessoa Fragment"
    }
    val text: LiveData<String> = _text
}