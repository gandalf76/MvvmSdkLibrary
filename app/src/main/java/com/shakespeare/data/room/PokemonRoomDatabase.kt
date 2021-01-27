package com.shakespeare.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
abstract class PokemonRoomDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao?

    companion object {
        @Volatile
        private var INSTANCE: PokemonRoomDatabase? = null

        fun getDatabase(context: Context): PokemonRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(PokemonRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            PokemonRoomDatabase::class.java, "pokemon_database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}