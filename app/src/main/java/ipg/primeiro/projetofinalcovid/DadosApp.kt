package ipg.primeiro.projetofinalcovid

import androidx.fragment.app.Fragment
import ipg.primeiro.projetofinalcovid.classedastabelas.Distrito
import ipg.primeiro.projetofinalcovid.classedastabelas.Pessoa
import ipg.primeiro.projetofinalcovid.ui.ListaPessoaFragment.ListaPessoasFragment
import ipg.primeiro.projetofinalcovid.ui.NovaPessoaFragment.NovaPessoaFragment

class DadosApp {
    companion object{
        lateinit var activity: MainActivity
        lateinit var fragment: Fragment

        var pessoaSelecionada: Pessoa? = null

        var distritoSelecionado: Distrito? = null
    }
}