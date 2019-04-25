package com.erhodes.arsmagica.model

import android.content.Context

class Stat(val type: StatEnum, var score: Int) {

    fun getName(context: Context): String = context.getString(type.resourceId)

    override fun toString(): String {
        return type.name + ":" + score
    }
}