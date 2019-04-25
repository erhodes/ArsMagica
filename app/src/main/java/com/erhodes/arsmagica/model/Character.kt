package com.erhodes.arsmagica.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Character {
    @PrimaryKey
    var name: String
    var characteristics: HashMap<StatEnum, Stat> = HashMap()
    @Ignore
    var abilities: HashMap<StatEnum, Stat> = HashMap()
    @Ignore
    val arts: HashMap<StatEnum, Stat> = HashMap()
    @Ignore
    val spells: ArrayList<Spell> = ArrayList()

    constructor(name: String) {
        this.name = name;

        addCharacteristic(StatEnum.STRENGTH, 1)
        addCharacteristic(StatEnum.DEXTERITY, 2)
        addCharacteristic(StatEnum.QUICKNESS, 3)
        addCharacteristic(StatEnum.STAMINA, 3)
        addCharacteristic(StatEnum.COMMUNICATION, -1)
        addCharacteristic(StatEnum.INTELLIGENCE, 3)
        addCharacteristic(StatEnum.PERCEPTION, 2)
        addCharacteristic(StatEnum.PRESENCE, 2)

        addAbility(StatEnum.PENETRATION, 2)

        addArt(StatEnum.CREO, 10)
        addArt(StatEnum.INTELLEGO, 20)
        addArt(StatEnum.MUTO, 5)
        addArt(StatEnum.PERDO, 15)
        addArt(StatEnum.REGO, 10)

        addArt(StatEnum.ANIMAL, 5)
        addArt(StatEnum.AQUAM, 5)
        addArt(StatEnum.AURAM, 5)
        addArt(StatEnum.CORPUS, 5)
        addArt(StatEnum.HERBAM, 5)
        addArt(StatEnum.IGNEM, 5)
        addArt(StatEnum.IMAGINEM, 5)
        addArt(StatEnum.MENTEM, 5)
        addArt(StatEnum.TERRAM, 5)
        addArt(StatEnum.VIM, 5)

        addSpell(Spell("Unseen Porter", 10, "Levitate something", StatEnum.REGO, StatEnum.TERRAM))
        addSpell(Spell("Firebolt", 15, "Fire spell that deals +10 damage at voice range", StatEnum.CREO, StatEnum.IGNEM))
    }

    fun getArtValue(art: StatEnum): Int {
        return arts[art]?.score ?: 0
    }

    fun addArt(type: StatEnum, score: Int) {
        arts[type] = Stat(type, score)
    }

    private fun addCharacteristic(type: StatEnum, score: Int) {
        characteristics[type] = Stat(type, score)
    }

    fun getCharacteristicValue(attribute: StatEnum): Int {
        return characteristics[attribute]?.score ?: 0
    }

    fun addAbility(type: StatEnum, score: Int) {
        abilities[type] = Stat(type, score)
    }

    fun getAbilityValue(ability: StatEnum): Int {
        return abilities[ability]?.score ?: 0
    }

    fun addSpell(spell: Spell) {
        spells.add(spell)
    }
}