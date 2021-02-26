package com.example.project.model

import android.content.Context
import android.view.ViewGroup
import com.example.financaspessoais.R
import com.example.financaspessoais.extensions.formatarDataBr
import com.example.financaspessoais.model.TipoTransacao
import com.example.financaspessoais.model.Transacao

class AlteraTransacao(
    private val context: Context,
    viewGroup: ViewGroup,
    private val transacao: Transacao
): FormularioTransacaoDialog(context, viewGroup,transacao.tipo) {



    fun configuraDialog(transacao: Transacao,delegate:(transacao: Transacao) -> Unit) {
        val tipo = transacao.tipo
        super.configuraDialog(delegate)
        inicializandoCampos(transacao, tipo)
    }

    fun inicializandoCampos(
        transacao: Transacao,
        tipo: TipoTransacao
    ) {
        recursoTransacaoValor.setText(transacao.valor.toString())
        recursoTransacaoData.setText((transacao.data.formatarDataBr()))
        val indexOf = pegaCategoria(tipo, transacao)
        recursoTransacaoCategoria.setSelection(indexOf)
    }

    private fun pegaCategoria(
        tipo: TipoTransacao,
        transacao: Transacao
    ): Int {
        val categoriaTipo = context.resources.getStringArray(devolveCategoria(tipo))
        return categoriaTipo.indexOf(transacao.categoria)
    }

    override val tituloBotaoPositivo: String
        get() = "Alterar"

    override fun devolveTipoTransacao(): Int {
        if(transacao.tipo==TipoTransacao.RECEITA){
            return R.string.altera_receita
        }
        return  R.string.altera_despesa
    }


}