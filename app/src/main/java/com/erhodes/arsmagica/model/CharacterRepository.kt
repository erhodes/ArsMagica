package com.erhodes.arsmagica.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository {

    var mCharacter: Character
    val characterLiveData: MutableLiveData<Character> = MutableLiveData()

    @Inject
    constructor() {
        mCharacter = Character("Ferrus")
        characterLiveData.postValue(mCharacter)
    }

    fun getCharacter(name: String): Character {
        return mCharacter
    }

    public fun increaseStat(stat: Stat) {
        stat.score++
        characterLiveData.postValue(mCharacter)
    }

    public fun decreaseStat(stat: Stat) {
        stat.score--
        characterLiveData.postValue(mCharacter)
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