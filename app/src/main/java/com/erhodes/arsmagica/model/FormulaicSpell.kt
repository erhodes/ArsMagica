package com.erhodes.arsmagica.model

class FormulaicSpell(val name: String, level: Int, val description: String, technique: StatEnum, form: StatEnum) : Spell(level, technique, form) {
    var mastery = 0

    override fun getCastingValue(caster: Character):Int {

        val techniqueScore = getLowestScore(techniques, caster)
        val formScore = getLowestScore(forms, caster)

        return techniqueScore + formScore + caster.getStatScore(StatEnum.STAMINA) + mastery +
                caster.getStatScore(StatEnum.TALISMAN) + caster.getStatScore(StatEnum.MISC)
    }

}