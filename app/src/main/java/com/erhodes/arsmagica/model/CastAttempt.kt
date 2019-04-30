package com.erhodes.arsmagica.model

class CastAttempt(val spell: Spell) {
    var fastCast: Boolean = false

    /**
     * Get the current cast total for this attempt
     */
    fun getCastTotal(caster: Character): Int {

        var castingTotal = spell.getCastingValue(caster)

        if (fastCast) {
            castingTotal-=10
        }

        return castingTotal
    }

    fun cast(caster: Character): Int {
        var roll = roll()
        if (roll == 0) {
            return -spell.getCastingValue(caster)
        } else {
            if (spell is SpontaneousSpell) {
                when (spell.mode) {
                    SpontaneousSpell.Mode.SPONTANEOUS -> roll = roll/5
                    SpontaneousSpell.Mode.FATIGUED, SpontaneousSpell.Mode.DIEDNE -> roll = roll/2
                }
            }
            return roll + spell.getCastingValue(caster)
        }
    }

    private fun roll(): Int {
        var roll = (0..9).shuffled().first()
        when (roll) {
            1 -> roll+=stresslessRoll()
        }
        if (roll == 1) {
            roll+=stresslessRoll()
        }
        return roll
    }

    private fun stresslessRoll(): Int {
        var roll = (1..10).shuffled().first()
        if (roll == 1) {
            roll+=stresslessRoll()
        }
        return roll
    }
}