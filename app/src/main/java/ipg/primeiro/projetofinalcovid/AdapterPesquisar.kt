package ipg.primeiro.projetofinalcovid

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ipg.primeiro.projetofinalcovid.classedastabelas.Pessoa
import ipg.primeiro.projetofinalcovid.classedastabelas.Teste
import ipg.primeiro.projetofinalcovid.ui.PessoasFragment.AdapterPessoas
import ipg.primeiro.projetofinalcovid.ui.PessoasFragment.ListaPessoasFragment
import java.util.*
import kotlin.collections.ArrayList

class AdapterPesquisar(val fragment: PesquisarPessoasFragment): RecyclerView.Adapter<AdapterPesquisar.ViewHolderPesquisaPessoas>(), Filterable {

  var itemPessoaList = ArrayList<Pessoa>()
    var itemPessoaListFilter = ArrayList<Pessoa>()

    fun setData(itemPessoaList: ArrayList<Pessoa>) {
        this.itemPessoaList = itemPessoaList
        this.itemPessoaListFilter = itemPessoaList
        notifyDataSetChanged()
    }





    var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolderPesquisaPessoas(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        private val textViewNome = itemView.findViewById<TextView>(R.id.textViewNome)
        private val textViewSexo = itemView.findViewById<TextView>(R.id.textViewSexo)


        private lateinit var pessoa : Pessoa


        init{
            itemView.setOnClickListener(this)
        }

        fun atualizaPessoa(pessoa: Pessoa) {

            this.pessoa = pessoa


            textViewNome.text = pessoa.nome
            textViewSexo.text = pessoa.sexo

        }






        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        override fun onClick(v: View?) {
            selecionado?.desSeleciona()
            seleciona()
        }

        private fun seleciona() {
            selecionado = this
            itemView.setBackgroundResource(R.color.cor_selecao)
            DadosApp.pesquisaSelecionada = pessoa

            findNavController((DadosApp.fragment as PesquisarPessoasFragment)).navigate(R.id.action_pesquisarPessoasFragment_to_verDadosFragment)

        }

        private fun desSeleciona(){
            selecionado = null
            itemView.setBackgroundResource(android.R.color.white)
        }

        companion object{
            var selecionado : ViewHolderPesquisaPessoas? = null
        }
    }

    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     *
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     *
     * The new ViewHolder will be used to display items of the adapter using
     * [.onBindViewHolder]. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary [View.findViewById] calls.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see .getItemViewType
     * @see .onBindViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPesquisaPessoas {
        val itemPessoa = fragment.layoutInflater.inflate(R.layout.item_pesquisar_pessoa, parent, false)

        return ViewHolderPesquisaPessoas(itemPessoa)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolderPesquisaPessoas, position: Int) {

        cursor!!.moveToPosition(position)
        holder.atualizaPessoa(Pessoa.fromCursor(cursor!!))



    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {

        return cursor?.count ?: 0
    }

    override fun getFilter(): Filter {

        return  object: Filter(){
            override fun performFiltering(charSequence: CharSequence?): FilterResults {

                val filterResults = FilterResults()
                if (charSequence == null || charSequence.length < 0){
                    filterResults.count = itemPessoaListFilter.size
                    filterResults.values =  itemPessoaListFilter
                }else{
                    var searchView = charSequence.toString().toLowerCase()

                    val itemPessoaList = ArrayList<Pessoa>()

                    for (item in itemPessoaListFilter){
                        if(item.nome.contains(searchView)){
                            itemPessoaList.add(item)
                        }

                    }

                    filterResults.count = itemPessoaList.size
                    filterResults.values = itemPessoaList
                }
                return  filterResults
            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {

                itemPessoaList = filterResults!!.values as ArrayList<Pessoa>
                notifyDataSetChanged()
            }

        }
    }
}