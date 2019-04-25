package com.erhodes.arsmagica.model

class Spell(val name: String, val level: Int, val description: String, val technique: StatEnum, val form: StatEnum) {
    var mastery: Int = 0

    fun getCastingValue(caster: Character): Int {
        return caster.getCharacteristicValue(StatEnum.STAMINA) + caster.getArtValue(technique) + caster.getArtValue(form) +
            mastery;
    }

    fun getPenetrationBonus(caster: Character): Int {
        return caster.getAbilityValue(StatEnum.PENETRATION)
    }
}