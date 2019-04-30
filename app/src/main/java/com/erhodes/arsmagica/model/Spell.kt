package com.erhodes.arsmagica.model

open class Spell(val level: Int, val techniques: MutableSet<StatEnum>, val forms: MutableSet<StatEnum>) {
    constructor(level: Int, technique: StatEnum, form: StatEnum): this(level, StatEnum.asSet(technique), StatEnum.asSet(form))

    fun toggleForm(art: StatEnum, included: Boolean) {
        if (included) {
            forms.add(art)
        } else {
            forms.remove(art)
        }
    }

    fun toggleTechnique(art: StatEnum, included: Boolean) {
        if (included) {
            techniques.add(art)
        } else {
            techniques.remove(art)
        }
    }

    protected fun getLowestScore(list: Set<StatEnum>, caster: Character): Int {
        if (list.size == 0) {
            return 0
        }
        var result = caster.getStatScore(list.first())
        for(stat in list) {
            result = Math.min(result, caster.getStatScore(stat))
        }
        return result
    }

    open fun getCastingValue(caster: Character): Int {
//        return caster.getStatScore(StatEnum.STAMINA) + caster.getStatScore(technique) + caster.getStatScore(form) +
//            mastery;
        return 1
    }

    fun getPenetrationBonus(caster: Character): Int {
        return caster.getStatScore(StatEnum.PENETRATION)
    }
}