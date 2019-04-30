package com.erhodes.arsmagica.model

import android.util.Log

class SpontaneousSpell(level: Int, form: StatEnum, technique: StatEnum) : Spell(level, form, technique) {
    var mode: Mode = Mode.SPONTANEOUS

    override fun getCastingValue(caster: Character): Int {
        var techniqueScore = getLowestScore(techniques, caster)
        var formScore = getLowestScore(forms, caster)

        Log.d("Eric","ts " + techniqueScore + " fs " + formScore)
        if (mode == Mode.DIEDNE) {
            Log.d("Eric","diedne!" + formScore + " " + techniqueScore);
            if (formScore <= techniqueScore) {
                formScore += formScore
            } else {
                techniqueScore += techniqueScore
            }
        }
        var castingTotal: Int = caster.getStatScore(StatEnum.STAMINA) + formScore + techniqueScore
        when (mode) {
            Mode.SPONTANEOUS -> castingTotal/=5
            Mode.FATIGUED -> castingTotal/=2
            Mode.DIEDNE -> castingTotal/=2
        }
        return castingTotal
    }

    enum class Mode {
        SPONTANEOUS,
        FATIGUED,
        DIEDNE,
        REGULAR
    }

    companion object {
        val MODE_REGULAR = 0
        val MODE_FATIGUED = 1
        val MODE_DIEDNE = 2
    }
}