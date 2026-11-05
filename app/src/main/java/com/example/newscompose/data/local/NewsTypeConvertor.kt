package com.example.newscompose.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.newscompose.domain.model.Source


@ProvidedTypeConverter
class NewsTypeConvertor {

    @TypeConverter
    fun sourceToString(source: Source): String {
        return "${source.id ?: ""},${source.name}"
    }

    @TypeConverter
    fun stringToSource(source: String): Source {
        val sourceArray = source.split(',')
        return Source(
            id = sourceArray.getOrNull(0) ?: "",
            name = sourceArray.getOrNull(1) ?: ""
        )
    }
}
