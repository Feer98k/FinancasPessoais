package com.example.financaspessoais.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*
@Entity
class Transacao(
    val categoria: String = "Indefinido",
    val tipo: TipoTransacao,
    val data: Calendar = Calendar.getInstance(),
    var posicao : Int = 0,
    val valor: Double = 0.0
    ){
    @PrimaryKey(autoGenerate = true)
   var id: Int =0
    get() = field
    set(value){
        field = value
    }


}


