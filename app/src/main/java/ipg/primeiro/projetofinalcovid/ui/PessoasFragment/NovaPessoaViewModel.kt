package ipg.primeiro.projetofinalcovid.ui.PessoasFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NovaPessoaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Nova Pessoa Fragment"
    }
    val text: LiveData<String> = _text
}