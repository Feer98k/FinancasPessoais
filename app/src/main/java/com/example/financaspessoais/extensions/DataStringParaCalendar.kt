package com.example.financaspessoais.dialog

import java.text.SimpleDateFormat
import java.util.*


fun String.converteParaCalendar(): Calendar {
    val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
    val parse = formatoBrasileiro.parse(this)
    val data = Calendar.getInstance()
    data.time = parse
    return data

}