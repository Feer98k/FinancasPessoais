package com.example.project.model

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.financaspessoais.R
import com.example.financaspessoais.database.DataBase
import com.example.financaspessoais.database.dao.TransacaoDAO
import com.example.financaspessoais.dialog.converteParaCalendar
import com.example.financaspessoais.extensions.formatarDataBr
import com.example.financaspessoais.model.TipoTransacao
import com.example.financaspessoais.model.Transacao
import java.math.BigDecimal
import java.util.*

abstract class FormularioTransacaoDialog(
    private val context: Context,
    private val viewGroup: View,
    private val tipoTransacao: TipoTransacao
) {
    private val viewCriada = criarLayout()
    protected val recursoTransacaoValor =
        viewCriada.findViewById<EditText>(R.id.form_transacao_valor)
    protected val recursoTransacaoCategoria =
        viewCriada.findViewById<Spinner>(R.id.form_transacao_categoria)
    protected val recursoTransacaoData = viewCriada.findViewById<EditText>(R.id.form_transacao_data)
    abstract val tituloBotaoPositivo: String

    fun configuraDialog(delegate: (transacao: Transacao) -> Unit) {
        configuraCampoData()
        configuraCampoCategoria(tipoTransacao)
        criaDialog(delegate)
    }

    private fun criaDialog(delegate: (transacao: Transacao) -> Unit) {
        val titulo = devolveTipoTransacao()

        AlertDialog
            .Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton(tituloBotaoPositivo) { _, _ ->
                val campoValorTransacao = recursoTransacaoValor.text.toString()
                val campoTransacaoCategoria = recursoTransacaoCategoria.selectedItem.toString()
                val campoTransacaoData = recursoTransacaoData.text.toString()
                val posicao : Int = DataBase.getInstance(context)!!.TransacaoDAO().getAllTransacao().size
                val valorTransacaoFormatado = converteValorParaBigDecimal(campoValorTransacao)
                val data = campoTransacaoData.converteParaCalendar()
                val tipoEnumCriado = tipoTransacao
                val transacoes1 =
                    Transacao(
                        valor = valorTransacaoFormatado,
                        tipo = tipoEnumCriado,
                        categoria = campoTransacaoCategoria,
                        data = data,
                        posicao = posicao

                    )
                delegate(transacoes1)

            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun converteValorParaBigDecimal(campoValorTransacao: String): Double {
        try {
            return campoValorTransacao.toDouble()
        } catch (exception: NumberFormatException) {

        }
        return 0.0
    }
    private fun configuraCampoCategoria(tipo: TipoTransacao) {
        val categoria = devolveCategoria(tipo)
        val adapterSpinnerDepesa = ArrayAdapter.createFromResource(
            context, categoria,
            android.R.layout.simple_spinner_dropdown_item
        )
        recursoTransacaoCategoria.adapter = adapterSpinnerDepesa

    }

    protected fun devolveCategoria(tipo: TipoTransacao): Int {
        if (tipo == TipoTransacao.DESPESA) {
            return R.array.categorias_de_despesa
        }
        return R.array.categorias_de_receita

    }

    private fun configuraCampoData() {
        var dataAtual = Calendar.getInstance()
        recursoTransacaoData.setText(dataAtual.formatarDataBr())
        val ano = dataAtual.get(Calendar.YEAR)
        val mes = dataAtual.get(Calendar.MONTH)
        val dia = dataAtual.get(Calendar.DAY_OF_MONTH)


        recursoTransacaoData
            .setOnClickListener {
                DatePickerDialog(
                    context,
                    { _, ano, mes, dia ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(ano, mes, dia)
                        recursoTransacaoData
                            .setText(dataSelecionada.formatarDataBr())
                    }, ano, mes, dia
                )
                    .show()
            }
    }

    private fun criarLayout(): View {
        return LayoutInflater
            .from(context)
            .inflate(R.layout.form_transacao, viewGroup as ViewGroup, false)
    }

    abstract fun devolveTipoTransacao(): Int


}