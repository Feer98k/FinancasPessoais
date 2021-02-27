package com.example.financaspessoais.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.financaspessoais.R
import com.example.financaspessoais.database.DataBaseTransacoes
import com.example.financaspessoais.database.dao.TransacaoDAO
import com.example.financaspessoais.model.TipoTransacao
import com.example.financaspessoais.model.Transacao
import com.example.financaspessoais.ui.adapter.itemTouchHelperTransacoes
import com.example.financaspessoais.ui.adapter.listaTransacoesAdapter
import com.example.project.model.AdicionaTransacao
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu

class ResumoUsuario : AppCompatActivity() {
    private lateinit var viewList: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var listaTransacoes: MutableList<Transacao>
    private lateinit var transacaoDAO: TransacaoDAO;
    private lateinit var listaTransacoesAdapter: listaTransacoesAdapter
    private lateinit var adicionaDespesaFAB: FloatingActionButton
    private lateinit var adicionaReceitaFab: FloatingActionButton
    private lateinit var adicionaMenuFab: FloatingActionMenu
    private lateinit var graficoButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewList = window.decorView
        setContentView(R.layout.activity_main)
        carregaComponentes()
        configuraFAB()
        setStatusBarColor()
    }

    override fun onResume() {
        super.onResume()
        atualizaLista()
        btnGraficoOnClick()
    }

    private fun setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.azul_default)
        }
    }

    fun carregaComponentes() {
        recyclerView = findViewById(R.id.main_recyclerView)
        adicionaDespesaFAB = findViewById(R.id.main_adicionar_despesa_fab)
        adicionaReceitaFab = findViewById(R.id.main_adicionar_receita_fab)
        adicionaMenuFab = findViewById(R.id.main_menu_fab)
        graficoButton = findViewById(R.id.main_btn_grafico_resumo)


        transacaoDAO = DataBaseTransacoes.getInstance(this)!!.TransacaoDAO()
        listaTransacoes = transacaoDAO.getAllTransacao()

        listaTransacoesAdapter =
            listaTransacoesAdapter(this, listaTransacoes, viewList as ViewGroup)
        recyclerView.adapter = listaTransacoesAdapter
        val itemTouchHelper =
            ItemTouchHelper(itemTouchHelperTransacoes(listaTransacoesAdapter, listaTransacoes))
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    private fun btnGraficoOnClick() {
        graficoButton.setOnClickListener {
            val intent = Intent(applicationContext, ResumoGraficoUsuario::class.java)
            startActivity(intent)
        }
    }

    private fun atualizaLista() {
        listaTransacoesAdapter.atualizaLista()
    }


    private fun configuraFAB() {
        adicionaDespesaFAB.setOnClickListener {
            invocarDialog(TipoTransacao.DESPESA)
        }
        adicionaReceitaFab.setOnClickListener {
            invocarDialog(TipoTransacao.RECEITA)
        }
    }

    private fun invocarDialog(tipo: TipoTransacao) {
        AdicionaTransacao(this, viewList as ViewGroup, tipo)
            .configuraDialog(delegate = { transacaoAdicionar ->
                adicona(transacaoAdicionar)
                adicionaMenuFab.close(true)
            })

    }

    private fun adicona(transacao: Transacao) {
        transacaoDAO.insert(transacao)
        atualizaLista()
    }

}


