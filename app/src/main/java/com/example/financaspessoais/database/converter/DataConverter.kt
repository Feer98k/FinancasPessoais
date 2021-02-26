package com.example.financaspessoais.database.converter

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*


class DataConverter {

    @TypeConverter
    fun toLong(calendar: Calendar): Long? {
        return calendar.timeInMillis

    }

    @TypeConverter
    fun toCalendar(value: Long): Calendar? {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = value
        return calendar

    }

}

