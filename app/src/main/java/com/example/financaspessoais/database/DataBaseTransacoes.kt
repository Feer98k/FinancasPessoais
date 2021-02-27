package com.example.financaspessoais.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.financaspessoais.database.converter.DataConverter
import com.example.financaspessoais.database.converter.TipoTransacaoConverter
import com.example.financaspessoais.database.dao.TransacaoDAO
import com.example.financaspessoais.model.Transacao


@Database(version = 1, entities = [Transacao::class], exportSchema = false)
@TypeConverters(*[TipoTransacaoConverter::class,DataConverter::class])

abstract class DataBaseTransacoes : RoomDatabase() {
    abstract fun TransacaoDAO(): TransacaoDAO

    companion object {
        private const val DB_NAME = "db_notes"
        private var instance: DataBaseTransacoes? = null

        fun getInstance(context: Context): DataBaseTransacoes? {
            if (instance == null) {
                synchronized(DataBaseTransacoes::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataBaseTransacoes::class.java, DB_NAME
                    )
                        .allowMainThreadQueries()
                        .addMigrations()
                        .build()
                }
            }
            return instance
        }
    }
}