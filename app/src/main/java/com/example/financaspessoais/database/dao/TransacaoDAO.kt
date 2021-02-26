package com.example.financaspessoais.database.dao

import androidx.room.*
import com.example.financaspessoais.model.Transacao

@Dao
interface TransacaoDAO {

    @Query("SELECT * FROM Transacao ORDER BY posicao DESC")
    fun getAllTransacao(): MutableList<Transacao>

    @Insert
    fun insert(transacao: Transacao)

    @Delete
    fun delete(transacao: Transacao)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(transacao: Transacao)

}