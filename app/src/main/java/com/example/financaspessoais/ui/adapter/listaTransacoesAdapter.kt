package com.example.financaspessoais.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.financaspessoais.R
import com.example.financaspessoais.database.DataBase
import com.example.financaspessoais.extensions.formataMoedaBrasil
import com.example.financaspessoais.extensions.formatarDataBr
import com.example.financaspessoais.model.ResumoView
import com.example.financaspessoais.model.TipoTransacao
import com.example.financaspessoais.model.Transacao
import com.example.project.model.AlteraTransacao
import java.util.*

class listaTransacoesAdapter(
    private val context: Context,
    private val lista: MutableList<Transacao> = mutableListOf(),
    private val listaView: View

) : RecyclerView.Adapter<listaTransacoesAdapter.TransacoesHolder>() {
    var onClick: (transacao: Transacao) -> Unit = {
    }

    var dao = DataBase.getInstance(context)!!.TransacaoDAO()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransacoesHolder {
        val inflate = LayoutInflater.from(context)
            .inflate(R.layout.transacao_item, parent, false)
        return TransacoesHolder(inflate)
    }

    override fun onBindViewHolder(holder: TransacoesHolder, position: Int) {
        val transacao = lista[position]

        holder.vincula(transacao, context, listaView as ViewGroup)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    internal fun remove(position: Int) {
        var transacaoR = lista[position]
       lista.forEach { v->
           run {
               if (v.posicao > transacaoR.posicao) {
                   v.posicao = v.posicao - 1
                   dao.update(v)
               }
           }
       }
        dao.delete(transacaoR)
        notifyItemRemoved(position)
        atualizaLista()
    }

    private fun atualizaResumo() {
        ResumoView(listaView, lista, context).configuraViewResumo()
    }
    internal fun atualizaLista(){
        lista.clear()
        lista.addAll(dao.getAllTransacao())
        atualizaResumo()
        notifyDataSetChanged()

    }

    fun onMoveItem(fromPos: Int, toPos: Int) {
        val trancao1 = lista[fromPos]
        val tracao2= lista[toPos]
        val rec : Int = trancao1.posicao
        trancao1.posicao=(tracao2.posicao)
        tracao2.posicao = rec
        Collections.swap(lista,fromPos,toPos)
        dao.update(trancao1)
        dao.update(tracao2)
        notifyItemMoved(fromPos,toPos)


    }

    inner class TransacoesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var data = itemView.findViewById<TextView>(R.id.transacao_data)
        private var valor = itemView.findViewById<TextView>(R.id.transacao_valor)
        private var iconeTransacao = itemView.findViewById<ImageView>(R.id.transacao_icone)
        private var categoria = itemView.findViewById<TextView>(R.id.transacao_categoria)
        private lateinit var transacao: Transacao



        fun vincula(transacao: Transacao, context: Context, view: ViewGroup) {
            this.transacao = transacao
            data.text = transacao.data.formatarDataBr()

            valor.text = transacao.valor.formataMoedaBrasil()
            categoria.text = transacao.categoria
            itemView.setOnClickListener(alteraTransacao(transacao, context, view))
            setIconeTransacao(transacao, context)
        }

        private fun alteraTransacao(
             transacaoAltera: Transacao,
            context: Context,
            view: ViewGroup,
        ): (v: View) -> Unit = {
            onClick(transacaoAltera)
            AlteraTransacao(context, view, transacaoAltera)
                .configuraDialog(transacaoAltera, delegate = { v ->
                    dao.delete(transacaoAltera)
                    v.posicao = transacaoAltera.posicao
                    Log.d("TAG", "alteraTransacao:${v.posicao} ")

                    dao.insert(v)
                    atualizaLista()
                })
            Log.d("TAG", "alteraTransacao:${transacaoAltera.posicao} ")

        }

        private fun setIconeTransacao(
            transacao: Transacao,
            context: Context
        ) {
            if (transacao.tipo == TipoTransacao.RECEITA) {
                valor.setTextColor(ContextCompat.getColor(context, R.color.receita))
                iconeTransacao.setBackgroundResource(R.drawable.icone_transacao_receita)
            } else {
                valor.setTextColor(ContextCompat.getColor(context, R.color.despesa))
                iconeTransacao.setBackgroundResource(R.drawable.icone_transacao_despesa)
            }
        }
    }

}

