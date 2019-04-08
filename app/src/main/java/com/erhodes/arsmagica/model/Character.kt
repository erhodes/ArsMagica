package com.erhodes.arsmagica.model

class Character {
    var name: String
    var characteristics: HashMap<StatEnum, Stat> = HashMap()
    var abilities: HashMap<AbilityEnum, Ability> = HashMap()
    val arts: HashMap<StatEnum, Stat> = HashMap()
    val spells: ArrayList<Spell> = ArrayList()

    constructor(name: String) {
        this.name = name;

        addCharacteristic(StatEnum.STRENGTH, 1)
        addCharacteristic(StatEnum.DEXTERITY, 2)
        addCharacteristic(StatEnum.STAMINA, 3)
        addCharacteristic(StatEnum.COMMUNICATION, -1)

        addAbility(AbilityEnum.PENETRATION, 2)

        addArt(StatEnum.CREO, 10)
        addArt(StatEnum.MUTO, 5)
        addArt(StatEnum.REGO, 10)
        addArt(StatEnum.IGNEM, 15)
        addArt(StatEnum.TERRAM, 20)

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

    fun addAbility(type: AbilityEnum, score: Int) {
        abilities[type] = Ability(type, score)
    }

    fun getAbilityValue(ability: AbilityEnum): Int {
        return abilities[ability]?.score ?: 0
    }

    fun addSpell(spell: Spell) {
        spells.add(spell)
    }
}