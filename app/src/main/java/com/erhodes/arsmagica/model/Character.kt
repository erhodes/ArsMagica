package com.erhodes.arsmagica.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Character {
    @PrimaryKey
    var name: String
    @Ignore
    val spells: ArrayList<Spell> = ArrayList()

    var stats: ArrayList<Stat> = ArrayList()

    constructor(name: String) {
        this.name = name;

        for (item in StatEnum.values()) {
            addStat(item, 1)
        }

        addSpell(Spell("Unseen Porter", 10, "Levitate something", StatEnum.REGO, StatEnum.TERRAM))
        addSpell(Spell("Firebolt", 15, "Fire spell that deals +10 damage at voice range", StatEnum.CREO, StatEnum.IGNEM))
    }

    fun addStat(type: StatEnum, score: Int) {
        stats.add(type.ordinal, Stat(type, score))
    }

    fun addStat(stat: Stat) {
        stats[stat.type.ordinal] = stat
    }

    fun getCharacteristics(): List<Stat> = stats.subList(StatEnum.STRENGTH.ordinal, StatEnum.PRESENCE.ordinal+1)

    fun getArts(): List<Stat> = stats.subList(StatEnum.CREO.ordinal, StatEnum.VIM.ordinal+1)

    fun getAbilities(): List<Stat> = stats.subList(StatEnum.FINESSE.ordinal, StatEnum.PENETRATION.ordinal+1)

    fun getStatScore(stat: StatEnum): Int = stats[stat.ordinal].score

    fun addSpell(spell: Spell) {
        spells.add(spell)
    }
}