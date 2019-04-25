package com.erhodes.arsmagica.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.erhodes.arsmagica.model.Character

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character")
    fun getAll(): List<Character>

    @Insert(onConflict = REPLACE)
    fun insert(character: Character)

}