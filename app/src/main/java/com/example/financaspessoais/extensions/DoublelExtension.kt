package com.example.financaspessoais.extensions

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*


fun Double.formataMoedaBrasil(): String {
    val format = DecimalFormat
        .getCurrencyInstance(Locale("pt", "br"))
    return format
        .format(this)
        .replace("R$", "R$ ")
        .replace("-R$", "R$ -")

}
