package ipg.primeiro.projetofinalcovid

import ipg.primeiro.projetofinalcovid.classedastabelas.Pessoa
import ipg.primeiro.projetofinalcovid.ui.ListaPessoaFragment.ListaPessoasFragment
import ipg.primeiro.projetofinalcovid.ui.NovaPessoaFragment.NovaPessoaFragment

class DadosApp {
    companion object{
        lateinit var activity: MainActivity
        var listaPessoasFragment: ListaPessoasFragment? = null
        var novaPessoaFragment: NovaPessoaFragment? = null
        var pessoaSelecionada: Pessoa? = null
    }
}