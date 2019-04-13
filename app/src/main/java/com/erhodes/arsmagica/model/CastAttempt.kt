package com.erhodes.arsmagica.model

class CastAttempt {
    public lateinit var technique: StatEnum
    public lateinit var form: StatEnum
    public var fastCast: Boolean = false

    /**
     * If this attempt is for a forumlaic spell, pass it in here
     */
    public fun setSpell(spell: Spell) {
        technique = spell.technique
        form = spell.form
    }
}