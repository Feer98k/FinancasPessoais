package com.example.financaspessoais.database.converter

import androidx.room.TypeConverter
import com.example.financaspessoais.model.TipoTransacao

class TipoTransacaoConverter {

    @TypeConverter
    fun toString(tipo: TipoTransacao): String? {
        return tipo.name
    }
    @TypeConverter
    fun toTipoEnum(stringValue: String): TipoTransacao? {
        return TipoTransacao.valueOf(stringValue)
    }
}