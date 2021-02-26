package com.example.financaspessoais.model

import android.content.Context
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.financaspessoais.R
import com.example.financaspessoais.extensions.formataMoedaBrasil
import com.example.financaspessoais.ui.activity.MainActivity
import java.math.BigDecimal

class ResumoView(
    private val view: View,
    private val transacoes: List<Transacao>,
    private val contexto: Context,


) {

    private var totalReceita = 0.0
    private var totalDespesa = 0.0
    private var totalLiquido = 0.0


    fun configuraViewResumo() {
        definaValoresParaResumo()
        configuraViews()
    }

    private fun definaValoresParaResumo() {
        for (transacaoes in transacoes) {
            if (transacaoes.tipo == TipoTransacao.RECEITA) totalReceita+=transacaoes.valor
            else totalDespesa = totalDespesa.plus(transacaoes.valor)
        }
        totalLiquido = totalReceita-totalDespesa
    }

    private fun configuraViews() {
        with(view.findViewById<TextView>(R.id.main_valorDespesa_textView)) {
            text = totalDespesa.formataMoedaBrasil()

        }
        with(view.findViewById<TextView>(R.id.main_valorReceita_textView)) {
            text = totalReceita.formataMoedaBrasil()
        }
        with(view.findViewById<TextView>(R.id.main_valorTotaltextView)) {
            text = totalLiquido.formataMoedaBrasil()
            setTextColor(ContextCompat.getColor(contexto, configuraCorResumo(totalLiquido)))
        }

    }

    private fun configuraCorResumo(total: Double): Int {
        if (total >= 0.0)
            return R.color.receita
        return R.color.despesa
    }

}