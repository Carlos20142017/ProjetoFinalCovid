package ipg.primeiro.projetofinalcovid.DistritoFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListaDistritoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Pessoa Fragment"
    }
    val text: LiveData<String> = _text
}