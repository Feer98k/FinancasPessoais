package com.example.project.model

import android.content.Context
import android.view.ViewGroup
import com.example.financaspessoais.R
import com.example.financaspessoais.model.TipoTransacao

class AdicionaTransacao(
    private val context: Context,
    private val viewGroup: ViewGroup,
    private val tipoTransacao: TipoTransacao
) : FormularioTransacaoDialog(context, viewGroup, tipoTransacao) {
    override val tituloBotaoPositivo: String
        get() = "Adicionar"

    override fun devolveTipoTransacao(): Int {
        if(tipoTransacao==TipoTransacao.RECEITA){
            return  R.string.adiciona_receita
        }
        return  R.string.adiciona_despesa
    }
}