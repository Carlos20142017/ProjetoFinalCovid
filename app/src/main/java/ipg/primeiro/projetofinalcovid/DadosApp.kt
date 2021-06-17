package ipg.primeiro.projetofinalcovid

import ipg.primeiro.projetofinalcovid.classedastabelas.Pessoa
import ipg.primeiro.projetofinalcovid.ui.ListaPessoaFragment.ListaPessoasFragment

class DadosApp {
    companion object{
        lateinit var activity: MainActivity
        lateinit var listaPessoasFragment: ListaPessoasFragment
        var pessoaSelecionada: Pessoa? = null
    }
}