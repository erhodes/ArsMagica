package com.erhodes.arsmagica.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.erhodes.arsmagica.model.Character

@TypeConverters(Converters::class)
@Database(entities = arrayOf(Character::class), version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}