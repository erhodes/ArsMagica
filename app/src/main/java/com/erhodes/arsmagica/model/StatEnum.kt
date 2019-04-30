package com.erhodes.arsmagica.model

import com.erhodes.arsmagica.R

/**
 * The ordering here is important, since the ordinals are used to define which range of these are arts, abilities, etc
 */
enum class StatEnum(val resourceId: Int) {
    STRENGTH(R.string.strength),
    DEXTERITY(R.string.dexterity),
    QUICKNESS(R.string.quickness),
    STAMINA(R.string.stamina),
    INTELLIGENCE(R.string.intelligence),
    PERCEPTION(R.string.perception),
    COMMUNICATION(R.string.communication),
    PRESENCE(R.string.presence),

    CONCENTRATION(R.string.concentration),
    FINESSE(R.string.finesse),
    PENETRATION(R.string.penetration),

    CREO(R.string.creo),
    INTELLEGO(R.string.intellego),
    MUTO(R.string.muto),
    PERDO(R.string.perdo),
    REGO(R.string.rego),
    ANIMAL(R.string.animal),
    AQUAM(R.string.aquam),
    AURAM(R.string.auram),
    CORPUS(R.string.corpus),
    HERBAM(R.string.herbam),
    IGNEM(R.string.ignem),
    IMAGINEM(R.string.imaginem),
    MENTEM(R.string.mentem),
    TERRAM(R.string.terram),
    VIM(R.string.vim);

    companion object {
        fun asList(vararg stats: StatEnum): List<StatEnum> {
            val list = ArrayList<StatEnum>()
            list.addAll(stats)
            return list
        }
        fun asSet(vararg stats: StatEnum): MutableSet<StatEnum> {
            val set = HashSet<StatEnum>()
            set.addAll(stats)
            return set
        }
    }
}