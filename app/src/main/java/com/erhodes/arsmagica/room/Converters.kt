package com.erhodes.arsmagica.room

import android.util.Log
import androidx.room.TypeConverter
import com.erhodes.arsmagica.model.Stat
import com.erhodes.arsmagica.model.StatEnum

class Converters {

    @TypeConverter
    fun statsFromString(string: String): ArrayList<Stat> {
        Log.d("Eric","read in " + string)
        val result = ArrayList<Stat>()

        for (subString in string.split(",")) {
            Log.d("Eric",subString)
            val type = subString.split(":")[0]
            val score = subString.split(":")[1]
            val statEnum = StatEnum.valueOf(type)
            Integer.valueOf(score)
            val stat = Stat(statEnum, Integer.valueOf(score))
            result.add(stat)
        }

        return result
    }

    @TypeConverter
    fun stringFromStats(stats: ArrayList<Stat>): String {
        var dataString = ""
        for (stat in stats) {
            dataString += stat.toString() + ","
        }
        // to get rid of the last comma
        dataString = dataString.dropLast(1)
        Log.d("Eric","saved " + dataString)
        return dataString
    }

}