package com.erhodes.arsmagica.model

class Spell(val name: String, val level: Int, val description: String, val technique: StatEnum, val form: StatEnum) {
    var mastery: Int = 0

    fun getCastingValue(caster: Character): Int {
        return caster.getStatScore(StatEnum.STAMINA) + caster.getStatScore(technique) + caster.getStatScore(form) +
            mastery;
    }

    fun getPenetrationBonus(caster: Character): Int {
        return caster.getStatScore(StatEnum.PENETRATION)
    }
}