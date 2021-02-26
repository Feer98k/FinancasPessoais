package com.example.financaspessoais.ui.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.financaspessoais.database.dao.TransacaoDAO
import com.example.financaspessoais.model.Transacao

class itemTouchHelperTransacoes(
    private val daoAdapter: listaTransacoesAdapter,
    private val lista: List<Transacao>,
) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {


    override fun onMove(
        recyclerView: RecyclerView,
        holder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val fromPos = holder.adapterPosition
        val toPos = target.adapterPosition
        daoAdapter.onMoveItem(fromPos, toPos)
        return true
    }

    override fun onSwiped(holder: RecyclerView.ViewHolder, direction: Int) {
        val fromPos = holder.adapterPosition
        daoAdapter.remove(fromPos)
    }


}