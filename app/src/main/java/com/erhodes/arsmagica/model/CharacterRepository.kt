package com.erhodes.arsmagica.model

import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.erhodes.arsmagica.room.AppDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository {

    var mCharacter: Character
    val characterLiveData: MutableLiveData<Character> = MutableLiveData()
    val appDatabase: AppDatabase
    val handler: Handler

    @Inject
    constructor(database: AppDatabase) {
        val handlerThread = HandlerThread("databaseThread")
        handlerThread.start()
        handler = Handler(handlerThread.looper)

        mCharacter = Character("Ferrus")

        appDatabase = database
        handler.post {
//            saveCharacter(mCharacter)
            if (appDatabase.characterDao().getAll().isEmpty()) {
                Log.d("Eric", "initialize db")
                mCharacter = Character("Ferrus")
                saveCharacter(mCharacter)
            } else {
                Log.d("Eric", "loaded in a character")
                mCharacter = appDatabase.characterDao().getAll()[0]
            }
            characterLiveData.postValue(mCharacter)
        }
    }

    fun getCharacter(name: String): Character {
        return mCharacter
    }

    public fun increaseStat(stat: Stat) {
        mCharacter.stats[stat.type.ordinal].score++
        characterLiveData.postValue(mCharacter)
        saveCharacter(mCharacter)
    }

    public fun decreaseStat(stat: Stat) {
        stat.score--
        characterLiveData.postValue(mCharacter)
    }

    fun saveCharacter(character: Character) {
        handler.post {
            appDatabase.characterDao().insert(character)
        }
    }

    public fun castSpell(caster: Character, spell: Spell, penalty: Int): CastResult {
        val roll = (0..9).shuffled().first()
        Log.d("Eric","rolled a " + roll)

        var total: Int = roll + spell.getCastingValue(caster) - penalty
        var penetration: Int = spell.getPenetrationBonus(caster)

        var result: Int
        if (total >= spell.level) {
            // if the spell was cast successfully, add the extra power to the Pen total
            penetration += (total - spell.level)
            result = 0
        } else if (total >= spell.level - 10) {
            result = 1
        } else {
            result = 2
        }
        return CastResult(result, roll, total, penetration)
    }
}